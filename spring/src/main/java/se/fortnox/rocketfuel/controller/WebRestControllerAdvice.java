package se.fortnox.rocketfuel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class WebRestControllerAdvice {
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Object> handleNotImplemented(RuntimeException ex) {
        var responseObject = new HashMap<>();
        responseObject.put("message", "Not implemented");
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseObject);
    }
}
