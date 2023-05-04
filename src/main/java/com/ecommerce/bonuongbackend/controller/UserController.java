package com.ecommerce.bonuongbackend.controller;

import com.ecommerce.bonuongbackend.dto.ResponseDto;
import com.ecommerce.bonuongbackend.dto.user.*;
import com.ecommerce.bonuongbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public GetUserResponseDto getUser(HttpServletRequest request) { return userService.getUser(request); }

    @PostMapping("/register")
    public ResponseDto register(@RequestBody RegisterDto registerDto) {
        return userService.register(registerDto);
    }

    @PostMapping("/activate")
    public ResponseDto activate(@RequestBody ActivateDto activateDto) {
        return userService.activate(activateDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    @PostMapping("/send-mail-reset-password")
    public ResponseDto sendMailResetPassword(@RequestBody SendMailResetPasswordDto sendMailResetPasswordDto) {
        return userService.sendMailResetPassword(sendMailResetPasswordDto);
    }

    @PostMapping("/reset-password")
    public ResponseDto resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        return userService.resetPassword(resetPasswordDto);
    }

    @PostMapping("/contact")
    public ResponseDto contact(@RequestBody ContactDto contactDto) {
        return userService.contact(contactDto);
    }
}
