package com.bank.sha.controller;

import com.bank.sha.dto.RegisterUserDto;
import com.bank.sha.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class AuthController {

    @Autowired
    private AuthService authService;
    @PostMapping("/register")
    public String register(@RequestBody RegisterUserDto registerUserDto) throws Exception{
        return authService.registerUser(registerUserDto);
    }
}
