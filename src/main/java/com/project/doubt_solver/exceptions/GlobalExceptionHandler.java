package com.project.doubt_solver.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<Object,Object>> handleResourceNotFoundException(ResourceNotFoundException ex){
        String message=ex.getMessage();
        Map<Object,Object> map=new HashMap<>();
        map.put("message",message);
        map.put("status",false);
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Map<String,String>>(errors,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // Check if it's an email duplicate error (optional: you can inspect the exception message)
        Map<String,String> map=new HashMap<>();
        if (ex.getCause() != null && ex.getCause().getMessage() != null &&
                ex.getCause().getMessage().contains("Duplicate entry")) {

                 map.put("message","Duplicate Entry Not Allowed");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)  // 409 Conflict
                    .body(map);
        }


        // For other DataIntegrityViolationException cases
        map.put("message","Invalid request data.");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(map);
    }

}
