package com.egwallet.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsResponse {
    private String period;
    private LocalDate startDate;
    private LocalDate endDate;
    private SummaryResponse summary;
    private List<CategoryBreakdownResponse> incomeBreakdown;
    private List<CategoryBreakdownResponse> expenseBreakdown;
    private List<DailyBalanceResponse> dailyBalance;
    private MonthlyComparisonResponse monthlyComparison;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SummaryResponse {
        private BigDecimal totalIncome;
        private BigDecimal totalExpense;
        private BigDecimal netBalance;
        private Double savingsRate;
        private Integer transactionCount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategoryBreakdownResponse {
        private String categoryName;
        private BigDecimal amount;
        private Double percentage;
        private Integer transactionCount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DailyBalanceResponse {
        private LocalDate date;
        private BigDecimal balance;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MonthlyComparisonResponse {
        private BigDecimal current;
        private BigDecimal previous;
        private Double changePercentage;
    }
}
