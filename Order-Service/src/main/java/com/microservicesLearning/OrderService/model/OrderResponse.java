package com.microservicesLearning.OrderService.model;

import java.time.Instant;

import com.microservicesLearning.OrderService.external.response.PaymentResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
	private long orderId;
	private Instant orderDate;
	private String orderStatus;
	private long amount;
	private ProductDetails productDetails;
	private PaymentResponse paymentResponse;
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ProductDetails {
		
		private String productName;
		private long productId;
		private long price;
		private long quantity;
		
	}
}
