package com.egwallet.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class CreateBudgetRequest {
    @Positive(message = "Category ID must be positive")
    private Long categoryId;

    @NotBlank(message = "Budget name is required")
    private String name;

    @Positive(message = "Limit amount must be positive")
    private BigDecimal limitAmount;

    @NotBlank(message = "Period is required")
    private String period; // DAILY, WEEKLY, MONTHLY, YEARLY

    private LocalDate startDate;
    private LocalDate endDate;

    @Min(value = 1, message = "Alert percentage must be at least 1")
    @Max(value = 100, message = "Alert percentage must not exceed 100")
    private Integer alertPercentage = 80;

    private Boolean alertEnabled = true;
    private String colorHex;
}
