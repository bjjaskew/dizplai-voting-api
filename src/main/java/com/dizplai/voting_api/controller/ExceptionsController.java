package com.dizplai.voting_api.controller;

import com.dizplai.voting_api.exceptions.NotFoundException;
import com.dizplai.voting_api.exceptions.PollTooLargeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleRequestObjectErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::fieldErrorToString)
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PollTooLargeException.class)
    public ResponseEntity<String> handlePollTooLargeException(PollTooLargeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private String fieldErrorToString(FieldError error) {
        return error.getField() + " " + error.getDefaultMessage();
    }
}
