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
public class CreateCategoryRequest {
    @NotBlank(message = "Category name is required")
    private String name;

    @NotBlank(message = "Category type is required")
    private String type; // INCOME, EXPENSE

    private String iconEmoji;
    private String colorHex;
}
