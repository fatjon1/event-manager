package com.fatjon.eventmanager.advice;

import com.fatjon.eventmanager.exception.FieldCantBeNullException;
import com.fatjon.eventmanager.exception.UserNotFoundException;
import com.fatjon.eventmanager.exception.UsernameExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundException(UserNotFoundException userNotFoundException){
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<String> usernameExistsException(UsernameExistsException usernameExistsException){
        return new ResponseEntity<>(usernameExistsException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FieldCantBeNullException.class)
    public ResponseEntity<String> fieldCantBeNullException(FieldCantBeNullException fieldCantBeNullException){
        return new ResponseEntity<>(fieldCantBeNullException.getMessage() , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException){
        return new ResponseEntity<>(httpRequestMethodNotSupportedException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?>httpMessageNotReadableException(HttpMessageNotReadableException ex){
        return new ResponseEntity<>("JSON parse error: Invalid numeric value: Leading zeroes not allowed;",HttpStatus.BAD_REQUEST);
    }*/




}
