package com.ecommerce.bonuongbackend.dto.account;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountDto {
    private String role;
    private Boolean isBlock;

    public Boolean getBlock() {
        return isBlock;
    }

    public void setBlock(Boolean block) {
        isBlock = block;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
