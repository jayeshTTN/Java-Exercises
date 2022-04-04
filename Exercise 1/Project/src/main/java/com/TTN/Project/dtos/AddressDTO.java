package com.TTN.Project.dtos;

import javax.validation.constraints.NotBlank;

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
    private int zipCode;

    public AddressDTO(String city, String state, String country, String addressLine, int zipCode) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.addressLine = addressLine;
        this.zipCode = zipCode;
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

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
}
