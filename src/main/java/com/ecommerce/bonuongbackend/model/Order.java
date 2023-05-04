package com.ecommerce.bonuongbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class Order {
    @Id
    private String id;

    private OrderDetail[] orderDetail;

    private Date createdAt;

    private Boolean isDelivery;

    private Boolean isPayment;

    private Number totalPrice;

    private String note;

    @DBRef
    private User user;

    public Order(OrderDetail[] orderDetail, Boolean isPayment, Number totalPrice, User user, String note) {
        this.orderDetail = orderDetail;
        this.createdAt = new Date();
        this.isDelivery = false;
        this.isPayment = isPayment;
        this.totalPrice = totalPrice;
        this.user = user;
        this.note = note;
    }
}
