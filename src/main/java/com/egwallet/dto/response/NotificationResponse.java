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
public class NotificationResponse {
    private Long id;
    private String type;
    private String title;
    private String message;
    private String entityType;
    private Long entityId;
    private Boolean isRead;
    private LocalDateTime readAt;
    private String actionUrl;
    private LocalDateTime createdAt;
}
