package com.bank.sha.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferBwaResponseDto {
    private String recipientName;
    private String recipientCardNumber;
    private String transactionCode;
    private String status;
    private Integer amount;
}
