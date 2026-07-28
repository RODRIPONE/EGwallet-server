package com.egwallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccountRequest {
    @NotBlank(message = "Account name is required")
    private String name;

    @NotBlank(message = "Account type is required")
    private String type; // CASH, ORANGE_MONEY, MTN_MONEY, WAVE, BANK, OTHER

    private String currency = "XAF";

    @Positive(message = "Initial balance must be positive")
    private BigDecimal initialBalance = BigDecimal.ZERO;

    private String iconEmoji;
    private String colorHex;
    private Boolean isDefault = false;
    private String bankName;
    private String accountNumber;
}
