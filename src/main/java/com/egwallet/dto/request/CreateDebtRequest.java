package com.egwallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDebtRequest {
    @NotBlank(message = "Creditor name is required")
    private String creditorName;

    private String creditorPhone;

    @NotBlank(message = "Debt type is required")
    private String type; // BORROWED, LENT

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private LocalDate dueDate;
    private String description;
    private BigDecimal interestRate;
}
