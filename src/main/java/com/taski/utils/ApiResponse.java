package com.taski.utils;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private int code;
    private T data;

    public ApiResponse(String status, String message, int code, T data) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.data = data;
    }

    public ApiResponse(String status, String message, int code) {
        this(status, message, code, null);
    }

    // Getters

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}