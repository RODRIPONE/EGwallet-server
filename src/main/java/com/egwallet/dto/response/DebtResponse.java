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
public class DebtResponse {
    private Long id;
    private String creditorName;
    private String creditorPhone;
    private String type;
    private BigDecimal amount;
    private BigDecimal remainingAmount;
    private BigDecimal paidAmount;
    private LocalDate dueDate;
    private String description;
    private String status;
    private BigDecimal interestRate;
    private Boolean isArchived;
    private Double percentagePaid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
