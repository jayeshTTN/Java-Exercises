package com.TTN.Project.Controller;

import com.TTN.Project.Repository.UserRepo;
import com.TTN.Project.Security.SecurityService;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserEntity getUser(@PathVariable String user) {
        UserEntity userEntityFound = userRepo.findByFirstName(user);
        return userEntityFound;
    }

    @PostMapping("/registerUser")
    public String register(@Valid @RequestBody UserEntity userEntity) {
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userRepo.save(userEntity);
        return "successful";
    }


    @GetMapping("/login")
    public UserEntity printWelcome() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        System.out.println("dsajhd");
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
