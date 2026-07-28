package com.egwallet.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DebtPaymentResponse {
    private Long id;
    private Long debtId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String notes;
    private String receiptUrl;
    private LocalDateTime createdAt;
}
