package com.microservicesLearning.OrderService.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Order {
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
}
