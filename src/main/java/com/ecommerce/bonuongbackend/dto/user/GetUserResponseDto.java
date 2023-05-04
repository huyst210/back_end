package com.ecommerce.bonuongbackend.dto.user;

import com.ecommerce.bonuongbackend.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetUserResponseDto {
    private int status;
    private boolean success;
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
