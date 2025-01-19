package com.fastcampus.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    // To handle error from API Exception
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionResponse> handler(ApiException e) {
        List<String> errorMessages = new ArrayList<>(Collections.singletonList(e.getMessage()));
        ApiExceptionResponse exceptionResponse = ApiExceptionResponse.builder().ErrorMessages(errorMessages).build();
        return ResponseEntity.status(e.getHttpStatus()).body(exceptionResponse);
    }

    // To handle error sent by API Argument
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<ApiExceptionResponse> handler(MethodArgumentNotValidException exceptionArgument) {
        List<String> errorMessages = new ArrayList<>();
        exceptionArgument.getFieldErrors().forEach(fieldError -> errorMessages.add(fieldError.getDefaultMessage()));
        ApiExceptionResponse exceptionResponse = ApiExceptionResponse.builder().ErrorMessages(errorMessages).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}
