package com.bank.sha.controller;

import com.bank.sha.dto.request.LoginUserDto;
import com.bank.sha.dto.request.RegisterUserDto;
import com.bank.sha.dto.response.LoginResponse;
import com.bank.sha.dto.response.RegisterResponse;
import com.bank.sha.dto.response.RegisterWalletResponse;
import com.bank.sha.entity.User;
import com.bank.sha.entity.UserPrincipal;
import com.bank.sha.service.AuthService;
import com.bank.sha.service.JwtService;
import com.bank.sha.service.UserService;
import com.bank.sha.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;
    private JwtService jwtService;
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterUserDto registerUserDto) throws Exception{
        User registeredUser = authService.registerUser(registerUserDto);
        if (registeredUser == null) {
            return ResponseUtil.generateResponse("Failed register", HttpStatus.BAD_REQUEST, null);
        }

        User userByEmail = userService.findUserAndWalletByEmail(registeredUser.getEmail());
        RegisterWalletResponse walletResponse = Optional.ofNullable(userByEmail.getWallets())
                .orElse(Collections.emptyList())
                .stream()
                .findFirst()
                .map(wallet -> RegisterWalletResponse.builder()
                        .pin(wallet.getPin())
                        .balance(wallet.getBalance())
                        .cardNumber(wallet.getCardNumber())
                        .build())
                .orElse(null);

        UserDetails userPrincipal = new UserPrincipal(registeredUser);
        String jwtToken = jwtService.generateToken(userPrincipal);

        RegisterResponse response = RegisterResponse.builder()
                .id(String.valueOf(userByEmail.getId()))
                .name(userByEmail.getName())
                .username(userByEmail.getUsername())
                .email(userByEmail.getEmail())
                .pin(walletResponse != null ? walletResponse.getPin() : null)
                .verified(userByEmail.isVerified())
                .profilePicture(userByEmail.getProfilePicture())
                .ktp(userByEmail.getKtp())
                .balance(walletResponse != null ? walletResponse.getBalance() : null)
                .cardNumber(walletResponse != null ? walletResponse.getCardNumber() : null)
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .tokenType("Bearer")
                .build();

        return ResponseUtil.generateResponse("Success register", HttpStatus.OK, response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);
        UserDetails userDetails = new UserPrincipal(authenticatedUser);
        String jwtToken = jwtService.generateToken(userDetails);
        LoginResponse response = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseUtil.generateResponse("Success login", HttpStatus.OK, response);
    }
}
