package com.TTN.Project.dtos.category;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class CategoryDTO {
    @Column(unique = true)
    @NotBlank(message = "Enter Category Name")
    private String name;
    private long parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
