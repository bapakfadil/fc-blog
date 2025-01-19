package com.fastcampus.blog.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionResponse> handler(ApiException e) {
        List<String> errorMessages = new ArrayList<>(Collections.singletonList(e.getMessage()));
        ApiExceptionResponse exceptionResponse = ApiExceptionResponse.builder().ErrorMessages(errorMessages).build();
        return ResponseEntity.status(e.getHttpStatus()).body(exceptionResponse);
    }
}
