package com.TTN.Project.Controller;


import com.TTN.Project.Repository.AddressRepo;
import com.TTN.Project.Repository.RoleRepo;
import com.TTN.Project.Repository.SellerRepo;
import com.TTN.Project.Repository.UserRepo;
import com.TTN.Project.Security.SecurityService;
import com.TTN.Project.dtos.seller.SellerDTO;
import com.TTN.Project.dtos.seller.SellerResDTO;
import com.TTN.Project.entities.Address;
import com.TTN.Project.entities.Seller;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    SellerRepo sellerRepo;

    @Autowired
    AddressRepo addressRepo;


    @Autowired
    private SecurityService securityService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<SellerResDTO> register(@Valid @RequestBody SellerDTO sellerDTO) {

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
        user.setRole(roleRepo.findByName("ROLE_SELLER"));

        Address address = new Address();
        address.setCity(sellerDTO.getAddressDTO().getCity());
        address.setState(sellerDTO.getAddressDTO().getState());
        address.setCountry(sellerDTO.getAddressDTO().getCountry());
        address.setAddressLine(sellerDTO.getAddressDTO().getAddressLine());
        address.setZipCode(sellerDTO.getAddressDTO().getZipCode());
        user.setAddress(address);
        address.setUser(user);

        seller.setUser(user);
        sellerRepo.save(seller);
        addressRepo.save(address);
        userRepo.save(user);

        SellerResDTO sellerResDTO = new SellerResDTO(user.getId(), user.getEmail(), user.getFirstName(), user.getMiddleName(), user.getLastName());

        return new ResponseEntity<SellerResDTO>(sellerResDTO, HttpStatus.CREATED);
    }

}
