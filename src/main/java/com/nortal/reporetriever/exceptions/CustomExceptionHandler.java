package com.nortal.reporetriever.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotAcceptableStatusException.class)
    protected ResponseEntity<Map<String, String>> notAcceptable(NotAcceptableStatusException ex) {
        return status(NOT_ACCEPTABLE)
                .body(Map.of("status", Integer.toString(NOT_ACCEPTABLE.value()), "Message", "The resource is not available in requested format"));
    }

    @ExceptionHandler(DataNotAvailableException.class)
    protected ResponseEntity<Map<String, String>> notFound(DataNotAvailableException ex) {
        return status(NOT_FOUND)
                .body(Map.of("status", Integer.toString(NOT_FOUND.value()), "Message", "There is no data for given user"));
    }

}
