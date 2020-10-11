package io.pismo.pismoapi.controller;

import io.pismo.pismoapi.exception.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse();
        ex.getBindingResult()
               .getAllErrors()
                .forEach(e -> {
                    FieldError fieldError = (FieldError) e;
                    response.getErrors()
                            .add(new Error(fieldError.getField(), fieldError.getDefaultMessage()));
                });
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> onEntityNotFoundException(EntityNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse();
        response.getErrors().add(new Error(ex.getMessage()));
        return ResponseEntity.badRequest().body(response);
    }
}
