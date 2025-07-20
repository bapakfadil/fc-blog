package com.bapakfadil.blog.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionResponse> handler(ApiException exception) {

        List<String> errorMessages = new ArrayList<>(Collections.singletonList(exception.getMessage()));

        ApiExceptionResponse response = ApiExceptionResponse.builder().ErrorMessages(errorMessages).build();
        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }
}
