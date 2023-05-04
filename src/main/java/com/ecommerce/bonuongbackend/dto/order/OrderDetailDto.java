package com.ecommerce.bonuongbackend.dto.order;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderDetailDto {
    private String id;
    private Number quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Number getQuantity() {
        return quantity;
    }

    public void setQuantity(Number quantity) {
        this.quantity = quantity;
    }
}
