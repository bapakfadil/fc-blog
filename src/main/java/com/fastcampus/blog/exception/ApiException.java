package com.fastcampus.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private final String message;
    @Getter
    private final HttpStatus httpStatus;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.httpStatus = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
