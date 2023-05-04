package com.ecommerce.bonuongbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@Document
public class Category {
    @Id
    private String id;

    @NotBlank(message = "Category name is required")
    private String name;

    public Category(String name) {
        this.name = name;
    }
}