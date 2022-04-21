package com.TTN.Project.Service;


import com.TTN.Project.Exception.CustomerAlreadyExistException;
import com.TTN.Project.Exception.PasswordMismatchException;
import com.TTN.Project.Exception.ResourceDoesNotExistException;
import com.TTN.Project.Repository.*;
import com.TTN.Project.Security.SecurityService;
import com.TTN.Project.dtos.PasswordDTO;
import com.TTN.Project.dtos.address.AddressDTO;
import com.TTN.Project.dtos.address.AddressResDTO;
import com.TTN.Project.dtos.customer.CustomerDTO;
import com.TTN.Project.dtos.customer.CustomerResDTO;
import com.TTN.Project.entities.Address;
import com.TTN.Project.entities.ConfirmationToken;
import com.TTN.Project.entities.Customer;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.Map;

@Service
public class CustomerService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    private SecurityService securityService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    CategoryService categoryService;
    @Autowired
    private TokenRepo tokenRepository;




    public ResponseEntity<String> register(CustomerDTO customerDTO){
        if(userRepo.findByEmail(customerDTO.getEmail())!=null){
            throw new CustomerAlreadyExistException("Account already exist with Email :- "+customerDTO.getEmail());
        }
        if (!customerDTO.getPassword().equals(customerDTO.getRpassword())){
            throw new PasswordMismatchException("Password Does Not Match");
        }
        UserEntity user = new UserEntity();
        user.setEmail(customerDTO.getEmail());
        user.setFirstName(customerDTO.getFirstName());
        user.setMiddleName(customerDTO.getMiddleName());
        user.setLastName(customerDTO.getLastName());
        user.setPassword(encoder.encode(customerDTO.getPassword()));
        user.setIs_active(false);
        user.setRole(roleRepo.findById(2));

        Customer customer = new Customer();
        customer.setUser(user);
        customer.setContact(customerDTO.getContact());
        customerRepo.save(customer);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        tokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Complete registration");
        mailMessage.setText("To confirm your account, please click on the given link : "
                +"http://localhost:9092/confirm/customer?token="+confirmationToken.getConfirmationToken());
        mailMessage.setTo(user.getEmail());

        emailService.sendEmail(mailMessage);

        String str = "Account Created Please Click On link received on your Email to activate your account";
        return new ResponseEntity<String>(str, HttpStatus.CREATED);
    }

    public ResponseEntity<AddressDTO> addAddress(AddressDTO addressDTO){
        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userFound = userRepo.findByEmail(user.getEmail());
        Address address = new Address();
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setAddressLine(addressDTO.getAddressLine());
        address.setZipCode(addressDTO.getZipCode());
        address.setLabel(addressDTO.getLabel());
        userFound.setAddress(address);
        address.setUser(userFound);
        addressRepo.save(address);
        return new ResponseEntity<AddressDTO>(addressDTO,HttpStatus.CREATED);
    }

    public ResponseEntity<AddressResDTO> viewAddress(){
        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Address address = user.getAddress();
        if(address==null) {
            throw new ResourceDoesNotExistException("No address exist for user:- "+user.getEmail());
        }
        AddressResDTO addressDTO = new AddressResDTO(address.getId(),address.getCity(),address.getState(),address.getCountry(),address.getAddressLine(), address.getZipCode(),address.getLabel());
        return new ResponseEntity<AddressResDTO>(addressDTO,HttpStatus.FOUND);
    }

    public AddressResDTO updateAddress(long id ,Map<Object, Object> map) {
        Address address = addressRepo.findById(id);
        map.forEach((k, v) -> {
            Field field = org.springframework.util.ReflectionUtils.findField(Address.class,(String)k);
            if(field.getName()=="id" || field.getName()=="user_id" ||field.getName()=="label"){
                return;
            }
            field.setAccessible(true);
            System.out.println(">>>>>>"+field);
            ReflectionUtils.setField(field, address, v);
        });
        addressRepo.save(address);
        Address address1 = addressRepo.getOne(address.getId());
        AddressResDTO addressResDTO = new AddressResDTO(address1.getId(),address1.getCity(),address1.getState(),address1.getCountry(),address1.getAddressLine(),address1.getZipCode(),address1.getLabel());
        return addressResDTO;
    }
    public String addressDelete(long id ){
        addressRepo.deleteById(id);
        return "Address is Deleted";
    }
    public CustomerResDTO viewProfile() {
        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerRepo.getOne(user.getId());
        CustomerResDTO customerResDTO = new CustomerResDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getMiddleName(), user.getLastName(),customer.getContact());
        return customerResDTO;
    }
    public CustomerResDTO editProfile(Map<Object, Object> map) {
        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //UserEntity user = userRepo.findByEmail(email);
        map.forEach((k, v) -> {
            Field field = org.springframework.util.ReflectionUtils.findField(UserEntity.class,(String)k);
            if(field.getName()=="email" || field.getName()=="password" ||field.getName()=="id") {
                return;
            }
            field.setAccessible(true);
            System.out.println(">>>>>>"+field);
            ReflectionUtils.setField(field, user, v);
        });
        userRepo.save(user);
        Customer customer = customerRepo.getOne(user.getId());
        CustomerResDTO customerResDTO = new CustomerResDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getMiddleName(), user.getLastName(),customer.getContact());
        return customerResDTO;
    }
    public String passwordUpdate(PasswordDTO password){
        if(!password.getPassword().equals(password.getConfirmPassword())){
            return "password does not match";
        }
        else{
            UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserEntity user1 = userRepo.findByEmail(user.getEmail());
            user1.setPassword(encoder.encode(password.getPassword()));
            userRepo.save(user1);
            return "password Changed Successfully for user :- "+user.getEmail();
        }
    }

}
