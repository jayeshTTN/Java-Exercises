package com.TTN.Project.Repository;

import com.TTN.Project.entities.Address;
import com.TTN.Project.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long>{

    Address findById(long id);
    Address findByUserId(long id);
}
