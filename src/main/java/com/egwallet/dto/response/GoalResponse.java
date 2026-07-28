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
public class GoalResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private LocalDate targetDate;
    private String iconEmoji;
    private String colorHex;
    private String priority;
    private String status;
    private Double percentageAchieved;
    private BigDecimal remainingAmount;
    private Integer daysRemaining;
    private Integer contributionCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
