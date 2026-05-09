package com.fastcampus.blog.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionResponse> handler(ApiException apiException) {
        List<String> listErrorMessages = new ArrayList<>(Collections.singletonList(apiException.getErrorMessage()));
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder().errorMessages(listErrorMessages).build();
        return ResponseEntity.status(apiException.getErrorHttpStatus()).body(apiExceptionResponse);
    };
}
