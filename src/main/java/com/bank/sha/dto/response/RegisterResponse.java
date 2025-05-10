package com.bank.sha.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RegisterResponse {

    private String id;
    private String name;
    private String email;
    private String username;
    private String pin;
    private boolean verified;
    private String ktp;
    private String profilePicture;
    private BigDecimal balance;
    private String cardNumber;
    private String token;
    private String tokenType;
    private long expiresIn;

}
