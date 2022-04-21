package com.TTN.Project.dtos.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Map;

public class ProductVariationDTO {
    @Positive(message = "Enter Product Id")
    private long productId;

    @NotNull(message = "At-least One metadata is needed for product variation to be added")
    private Map<String,String> metadata;
    @Positive( message = "Enter valid Quantity")
    private long quantity;
    @Positive(message = "Enter Valid Price")
    private long price;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

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
}

