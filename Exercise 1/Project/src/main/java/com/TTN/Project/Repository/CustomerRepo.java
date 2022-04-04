package com.TTN.Project.Repository;

import com.TTN.Project.entities.Customer;
import com.TTN.Project.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    //UserEntity findByEmail(String email);
}
