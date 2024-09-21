package com.example.news.exseption.model;


import org.springframework.http.HttpStatus;

public class ApiError {
    private final String message;
    private String status;

    public ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status.getReasonPhrase().toUpperCase();
    }
}
