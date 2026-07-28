package com.egwallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionRequest {
    @Positive(message = "Account ID must be positive")
    private Long accountId;

    @Positive(message = "Category ID must be positive")
    private Long categoryId;

    @NotBlank(message = "Transaction type is required")
    private String type; // INCOME, EXPENSE, TRANSFER

    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    private String description;
    private String notes;
    private LocalDate transactionDate;
    private LocalTime transactionTime;
    private String recipientName;
    private String recipientPhone;
    private String paymentMethod;
    private String referenceNumber;
    private String receiptUrl;
    private Boolean isRecurring = false;
    private String recurrencePattern;
    private LocalDate recurrenceEndDate;
    private Long relatedAccountId; // For TRANSFER type
}
