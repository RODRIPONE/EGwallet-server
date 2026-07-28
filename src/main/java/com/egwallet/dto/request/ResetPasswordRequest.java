package com.egwallet.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Reset code is required")
    private String resetCode;

    @NotBlank(message = "New password is required")
    private String newPassword;
}
