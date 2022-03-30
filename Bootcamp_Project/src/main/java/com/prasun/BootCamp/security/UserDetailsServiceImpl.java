package com.prasun.BootCamp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.prasun.BootCamp.Model.ApplicationUser;
import com.prasun.BootCamp.repo.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepo.findByEmail(username);

        if(user==null) {
            throw new UsernameNotFoundException("User Not Found for Email" + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword() , user.getRoles());
    }

}