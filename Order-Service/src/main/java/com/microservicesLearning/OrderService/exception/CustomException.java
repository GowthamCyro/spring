package com.microservicesLearning.OrderService.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class CustomException  extends RuntimeException{
	
	private HttpStatus errorCode;
	private int status;
	
	public CustomException(String message,HttpStatus errorCode,int status) {
		super(message);
		this.errorCode = errorCode;
		this.status = status;
	}

}
