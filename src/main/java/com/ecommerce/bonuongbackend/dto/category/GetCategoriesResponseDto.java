package com.ecommerce.bonuongbackend.dto.category;

import com.ecommerce.bonuongbackend.model.Category;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetCategoriesResponseDto {
    private int status;
    private boolean success;
    private String message;
    private List<Category> categories;

    public GetCategoriesResponseDto(int status, boolean success, String message) {
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
