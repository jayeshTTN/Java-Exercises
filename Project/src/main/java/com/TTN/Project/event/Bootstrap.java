package com.TTN.Project.event;

import com.TTN.Project.Enum.RoleEnum;
import com.TTN.Project.Repository.RoleRepo;
import com.TTN.Project.Repository.UserRepo;
import com.TTN.Project.entities.Role;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
        if(Objects.isNull(roleRepo.findByName(RoleEnum.ROLE_ADMIN))) {
            Role role = new Role();
            role.setName(RoleEnum.ROLE_ADMIN);
            Role role1 = new Role();
            role1.setName(RoleEnum.ROLE_CUSTOMER);
            Role role2 = new Role();
            role2.setName(RoleEnum.ROLE_SELLER);

            roleRepo.save(role);
            roleRepo.save(role1);
            roleRepo.save(role2);

            UserEntity user = new UserEntity();
            user.setEmail("admin@ttn.com");
            user.setFirstName("jayesh");
            user.setMiddleName("jagdish");
            user.setLastName("gupta");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole(roleRepo.findById(1));
            user.setIs_active(true);
            user.setIs_deleted(false);
            user.setIs_expired(false);
            user.setIs_locked(false);
            userRepo.save(user);

        }
    }
}
