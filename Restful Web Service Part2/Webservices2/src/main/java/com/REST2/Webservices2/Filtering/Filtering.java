package com.REST2.Webservices2.Filtering;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(value = {"password","id"})      static filtering
@JsonFilter("Filtering")               //dynamic filtering
class User {
    private String name;

    private Integer id;



    @JsonIgnore
    private String password;

    public User(String name, Integer id, String password) {
        this.name = name;
        this.id = id;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
