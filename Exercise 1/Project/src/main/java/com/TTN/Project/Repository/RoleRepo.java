package com.TTN.Project.Repository;

import com.TTN.Project.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {

    Set<Role> findByName(String name);

}
