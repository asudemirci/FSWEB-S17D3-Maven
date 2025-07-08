package com.workintech.zoo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ZooGlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ZooErrorResponse> handlerZooException(ZooException exception) {
        log.error("Zoo Exception: {}", exception.getMessage());

        HttpStatus status = exception.getHttpStatus() != null ? exception.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;

        ZooErrorResponse error = new ZooErrorResponse(
                status.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler
    public ResponseEntity<ZooErrorResponse> handleGenericException(Exception exception) {
        log.error("Generic Exception: {}", exception.getMessage());

        ZooErrorResponse error = new ZooErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
