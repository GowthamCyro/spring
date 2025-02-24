package com.microservicesLearning.OrderService.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservicesLearning.OrderService.external.response.PaymentResponse;
import com.microservicesLearning.OrderService.model.PaymentRequest;

@FeignClient(name = "payment",url = "${microservices.payment}")
public interface PaymentService {
	
	@PostMapping("/doPayment")
	public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);
	
	@GetMapping("/{orderId}")
	public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable long orderId);
	
}
