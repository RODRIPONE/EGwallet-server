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
public class BudgetResponse {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String name;
    private BigDecimal limitAmount;
    private BigDecimal spentAmount;
    private String period;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer alertPercentage;
    private Boolean alertEnabled;
    private String colorHex;
    private Boolean isActive;
    private Double percentageUsed;
    private BigDecimal remainingAmount;
    private Integer daysRemaining;
    private BudgetAlertResponse alert;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BudgetAlertResponse {
        private Boolean isTriggered;
        private String type;
        private Integer currentPercentage;
    }
}
