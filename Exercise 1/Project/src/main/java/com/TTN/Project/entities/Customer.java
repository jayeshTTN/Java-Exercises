package com.TTN.Project.entities;



import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Customer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotBlank(message = "Enter Your Contact")
    String contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public UserEntity getUser() {
        return user;
    }

}
