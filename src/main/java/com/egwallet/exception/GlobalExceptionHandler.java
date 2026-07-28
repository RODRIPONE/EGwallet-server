package com.egwallet.exception;

import com.egwallet.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        log.error("ResourceNotFoundException: {}", ex.getMessage());
        
        ApiResponse.ErrorDetails errorDetails = ApiResponse.ErrorDetails.builder()
                .code("RESOURCE_NOT_FOUND")
                .build();
        
        ApiResponse<?> response = ApiResponse.error(
                ex.getMessage(),
                errorDetails,
                HttpStatus.NOT_FOUND.value()
        );
        
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<?>> handleUnauthorizedException(
            UnauthorizedException ex, WebRequest request) {
        log.error("UnauthorizedException: {}", ex.getMessage());
        
        ApiResponse.ErrorDetails errorDetails = ApiResponse.ErrorDetails.builder()
                .code("UNAUTHORIZED")
                .build();
        
        ApiResponse<?> response = ApiResponse.error(
                ex.getMessage(),
                errorDetails,
                HttpStatus.UNAUTHORIZED.value()
        );
        
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequestException(
            BadRequestException ex, WebRequest request) {
        log.error("BadRequestException: {}", ex.getMessage());
        
        ApiResponse.ErrorDetails errorDetails = ApiResponse.ErrorDetails.builder()
                .code("BAD_REQUEST")
                .build();
        
        ApiResponse<?> response = ApiResponse.error(
                ex.getMessage(),
                errorDetails,
                HttpStatus.BAD_REQUEST.value()
        );
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicateResourceException(
            DuplicateResourceException ex, WebRequest request) {
        log.error("DuplicateResourceException: {}", ex.getMessage());
        
        ApiResponse.ErrorDetails errorDetails = ApiResponse.ErrorDetails.builder()
                .code("DUPLICATE_RESOURCE")
                .build();
        
        ApiResponse<?> response = ApiResponse.error(
                ex.getMessage(),
                errorDetails,
                HttpStatus.CONFLICT.value()
        );
        
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(
            ValidationException ex, WebRequest request) {
        log.error("ValidationException: {}", ex.getMessage());
        
        List<ApiResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        if (ex.getErrors() != null) {
            ex.getErrors().forEach(error -> 
                fieldErrors.add(new ApiResponse.ErrorDetails.FieldError(null, error, null))
            );
        }
        
        ApiResponse.ErrorDetails errorDetails = ApiResponse.ErrorDetails.builder()
                .code("VALIDATION_ERROR")
                .details(fieldErrors)
                .build();
        
        ApiResponse<?> response = ApiResponse.error(
                ex.getMessage(),
                errorDetails,
                HttpStatus.BAD_REQUEST.value()
        );
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, WebRequest request) {
        log.error("MethodArgumentNotValidException: {}", ex.getMessage());
        
        List<ApiResponse.ErrorDetails.FieldError> fieldErrors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            Object rejectedValue = ((FieldError) error).getRejectedValue();
            
            fieldErrors.add(new ApiResponse.ErrorDetails.FieldError(fieldName, message, rejectedValue));
        });
        
        ApiResponse.ErrorDetails errorDetails = ApiResponse.ErrorDetails.builder()
                .code("VALIDATION_ERROR")
                .details(fieldErrors)
                .build();
        
        ApiResponse<?> response = ApiResponse.error(
                "Validation failed",
                errorDetails,
                HttpStatus.BAD_REQUEST.value()
        );
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGlobalException(
            Exception ex, WebRequest request) {
        log.error("Unexpected error: ", ex);
        
        ApiResponse.ErrorDetails errorDetails = ApiResponse.ErrorDetails.builder()
                .code("INTERNAL_SERVER_ERROR")
                .build();
        
        ApiResponse<?> response = ApiResponse.error(
                "An unexpected error occurred",
                errorDetails,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
