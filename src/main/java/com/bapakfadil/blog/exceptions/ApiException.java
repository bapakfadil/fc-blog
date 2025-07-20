package com.bapakfadil.blog.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private final String message;
    @Getter
    private final HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
