package com.epam.libraryservice.exception.exceptionhandler;


import com.epam.libraryservice.dto.ExceptionResponse;
import com.epam.libraryservice.exception.BookException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
	
	@ExceptionHandler(value = HttpClientErrorException.class)
	public ResponseEntity<ExceptionResponse> handleHttpClientErrorException(HttpClientErrorException exception,
			WebRequest request) throws JsonProcessingException {

		return ResponseEntity.status(exception.getStatusCode())
				.body(new ObjectMapper().readValue(exception.getResponseBodyAsString(), ExceptionResponse.class));

	}


	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponse> handleBookException(BookException exception,
																			 WebRequest request) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(buildExceptionResponse(request,HttpStatus.OK, exception.getMessage()));

	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception, WebRequest request) {

		List<String> errors = new ArrayList<>();
		exception.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(buildExceptionResponse(request, HttpStatus.BAD_REQUEST, errors.toString()));

	}

//	@ExceptionHandler(value = RuntimeException.class)
//	public ResponseEntity<ExceptionResponse> handlerRuntimeException(BookException exception,
//																 WebRequest request) {
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//				.body(buildExceptionResponse(request,HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
//
//	}

	    @ExceptionHandler(value = FeignException.class)
    public ResponseEntity<ExceptionResponse> handleFeignException(FeignException exception) throws IOException {
        return ResponseEntity.status(HttpStatus.valueOf(exception.status()))
                .body(new ObjectMapper().readValue(exception.contentUTF8(), ExceptionResponse.class));
    }



}
