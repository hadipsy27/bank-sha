package com.bank.sha.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreDto {

    @NotNull(message = "Amount is required")
    @Min(value = 10000, message = "Minimum amount is 10000")
    private Integer amount;

    @NotBlank(message = "PIN is required")
    @Pattern(regexp = "^[0-9]{6,}$", message = "PIN must be at least 6 digits")
    private String pin;

    @NotBlank(message = "Payment method is required")
    @Pattern(regexp = "^(bca_va|bri_va|bni_va)$", message = "Payment method must be one of: bca_va, bri_va, bni_va")
    private String paymentMethodCode;
}
