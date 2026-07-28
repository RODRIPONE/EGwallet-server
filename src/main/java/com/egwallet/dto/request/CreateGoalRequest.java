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
public class CreateGoalRequest {
    @NotBlank(message = "Goal name is required")
    private String name;

    private String description;

    @Positive(message = "Target amount must be positive")
    private BigDecimal targetAmount;

    private LocalDate targetDate;
    private String iconEmoji;
    private String colorHex;
    private String priority = "MEDIUM"; // LOW, MEDIUM, HIGH
}
