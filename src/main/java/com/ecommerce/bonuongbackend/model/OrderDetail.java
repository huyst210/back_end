package com.ecommerce.bonuongbackend.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class OrderDetail {
    @DBRef
    private Product product;
    private Number quantity;
    private Number price;

    public OrderDetail(Product product, Number quantity, Number price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}
