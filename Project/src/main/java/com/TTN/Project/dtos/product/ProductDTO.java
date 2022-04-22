package com.TTN.Project.dtos.product;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

public class ProductDTO {

    @NotEmpty(message = "Name can't be empty")
    private String name;

    @NotEmpty(message = "Brand cannot be empty")
    private String brand;
    @Positive(message = "Category Id cannot be empty")
    private long categoryId;
    private String description;

    private boolean isCancellable;

    private boolean isReturnable;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCancellable() {
        return isCancellable;
    }

    public void setCancellable(boolean cancellable) {
        isCancellable = cancellable;
    }

    public boolean isReturnable() {
        return isReturnable;
    }

    public void setReturnable(boolean returnable) {
        isReturnable = returnable;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
