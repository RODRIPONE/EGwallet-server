package com.egwallet.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String token;
    private String refreshToken;
    private Long expiresIn;
    private String tokenType = "Bearer";
    private SubscriptionResponse subscription;
    private LocalDateTime lastLoginAt;
}
