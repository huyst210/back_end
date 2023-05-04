package com.ecommerce.bonuongbackend.dto.account;

import com.ecommerce.bonuongbackend.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountResponseDto {
    private int status;
    private boolean success;
    private String message;
    private User user;

    public AccountResponseDto(int status, boolean success, String message) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
