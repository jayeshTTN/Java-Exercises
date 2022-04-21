package com.TTN.Project.entities;


import com.TTN.Project.entities.ProCate.Product;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
public class Seller {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;


    @Column(unique = true)
    @NotBlank(message = "Enter Your GST")
    private String gst;
    @NotBlank(message = "Enter Your Company Contact")
    private String companyContact;
    @Column(unique = true)
    @NotBlank(message = "Enter Your Company Name")
    private String companyName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                "\n email=" + user.getEmail() +
                "\n First Name=" + user.getFirstName() +
                "\n Middle Name=" + user.getMiddleName() +
                "\n Last name=" + user.getLastName() +
                "\n Company Address:- \n"+
                "\n Address Line=" + user.getAddress().getAddressLine() +
                "\n City=" + user.getAddress().getCity() +
                "\n State=" + user.getAddress().getState()+
                "\n Country=" + user.getAddress().getCountry()+
                "\n Zip Code=" + user.getAddress().getZipCode()+
                "\n gst='" + gst + '\'' +
                "\n companyContact='" + companyContact + '\'' +
                "\n companyName='" + companyName + '\'' +
                '}';
    }
}

