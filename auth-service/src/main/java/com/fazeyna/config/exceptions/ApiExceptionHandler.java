package com.fazeyna.config.exceptions;


import com.fazeyna.config.exceptions.ApiErrorResponse;
import com.fazeyna.exceptions.DuplicatedFieldException;
import com.fazeyna.exceptions.EntityNotFoundException;
import com.fazeyna.exceptions.RequiredFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler{

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequiredFieldException.class)
    public ResponseEntity<ApiErrorResponse> handleRequiredException(RequiredFieldException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatedFieldException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicatedException(DuplicatedFieldException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
