package com.TTN.Project.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role implements GrantedAuthority {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;
    @ManyToMany(mappedBy = "role")
    private Set<UserEntity> users;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public void setAuthority(String authority) {this.authority = authority;}

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> userEntities) {
        this.users = userEntities;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
