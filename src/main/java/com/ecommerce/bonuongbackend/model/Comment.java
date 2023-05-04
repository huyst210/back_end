package com.ecommerce.bonuongbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Document
public class Comment {
    @Id
    private String id;

    @NotBlank(message = "Comment content is required")
    private String content;

    @DBRef
    private User user;

    @DBRef
    private Product product;

    private Date createdAt;

    private int rating;

    public Comment(String content, User user, Product product, int rating) {
        this.content = content;
        this.user = user;
        this.product = product;
        this.createdAt = new Date();
        this.rating = rating != 0 && rating != 1 && rating != 2 && rating != 3 && rating != 4 && rating != 5 ? 5 : rating;
    }
}
