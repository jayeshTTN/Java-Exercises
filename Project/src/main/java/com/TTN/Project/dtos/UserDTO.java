package com.TTN.Project.dtos;

import com.TTN.Project.entities.Role;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public class UserDTO{
    private Long id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private Set<Role> roles;

}
