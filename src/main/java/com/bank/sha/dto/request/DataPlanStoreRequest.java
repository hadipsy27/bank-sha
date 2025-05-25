package com.bank.sha.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataPlanStoreRequest {

    @NotNull(message = "Data plan Id is required")
    private Integer dataPlanId;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "PIN is required")
    @Pattern(regexp = "^[0-9]{6,}$", message = "PIN must be at least 6 digits")
    private String pin;

}
