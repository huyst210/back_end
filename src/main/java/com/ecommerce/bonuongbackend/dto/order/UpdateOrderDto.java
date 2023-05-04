package com.ecommerce.bonuongbackend.dto.order;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateOrderDto {
    private Boolean isPayment;
    private Boolean isDelivery;

    public Boolean getPayment() {
        return isPayment;
    }

    public void setPayment(Boolean payment) {
        isPayment = payment;
    }

    public Boolean getDelivery() {
        return isDelivery;
    }

    public void setDelivery(Boolean delivery) {
        isDelivery = delivery;
    }
}
