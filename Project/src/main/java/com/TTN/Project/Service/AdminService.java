package com.TTN.Project.Service;


import com.TTN.Project.Repository.UserRepo;
import com.TTN.Project.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AdminService {

    @Autowired
    UserRepo userRepository;

    @Transactional
    public boolean activate(String email) {
        UserEntity userFound = userRepository.findByEmail(email);
        if (userFound!=null) {
            if (!userFound.isIs_active()) {
                userFound.setIs_active(true);
                userRepository.updateUser(email,true);
            }
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deactivate(String email) {
        UserEntity userFound = userRepository.findByEmail(email);
        if (userFound!=null) {
            if (userFound.isIs_active()) {
                userFound.setIs_active(false);
                userRepository.updateUser(email,false);
            }
            return true;
        }
        return false;
    }

}
