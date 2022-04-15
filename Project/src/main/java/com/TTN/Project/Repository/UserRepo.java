package com.TTN.Project.Repository;

import com.TTN.Project.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long> {

    UserEntity findByEmail(String email);

    @Modifying
    @Query(value = "update user set is_active=:flag where email =:email ",nativeQuery = true)
    void updateUser(@Param("email") String email,@Param("flag") boolean flag);
}
