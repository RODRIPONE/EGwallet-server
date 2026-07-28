package com.egwallet.dto.request;

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
public class AddDebtPaymentRequest {
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private LocalDate paymentDate;
    private String paymentMethod;
    private String notes;
    private String receiptUrl;
}
