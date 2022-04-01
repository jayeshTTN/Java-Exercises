package com.TTN.Project.event;

import com.TTN.Project.Repository.RoleRepo;
import com.TTN.Project.Repository.UserRepo;
import com.TTN.Project.entities.Role;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(Objects.isNull(userRepo.findByEmail("admin@ttn.com"))) {
            Role role = new Role();
            role.setAuthority("ROLE_ADMIN");
            Role savedRole = roleRepo.save(role);

            Set<Role> roles = new HashSet<>();
            roles.add(savedRole);


            UserEntity user = new UserEntity();
            user.setEmail("admin@ttn.com");
            user.setFirstName("jayesh");
            user.setMiddleName("jagdish");
            user.setLastName("gupta");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRpassword("admin");
            user.setRole(roles);
            userRepo.save(user);
        }
    }
}
