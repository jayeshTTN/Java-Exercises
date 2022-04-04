package com.TTN.Project.entities;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role implements GrantedAuthority {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "role")
    private Set<UserEntity> users;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> userEntities) {
        this.users = userEntities;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
