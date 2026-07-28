package com.egwallet.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> implements Serializable {
    private boolean success;
    private String message;
    private T data;
    private ErrorDetails error;
    private LocalDateTime timestamp;
    private int statusCode;

    public static <T> ApiResponse<T> success(String message, T data, int statusCode) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .statusCode(statusCode)
                .build();
    }

    public static <T> ApiResponse<T> error(String message, ErrorDetails error, int statusCode) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .error(error)
                .timestamp(LocalDateTime.now())
                .statusCode(statusCode)
                .build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorDetails {
        private String code;
        private List<FieldError> details;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class FieldError {
            private String field;
            private String message;
            private Object rejectedValue;
        }
    }
}
