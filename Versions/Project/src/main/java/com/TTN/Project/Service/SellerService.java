package com.TTN.Project.Service;

import com.TTN.Project.Exception.CustomerAlreadyExistException;
import com.TTN.Project.Exception.PasswordMismatchException;
import com.TTN.Project.Repository.*;
import com.TTN.Project.Security.SecurityService;
import com.TTN.Project.dtos.PasswordDTO;
import com.TTN.Project.dtos.address.AddressDTO;
import com.TTN.Project.dtos.address.AddressResDTO;
import com.TTN.Project.dtos.seller.SellerDTO;
import com.TTN.Project.dtos.seller.SellerResDTO;
import com.TTN.Project.entities.Address;
import com.TTN.Project.entities.ConfirmationToken;
import com.TTN.Project.entities.Seller;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SellerService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    SellerRepo sellerRepo;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private EmailService emailService;


    @Autowired
    private SecurityService securityService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    TokenRepo tokenRepository;

    public ResponseEntity<String> register(@Valid @RequestBody SellerDTO sellerDTO) {
        List<String> list = new ArrayList<>();
        list.add(sellerDTO.getEmail());
        list.add(sellerDTO.getCompanyName());
        list.add(sellerDTO.getGst());
        if(userRepo.findByEmail(sellerDTO.getEmail())!=null)
            throw new CustomerAlreadyExistException("Account already exist with Email :- "+sellerDTO.getEmail());
        if(sellerRepo.findByCompanyName(sellerDTO.getCompanyName())!=null)
            throw new CustomerAlreadyExistException("Account already exist with Company Name :- "+sellerDTO.getCompanyName());
        if(sellerRepo.findByGst(sellerDTO.getGst())!=null)
            throw new CustomerAlreadyExistException("Account already exist with GST :- "+sellerDTO.getGst());
        if (!sellerDTO.getPassword().equals(sellerDTO.getRpassword()))
            throw new PasswordMismatchException("Password Does Not Match");
        Seller seller = new Seller();
        seller.setGst(sellerDTO.getGst());
        seller.setCompanyContact(sellerDTO.getCompanyContact());
        seller.setCompanyName(sellerDTO.getCompanyName());

        UserEntity user = new UserEntity();
        user.setEmail(sellerDTO.getEmail());
        user.setFirstName(sellerDTO.getFirstName());
        user.setMiddleName(sellerDTO.getMiddleName());
        user.setLastName(sellerDTO.getLastName());
        user.setPassword(encoder.encode(sellerDTO.getPassword()));
        user.setRole(roleRepo.findById(3));

        Address address = new Address();
        address.setCity(sellerDTO.getCity());
        address.setState(sellerDTO.getState());
        address.setCountry(sellerDTO.getCountry());
        address.setAddressLine(sellerDTO.getAddressLine());
        address.setZipCode(sellerDTO.getZipCode());
        address.setLabel(sellerDTO.getLabel());
        user.setAddress(address);
        address.setUser(user);
        addressRepo.save(address);

        seller.setUser(user);
        sellerRepo.save(seller);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        tokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Activate Seller Account");
        mailMessage.setText("New Seller account is created, please click on the given link to activate account: "
                +"Seller Information:- \n" + sellerRepo.findById(user.getId()).toString()
                +"\n http://localhost:9092/confirm/seller?token="+confirmationToken.getConfirmationToken());
        mailMessage.setTo("admin@ttn.com");

        emailService.sendEmail(mailMessage);

        String str = "Account Created Please Wait for approval to activate your account ";
        return new ResponseEntity<String>(str, HttpStatus.CREATED);
    }

    public Map<String,Object> viewProfile() {
        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Seller seller = sellerRepo.getOne(user.getId());
        SellerResDTO sellerResDTO = new SellerResDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getMiddleName(), user.getLastName(),seller.getCompanyContact(),seller.getCompanyName(),seller.getGst());
        Address address = addressRepo.findByUserId(user.getId());
        AddressDTO addressDTO = new AddressDTO(address.getCity(), address.getState(), address.getCountry(), address.getAddressLine(), address.getZipCode(),address.getLabel());
        Map<String,Object> map = new HashMap<>();
        map.put("Seller Information",sellerResDTO);
        map.put("Address",addressDTO);
        return map;
    }

    public SellerResDTO editProfile(Map<Object, Object> map) {
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
        Seller seller = sellerRepo.getOne(user.getId());
        SellerResDTO sellerResDTO = new SellerResDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getMiddleName(), user.getLastName(),seller.getCompanyContact(),seller.getCompanyName(),seller.getGst());
        return sellerResDTO;
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

    public ResponseEntity<AddressResDTO> viewAddress(){
        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Address address = user.getAddress();
        AddressResDTO addressDTO = new AddressResDTO(address.getId(),address.getCity(),address.getState(),address.getCountry(),address.getAddressLine(), address.getZipCode(),address.getLabel());
        return new ResponseEntity<AddressResDTO>(addressDTO,HttpStatus.FOUND);
    }

    public AddressResDTO updateAddress(long id , Map<Object, Object> map) {
        Address address = addressRepo.findById(id);
        map.forEach((k, v) -> {
            Field field = org.springframework.util.ReflectionUtils.findField(Address.class,(String)k);
            if(field.getName()=="id" || field.getName()=="user_id") {
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
}
