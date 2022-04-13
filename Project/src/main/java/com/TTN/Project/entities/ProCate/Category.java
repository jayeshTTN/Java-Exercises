package com.TTN.Project.entities.ProCate;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique=true)
    @NotBlank(message = "Enter Your Category name")
    private String  name;
    private long parentCategory;


    @OneToMany(mappedBy = "category")
    private Set<CategoryMetadataFieldValues> categoryMetadataFieldValueSet = new HashSet<>();




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(long parentCategory) {
        this.parentCategory = parentCategory;
    }

}
