package com.ecommerce.bonuongbackend.dto.order;

import com.ecommerce.bonuongbackend.model.Order;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetOrdersResponseDto {
    private int status;
    private boolean success;
    private String message;
    private List<Order> orders;

    public GetOrdersResponseDto(int status, boolean success, String message) {
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
