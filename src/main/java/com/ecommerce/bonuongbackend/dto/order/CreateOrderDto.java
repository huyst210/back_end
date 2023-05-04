package com.ecommerce.bonuongbackend.dto.order;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateOrderDto {
    private OrderDetailDto[] orderDetail;
    private String userId;
    private String note;

    public OrderDetailDto[] getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetailDto[] orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
