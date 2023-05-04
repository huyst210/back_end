package com.ecommerce.bonuongbackend.dto.order;

import com.ecommerce.bonuongbackend.model.Order;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateOrderResponseDto {
    private int status;
    private boolean success;
    private String message;
    private Order order;

    public CreateOrderResponseDto(int status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
