package com.TTN.Project.entities;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
public class Seller {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(optional = false)
    @MapsId("id")
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @NotBlank(message = "Enter Your GST")
    private String gst;
    @NotBlank(message = "Enter Your Company Contact")
    private int companyContact;
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

    public int getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(int companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
