package com.MyAudioLibrairy.web.exceptions;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException e){
        return e.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e){
        return e.getMessage();
    }
    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentNotValidException e){
        return " Le parametre" + e.getName() +" a une valeur incorrecte : " + e.getValue();
    }*/

    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePropertyReferenceException(PropertyReferenceException e){
        return "La propriété " + e.getPropertyName()+ " demandée n'existe pas ";

    }
    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleEntityExistsException(EntityExistsException e){
        return e.getMessage();
    }
}
