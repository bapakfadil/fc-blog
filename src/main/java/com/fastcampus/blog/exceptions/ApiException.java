package com.fastcampus.blog.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    @Getter
    private final String errorMessage;
    @Getter
    private final HttpStatus errorHttpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.errorHttpStatus = httpStatus;
        this.errorMessage = message;
    }
}
