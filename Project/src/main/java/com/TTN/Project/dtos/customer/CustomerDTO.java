package com.TTN.Project.dtos.customer;

import com.TTN.Project.entities.Role;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

public class CustomerDTO {
    private Long id;
    @Column(unique=true)
    @NotBlank(message = "Enter Your email")
    @Email(message = "Enter a Valid email Address ")
    private String email;
    @NotBlank(message = "Enter First Name")
    private String firstName;
    @NotBlank(message = "Enter Middle Name")
    private String middleName;
    @NotBlank(message = "Enter Last Name")
    private String lastName;
    @Size(min = 6,max = 12,message = "password must be 6-12 characters")
    private String password;
    @Size(min = 6,max = 12,message = "password must be 6-12 characters")
    private String rpassword;
    @Size(min = 10,max = 10,message = "Enter your contact Number")
    private String contact;

    private Set<Role> role = new HashSet<>();




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRpassword() {
        return rpassword;
    }

    public void setRpassword(String rpassword) {
        this.rpassword = rpassword;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
