package com.bank.sha.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WebhookRequest {

    private String order_id;
    private String transaction_status;
    private String fraud_status;
    private BigDecimal gross_amount;
}
