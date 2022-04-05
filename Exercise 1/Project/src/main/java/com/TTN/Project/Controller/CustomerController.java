package com.TTN.Project.Controller;


import com.TTN.Project.Repository.AddressRepo;
import com.TTN.Project.Repository.CustomerRepo;
import com.TTN.Project.Repository.RoleRepo;
import com.TTN.Project.Repository.UserRepo;
import com.TTN.Project.Security.SecurityService;
import com.TTN.Project.dtos.AddressDTO;
import com.TTN.Project.dtos.UserDTO;
import com.TTN.Project.dtos.customer.CustomerDTO;
import com.TTN.Project.dtos.customer.CustomerResDTO;
import com.TTN.Project.entities.Address;
import com.TTN.Project.entities.Customer;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {

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
    private PasswordEncoder encoder;


    @PostMapping("/register")
    public ResponseEntity<CustomerResDTO> register(@Valid @RequestBody CustomerDTO customerDTO) {
        UserEntity user = new UserEntity();
        user.setEmail(customerDTO.getEmail());
        user.setFirstName(customerDTO.getFirstName());
        user.setMiddleName(customerDTO.getMiddleName());
        user.setLastName(customerDTO.getLastName());
        user.setPassword(encoder.encode(customerDTO.getPassword()));
        user.setRole(roleRepo.findByName("ROLE_CUSTOMER"));
        userRepo.save(user);

        Customer customer = new Customer();
        customer.setUser(user);
        customer.setContact(customerDTO.getContact());
        customerRepo.save(customer);

        CustomerResDTO customerResDTO = new CustomerResDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getMiddleName(), user.getLastName());

        return new ResponseEntity<CustomerResDTO>(customerResDTO, HttpStatus.CREATED);

    }

    @GetMapping("/profile")
    public CustomerResDTO viewProfile() {
        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //UserEntity userFound = userRepo.findByEmail(user.getEmail());
        CustomerResDTO customerResDTO = new CustomerResDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getMiddleName(), user.getLastName());
        return customerResDTO;
    }

    @PostMapping("/address")
    public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDTO){
        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userFound = userRepo.findByEmail(user.getEmail());
        Address address = new Address();
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setAddressLine(addressDTO.getAddressLine());
        address.setZipCode(addressDTO.getZipCode());
        userFound.setAddress(address);
        address.setUser(userFound);
        addressRepo.save(address);

        return new ResponseEntity<AddressDTO>(addressDTO,HttpStatus.CREATED);
    }
    @GetMapping("/address")
    public ResponseEntity<AddressDTO> viewAddress(){
        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userFound = userRepo.findByEmail(user.getEmail());
        Address address = userFound.getAddress();
        AddressDTO addressDTO = new AddressDTO(address.getCity(),address.getState(),address.getCountry(), address.getAddressLine(), address.getZipCode());

        return new ResponseEntity<AddressDTO>(addressDTO,HttpStatus.FOUND);
    }



}
