package com.apex.eqp.inventory.advisors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Validated
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(MethodArgumentNotValidException ex){
    	System.out.println("******************** GlobalExceptionHandler:handleValidationExceptions");
        return ex.getBindingResult().getAllErrors().stream().map(err->err.getDefaultMessage())
                .collect(Collectors.joining(","));
    }
}

