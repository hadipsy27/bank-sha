package com.bank.sha.controller;

import com.bank.sha.dto.request.LoginUserDto;
import com.bank.sha.dto.request.RegisterUserDto;
import com.bank.sha.dto.response.LoginResponse;
import com.bank.sha.entity.User;
import com.bank.sha.entity.UserPrincipal;
import com.bank.sha.service.AuthService;
import com.bank.sha.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterUserDto registerUserDto) throws Exception{
        return authService.registerUser(registerUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);
        UserDetails userDetails = new UserPrincipal(authenticatedUser);
        String jwtToken = jwtService.generateToken(userDetails);
        LoginResponse response = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(response);
    }
}
