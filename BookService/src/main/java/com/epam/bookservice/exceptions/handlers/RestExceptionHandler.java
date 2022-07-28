package com.epam.bookservice.exceptions.handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.epam.bookservice.dto.ExceptionResponse;
import com.epam.bookservice.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;



@RestControllerAdvice
public class RestExceptionHandler {

    private ExceptionResponse buildExceptionResponse(WebRequest request, HttpStatus status, String errorMessage) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setTimeStamp(new Date().toString());
        exceptionResponse.setDeveloperMessage(errorMessage);

        exceptionResponse.setStatus(status.name());
        exceptionResponse.setPath(request.getDescription(false));
        return exceptionResponse;

    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception, WebRequest request) {

        List<String> errors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildExceptionResponse(request, HttpStatus.BAD_REQUEST, errors.toString()));

    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                             WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildExceptionResponse(request, HttpStatus.NOT_FOUND, exception.getMessage()));

    }

}
