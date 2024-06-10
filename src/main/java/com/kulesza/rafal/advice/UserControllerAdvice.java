package com.kulesza.rafal.advice;

import com.kulesza.rafal.exception.InvalidUserDataException;
import com.kulesza.rafal.exception.UserIsNotUniqueException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(InvalidUserDataException.class)
    public ResponseEntity<String> handleInvalidUserDataException(InvalidUserDataException exception) {
        return ResponseEntity.badRequest()
                .body("Invalid User data. Exception message: " + exception.getLocalizedMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest()
                .body("Invalid HTTP message. Exception message: " + exception.getLocalizedMessage());
    }

    @ExceptionHandler(UserIsNotUniqueException.class)
    public ResponseEntity<String> handleUserIsNotUniqueException() {
        return ResponseEntity.badRequest()
                .body("Given username already exist.");
    }
