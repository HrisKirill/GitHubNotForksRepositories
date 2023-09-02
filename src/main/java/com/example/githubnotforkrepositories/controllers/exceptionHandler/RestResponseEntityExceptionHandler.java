package com.example.githubnotforkrepositories.controllers.exceptionHandler;

import com.example.githubnotforkrepositories.exceptions.UnsupportedMediaTypeException;
import com.example.githubnotforkrepositories.exceptions.UserNotFoundException;
import com.example.githubnotforkrepositories.models.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFoundException(Exception e) {
        Response response = new Response(e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ResponseEntity<Response> handleUnsupportedMediaTypeException(Exception e) {
        Response response = new Response(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(response, responseHeaders, response.getStatus());
    }

}
