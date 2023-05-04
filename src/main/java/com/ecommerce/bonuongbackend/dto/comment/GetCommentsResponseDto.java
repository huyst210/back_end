package com.ecommerce.bonuongbackend.dto.comment;

import com.ecommerce.bonuongbackend.model.Comment;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetCommentsResponseDto {
    private int status;
    private boolean success;
    private String message;
    private List<Comment> comments;

    public GetCommentsResponseDto(int status, boolean success, String message) {
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
