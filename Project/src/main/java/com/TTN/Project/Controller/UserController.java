package com.TTN.Project.Controller;

import com.TTN.Project.Exception.InvalidTokenException;
import com.TTN.Project.Repository.CustomerRepo;
import com.TTN.Project.Repository.TokenRepo;
import com.TTN.Project.Repository.UserRepo;
import com.TTN.Project.Security.SecurityService;
import com.TTN.Project.dtos.UserDTO;
import com.TTN.Project.dtos.customer.CustomerResDTO;
import com.TTN.Project.entities.ConfirmationToken;
import com.TTN.Project.entities.Customer;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;


@RestController
public class UserController {

    @Autowired
    UserRepo userRepo;




    @Autowired
    private SecurityService securityService;

    @Autowired
    private PasswordEncoder encoder;


    @GetMapping("/user/{user}")
    public UserDTO getUser(@PathVariable String user) {
        UserEntity userFound = userRepo.findByEmail(user);
        UserDTO dto=new UserDTO();
        dto.setId(userFound.getId());
        dto.setEmail(userFound.getEmail());
        dto.setFirstName(userFound.getFirstName());
        dto.setMiddleName(userFound.getMiddleName());
        dto.setLastName(userFound.getLastName());
        return dto;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<UserEntity> register(@Valid @RequestBody UserEntity userEntity) {
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        UserEntity user = userRepo.save(userEntity);
        return new ResponseEntity<UserEntity>(user, HttpStatus.CREATED);
    }





    @GetMapping("/current/user")
    public UserDTO printWelcome() {
       //System.out.println(">>>>>>>>>>>>"+SecurityContextHolder.getContext().getAuthentication());
        UserEntity user =  (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userFound = userRepo.findByEmail(user.getEmail());
        UserDTO dto=new UserDTO();
        dto.setId(userFound.getId());
        dto.setEmail(userFound.getEmail());
        dto.setFirstName(userFound.getFirstName());
        dto.setMiddleName(userFound.getMiddleName());
        dto.setLastName(userFound.getLastName());
        return dto;
    }
}
