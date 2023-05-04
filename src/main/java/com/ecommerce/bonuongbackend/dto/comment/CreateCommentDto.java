package com.ecommerce.bonuongbackend.dto.comment;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateCommentDto {
    private String userId;
    private String productId;
    private String content;
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
