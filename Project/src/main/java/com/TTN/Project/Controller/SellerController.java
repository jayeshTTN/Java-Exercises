package com.TTN.Project.Controller;


import com.TTN.Project.Repository.CustomerRepo;
import com.TTN.Project.Repository.RoleRepo;
import com.TTN.Project.Repository.SellerRepo;
import com.TTN.Project.Repository.UserRepo;
import com.TTN.Project.Security.SecurityService;
import com.TTN.Project.dtos.CustomerDTO;
import com.TTN.Project.dtos.SellerDTO;
import com.TTN.Project.entities.Customer;
import com.TTN.Project.entities.Role;
import com.TTN.Project.entities.Seller;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/register/seller")
public class SellerController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    SellerRepo sellerRepo;


    @Autowired
    private SecurityService securityService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/")
    public ResponseEntity<Seller> register(@Valid @RequestBody SellerDTO sellerDTO) {
        UserEntity user = new UserEntity();
        user.setEmail(sellerDTO.getEmail());
        user.setFirstName(sellerDTO.getFirstName());
        user.setMiddleName(sellerDTO.getMiddleName());
        user.setLastName(sellerDTO.getLastName());
        user.setPassword(encoder.encode(sellerDTO.getPassword()));

        Role role = new Role();
        role.setName("ROLE_SELLER");
        Role savedRole = roleRepo.save(role);

        Set<Role> roles = new HashSet<>();
        roles.add(savedRole);

        user.setRole(roles);
        userRepo.save(user);

        Seller seller = new Seller();
        seller.setUser(user);
        seller.setGst(sellerDTO.getGst());
        seller.setCompanyContact(sellerDTO.getCompanyContact());
        seller.setCompanyName(sellerDTO.getCompanyName());

        sellerRepo.save(seller);

        return new ResponseEntity<Seller>(seller, HttpStatus.CREATED);

    }
}
