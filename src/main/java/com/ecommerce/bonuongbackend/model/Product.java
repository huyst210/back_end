package com.ecommerce.bonuongbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Document
public class Product {
    @Id
    private String id;

    @DBRef
    private Category category;

    @NotBlank(message = "Category name is required")
    private String name;

    private String description;

    private Number price;

    private Number quantity;

    private String unit;

    private Number discount;

    private String image;

    private Date createdAt;

    private int rating;

    public Product(Category category, String name, String description, Number price, Number quantity, String unit, Number discount, String image) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.discount = discount;
        this.image = image;
        this.createdAt = new Date();
        this.rating = 0;
    }
}
