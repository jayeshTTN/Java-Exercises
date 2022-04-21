package com.TTN.Project.dtos.product;

import javax.validation.constraints.NotBlank;

public class ProductUpdateDTO {
    @NotBlank(message = "Enter Product Name")
    private String name;

    @NotBlank(message = "Enter description for product")
    private String description;

    private boolean isCancellable;

    private boolean isReturnable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
