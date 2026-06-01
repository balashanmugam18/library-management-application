package com.librarymanagement.application.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.librarymanagement.application.model.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException (CustomException exception){
        ErrorResponse response = new ErrorResponse();
        response.setCode("HttpStatus.NOT_FOUND");
        response.setMessage(exception.getMessage());
        return ResponseEntity.badRequest()
        .body(response);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRunTimeException (RuntimeException exception){
        ErrorResponse response = new ErrorResponse();
        response.setCode("HttpStatus.NOT_FOUND");
        response.setMessage(exception.getMessage());
        return ResponseEntity.badRequest()
        .body(response);

    }

}
