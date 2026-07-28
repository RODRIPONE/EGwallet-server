package com.egwallet.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
    private Long id;
    private Long accountId;
    private Long categoryId;
    private String categoryName;
    private String type;
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
    private Boolean isRecurring;
    private String recurrencePattern;
    private LocalDate recurrenceEndDate;
    private Boolean isReconciled;
    private String status;
    private Long relatedAccountId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
