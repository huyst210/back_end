package com.ecommerce.bonuongbackend.dto.comment;

import com.ecommerce.bonuongbackend.model.Comment;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateCommentResponseDto {
    private int status;
    private boolean success;
    private String message;
    private Comment comment;

    public CreateCommentResponseDto(int status, boolean success, String message) {
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
