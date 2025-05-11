package com.bank.sha.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class StoreResponse {
    private Long id;
    private Long userId;
    private Long transactionTypeId;
    private Long paymentMethodId;
    private Long productId;
    private BigDecimal amount;
    private String transactionCode;
    private String description;
    private String status;
}
