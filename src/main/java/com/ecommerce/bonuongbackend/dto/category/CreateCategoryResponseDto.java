package com.ecommerce.bonuongbackend.dto.category;

import com.ecommerce.bonuongbackend.model.Category;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateCategoryResponseDto {
    private int status;
    private boolean success;
    private String message;
    private Category category;

    public CreateCategoryResponseDto(int status, boolean success, String message) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
