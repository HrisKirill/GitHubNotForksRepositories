package com.example.githubnotforkrepositories.controllers.exceptionHandler;

import com.example.githubnotforkrepositories.exceptions.UnsupportedMediaTypeException;
import com.example.githubnotforkrepositories.exceptions.UserNotFoundException;
import com.example.githubnotforkrepositories.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFoundException(Exception e) {
        Response response = new Response(e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ResponseEntity<Response> handleException(Exception e) {
        Response response = new Response(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
