package com.example.news.controller.exseption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError {

    private String message;

    private String status;

    public ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status.getReasonPhrase().toUpperCase();
    }
}
