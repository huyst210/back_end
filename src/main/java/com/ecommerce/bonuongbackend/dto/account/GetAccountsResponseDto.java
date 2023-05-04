package com.ecommerce.bonuongbackend.dto.account;

import com.ecommerce.bonuongbackend.model.User;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetAccountsResponseDto {
    private int status;
    private boolean success;
    private String message;
    private List<User> users;

    public GetAccountsResponseDto(int status, boolean success, String message) {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
