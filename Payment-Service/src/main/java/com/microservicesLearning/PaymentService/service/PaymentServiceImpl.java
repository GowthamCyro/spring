package com.microservicesLearning.PaymentService.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicesLearning.PaymentService.entity.TransactionDetails;
import com.microservicesLearning.PaymentService.model.PaymentMode;
import com.microservicesLearning.PaymentService.model.PaymentRequest;
import com.microservicesLearning.PaymentService.model.PaymentResponse;
import com.microservicesLearning.PaymentService.repository.TransactionDetailsRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private TransactionDetailsRepository transactionDetailsRepository;

	@Override
	public long doPayment(PaymentRequest paymentRequest) {
		log.info("Recording Payment Details: {}",paymentRequest);
		
		TransactionDetails transactionDetails = TransactionDetails.builder()
				.paymentDate(Instant.now())
				.orderId(paymentRequest.getOrderId())
				.amount(paymentRequest.getAmount())
				.referenceNumber(paymentRequest.getReferenceNumber())
				.paymentMode(paymentRequest.getPaymentMode().name())
				.paymentStatus("SUCCESS")
				.build();
		
		transactionDetailsRepository.save(transactionDetails);
		log.info("Transaction Completed with Id: {}",transactionDetails.getId());
		return transactionDetails.getId();
		
	}

	@Override
	public PaymentResponse getPaymentDetailsByOrderId(long orderId) {
		log.info("Start of get Payment Details By OrderId : {}",orderId);
		
		TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(orderId);
		
		log.info(transactionDetails);
		
		PaymentResponse paymentResponse = PaymentResponse.builder()
											.paymentId(transactionDetails.getId())
											.status(transactionDetails.getPaymentStatus())
											.paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
											.amount(transactionDetails.getAmount())
											.paymentDate(transactionDetails.getPaymentDate())
											.orderId(transactionDetails.getOrderId())
											.build();
		
		
		return paymentResponse;
	}

}
