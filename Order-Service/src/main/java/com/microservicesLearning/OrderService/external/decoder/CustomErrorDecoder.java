package com.microservicesLearning.OrderService.external.decoder;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservicesLearning.OrderService.exception.CustomException;
import com.microservicesLearning.OrderService.external.response.ErrorResponse;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder{

	@Override
	public Exception decode(String methodKey, Response response) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		log.info(response);
		
		log.info("::{}",response.request().url());
		log.info("::{}",response.request().headers());
		
		try {
			ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(),ErrorResponse.class);
			return new CustomException(errorResponse.getErrorMessage(), errorResponse.getErrorCode(),408);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR,500);
		}
	}
	
}
