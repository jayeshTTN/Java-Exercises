package com.TTN.Project.Controller;


import com.TTN.Project.Repository.CustomerRepo;
import com.TTN.Project.Repository.RoleRepo;
import com.TTN.Project.Repository.UserRepo;
import com.TTN.Project.Security.SecurityService;
import com.TTN.Project.dtos.CustomerDTO;
import com.TTN.Project.dtos.UserDTO;
import com.TTN.Project.entities.Customer;
import com.TTN.Project.entities.Role;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/register/customer")
public class CustomerController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    CustomerRepo customerRepo;


    @Autowired
    private SecurityService securityService;

    @Autowired
    private PasswordEncoder encoder;


    @PostMapping("/")
    public ResponseEntity<Customer> register(@Valid @RequestBody CustomerDTO customerDTO) {
        UserEntity user = new UserEntity();
        user.setEmail(customerDTO.getEmail());
        user.setFirstName(customerDTO.getFirstName());
        user.setMiddleName(customerDTO.getMiddleName());
        user.setLastName(customerDTO.getLastName());
        user.setPassword(encoder.encode(customerDTO.getPassword()));

        Role role = new Role();
        role.setName("ROLE_CUSTOMER");
        Role savedRole = roleRepo.save(role);

        Set<Role> roles = new HashSet<>();
        roles.add(savedRole);

        user.setRole(roles);
        userRepo.save(user);

        Customer customer = new Customer();
        customer.setUser(user);
        customer.setContact(customerDTO.getContact());

        customerRepo.save(customer);

        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);

    }
}
