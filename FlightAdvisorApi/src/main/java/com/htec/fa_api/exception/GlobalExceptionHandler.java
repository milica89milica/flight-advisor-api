package com.htec.fa_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler { //todo

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorMessage> fieldValidationFailed(ConstraintViolationException ex, WebRequest request) {
        final Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        String message = "";
        String className = "";
        for (ConstraintViolation constraintViolation : constraintViolations) {
            message += constraintViolation.getMessage() + " ";
            className += constraintViolation.getRootBeanClass().getSimpleName();
        }
        ErrorMessage errorMessage = new ErrorMessage(message, HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpException.class})
    public ResponseEntity<ErrorMessage> handleHttpException(HttpException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(ex.getData().toString(), HttpStatus.BAD_REQUEST.toString(), "");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


}
