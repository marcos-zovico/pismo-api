package io.pismo.pismoapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponse> onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
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
}
