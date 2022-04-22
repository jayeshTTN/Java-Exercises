package com.TTN.Project.dtos.product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Map;

public class ProductVariationUpdateDTO {
    @Positive(message = "Enter Quantity")
    private long quantity;

    @Positive(message = "Enter Price")
    private long price;
    @NotNull(message = "Enter Metadata")
    private Map<String,String> metadata;

    private boolean isActive;

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
