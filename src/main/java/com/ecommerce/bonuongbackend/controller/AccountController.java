package com.ecommerce.bonuongbackend.controller;

import com.ecommerce.bonuongbackend.dto.account.AccountDto;
import com.ecommerce.bonuongbackend.dto.account.AccountResponseDto;
import com.ecommerce.bonuongbackend.dto.account.GetAccountsResponseDto;
import com.ecommerce.bonuongbackend.dto.account.UserAccountDto;
import com.ecommerce.bonuongbackend.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/users")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{role}")
    public GetAccountsResponseDto getAccounts(@PathVariable String role) { return accountService.getAccounts(role); }

//    @PostMapping("")
//    public CreateAccountResponseDto createAccount(@RequestBody CreateAccountDto createAccountDto) {
//        return accountService.createAccount(createAccountDto);
//    }

    @PutMapping("/personal/{userId}")
    public AccountResponseDto updateUserAccount(@PathVariable String userId, @RequestBody UserAccountDto updateUserAccountDto) {
        return accountService.updateUserAccount(userId, updateUserAccountDto);
    }

    @PutMapping("/{id}")
    public AccountResponseDto updateAccount(@PathVariable String id, @RequestBody AccountDto updateAccountDto) {
        return accountService.updateAccount(id, updateAccountDto);
    }

//    @DeleteMapping("/{id}")
//    public AccountResponseDto deleteAccount(@PathVariable String id) {
//        return accountService.deleteAccount(id);
//    }
}
