package com.microservicesLearning.PaymentService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicesLearning.PaymentService.model.PaymentRequest;
import com.microservicesLearning.PaymentService.model.PaymentResponse;
import com.microservicesLearning.PaymentService.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/doPayment")
	public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest){
		return new ResponseEntity<>(
				paymentService.doPayment(paymentRequest),HttpStatus.OK
				);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<PaymentResponse> getPaymentDetailsByOrderId(@PathVariable long orderId){
		PaymentResponse paymentResponse = paymentService.getPaymentDetailsByOrderId(orderId);
		return new ResponseEntity<>(paymentResponse,HttpStatus.OK);
	}
	
}
