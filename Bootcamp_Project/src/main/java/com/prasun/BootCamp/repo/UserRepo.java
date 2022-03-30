package com.prasun.BootCamp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prasun.BootCamp.Model.ApplicationUser;
@Repository
public interface UserRepo extends JpaRepository<ApplicationUser, Integer> {
ApplicationUser findByEmail(String email);
}
