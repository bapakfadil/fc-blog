package com.fastcampus.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {
    // General API exception handling
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionResponse> handler(ApiException apiException) {
        List<String> listErrorMessages = new ArrayList<>(Collections.singletonList(apiException.getErrorMessage()));
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder().errorMessages(listErrorMessages).build();
        return ResponseEntity.status(apiException.getErrorHttpStatus()).body(apiExceptionResponse);
    };

    // General input method exception handling
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> handler(MethodArgumentNotValidException methodException) {
        List<String> listErrorMessages = new ArrayList<>();
        methodException.getBindingResult().getFieldErrors().forEach(fieldError -> listErrorMessages.add(fieldError.getDefaultMessage()));
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder().errorMessages(listErrorMessages).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiExceptionResponse);
    }

    // General SQL integrity exception handling
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ApiExceptionResponse> handler(SQLIntegrityConstraintViolationException sqlIntegrityException) {
        List<String> listErrorMessages = new ArrayList<>(Collections.singletonList(sqlIntegrityException.getMessage()));
        ApiExceptionResponse apiExceptionResponse = ApiExceptionResponse.builder().errorMessages(listErrorMessages).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiExceptionResponse);
    }
}
