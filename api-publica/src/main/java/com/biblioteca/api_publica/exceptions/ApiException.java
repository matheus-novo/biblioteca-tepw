package com.biblioteca.api_publica.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    public final HttpStatus status;
    public final String key;

    public ApiException(HttpStatus status, String key, String message) {
        super(message);
        this.status = status;
        this.key = key;
    }
}