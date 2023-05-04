package com.ecommerce.bonuongbackend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@Document
public class User {
    @Id
    private String id;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Indexed(unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    @Indexed(unique = true)
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @Indexed(unique = true)
    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    private String role;

    private Boolean isBlock;

    private String avatar;

    public User(String fullName, String email, String username, String password, String phone, String address) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = "USER";
        this.isBlock = false;
        this.avatar = "";
    }
}
