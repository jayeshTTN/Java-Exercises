package com.TTN.Project.dtos.address;

import com.TTN.Project.Enum.LabelEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddressDTO {
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
    @NotNull(message = "Label Cannot empty")
    @Enumerated(EnumType.STRING)
    private LabelEnum label;

    public AddressDTO(String city, String state, String country, String addressLine, String zipCode, LabelEnum label) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.addressLine = addressLine;
        this.zipCode = zipCode;
        this.label = label;
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
