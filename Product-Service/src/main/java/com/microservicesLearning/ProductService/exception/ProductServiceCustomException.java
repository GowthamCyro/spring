package com.microservicesLearning.ProductService.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ProductServiceCustomException extends RuntimeException{
	
	private HttpStatus errorCode;
	
	public ProductServiceCustomException(String message,HttpStatus errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
}
