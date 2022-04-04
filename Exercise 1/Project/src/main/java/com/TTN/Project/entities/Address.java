package com.TTN.Project.entities;



import javax.persistence.*;

@Entity
public class Address{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String city;
    private String state;
    private String country;
    private String addressLine;
    private int zipCode;

    public UserEntity getUser() {
        return user;
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

