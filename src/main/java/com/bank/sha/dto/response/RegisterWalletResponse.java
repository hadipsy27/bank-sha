package com.bank.sha.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RegisterWalletResponse {
    private BigDecimal balance;
    private String cardNumber;
    private String pin;
}
