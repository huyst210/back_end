package com.ecommerce.bonuongbackend.dto.product;

import com.ecommerce.bonuongbackend.model.Product;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateProductResponseDto {
    private int status;
    private boolean success;
    private String message;
    private Product product;

    public CreateProductResponseDto(int status, boolean success, String message) {
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
