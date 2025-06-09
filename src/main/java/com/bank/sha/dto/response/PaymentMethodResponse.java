package com.bank.sha.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentMethodResponse {
    private String code;
    private String name;
    private String thumbnail;
    private String status;
}
