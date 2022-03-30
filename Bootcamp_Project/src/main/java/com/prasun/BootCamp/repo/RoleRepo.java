package com.prasun.BootCamp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prasun.BootCamp.Model.Role;



@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

}
