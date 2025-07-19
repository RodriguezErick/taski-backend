package com.taski.utils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ApiResponse<Map<String, String>> response = new ApiResponse<>(
                "error",
                "Validation failed",
                HttpStatus.BAD_REQUEST.value(),
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<Object>> handleDatabaseError(DataAccessException ex) {
        String message = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();

        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                "Database error: " + message,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleOtherErrors(Exception ex) {
        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                "Unexpected error: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}