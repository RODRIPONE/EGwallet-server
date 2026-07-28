package com.egwallet.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionResponse {
    private Long id;
    private String planType;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer billingPeriodDays;
    private BigDecimal amountPaid;
    private String paymentMethod;
    private String transactionReference;
    private Boolean autoRenew;
    private LocalDateTime cancelledAt;
    private String reasonCancelled;
    private SubscriptionFeaturesResponse features;
    private LocalDateTime createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SubscriptionFeaturesResponse {
        private Integer accountLimit;
        private Integer categoryLimit;
        private Integer budgetLimit;
        private Integer goalLimit;
        private Boolean advancedReports;
        private Boolean aiInsights;
        private Boolean prioritySupport;
    }
}
