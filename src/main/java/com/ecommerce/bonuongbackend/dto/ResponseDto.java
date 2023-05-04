package com.ecommerce.bonuongbackend.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseDto {
    private int status;
    private boolean success;
    private String message;

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
}
