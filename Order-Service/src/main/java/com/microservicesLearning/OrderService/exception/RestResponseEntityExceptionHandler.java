package com.microservicesLearning.OrderService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.microservicesLearning.OrderService.external.response.ErrorResponse;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception){
		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorMessage(exception.getMessage())
				.errorCode(exception.getErrorCode())
				.build();
		
		return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(exception.getStatus()));
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ErrorResponse> genericExceptionHandler(Exception exception){
		ErrorResponse errorResponse = ErrorResponse.builder()
				.errorMessage(exception.getMessage())
				.errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
				.build();
		
		return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
