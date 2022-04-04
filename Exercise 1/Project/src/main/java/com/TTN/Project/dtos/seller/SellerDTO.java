package com.TTN.Project.dtos.seller;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SellerDTO {
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
    @Size(min = 6,max = 12,message = "Password must be at least 6 characters")
    private String password;
    @Size(min = 6,max = 12,message = "Password must be at least 6 characters")
    private String rpassword;
    @NotBlank(message = "Enter GST")
    private String gst;
    @NotBlank(message = "Enter Company Contact")
    private String companyContact;
    @NotBlank(message = "Enter Company Name")
    private String companyName;

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

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
