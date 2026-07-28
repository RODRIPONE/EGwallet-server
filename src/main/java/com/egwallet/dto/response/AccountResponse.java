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
public class AccountResponse {
    private Long id;
    private String name;
    private String type;
    private String currency;
    private BigDecimal initialBalance;
    private BigDecimal currentBalance;
    private String iconEmoji;
    private String colorHex;
    private Boolean isDefault;
    private Boolean isArchived;
    private String bankName;
    private String accountNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
