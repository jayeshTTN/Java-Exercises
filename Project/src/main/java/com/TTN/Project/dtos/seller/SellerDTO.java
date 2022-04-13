package com.TTN.Project.dtos.seller;

import com.TTN.Project.Enum.LabelEnum;
import com.TTN.Project.dtos.address.AddressDTO;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.*;

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
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}",message ="8-15 Characters with at least 1 Lower case, 1 Upper case, 1 Special Character, 1 Number" )
    private String password;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}",message ="8-15 Characters with at least 1 Lower case, 1 Upper case, 1 Special Character, 1 Number" )
    private String rpassword;
    @Column(unique=true)
    @NotBlank(message = "Enter GST")
    private String gst;
    @NotBlank(message = "Enter City")
    private String city;
    @NotBlank(message = "Enter State")
    private String state;
    @NotBlank(message = "Enter Country")
    private String country;
    @NotBlank(message = "Enter Address")
    private String addressLine;
    @NotBlank(message = "Enter Zip Code")
    private String zipCode;

    @NotNull(message = "label cant be empty")
    @Enumerated(EnumType.STRING)
    private LabelEnum label;
    @NotBlank(message = "Enter Company Contact")
    private String companyContact;
    @Column(unique=true)
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public LabelEnum getLabel() {
        return label;
    }

    public void setLabel(LabelEnum label) {
        this.label = label;
    }
}
