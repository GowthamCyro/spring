package com.microservicesLearning.PaymentService.service;

import com.microservicesLearning.PaymentService.model.PaymentRequest;
import com.microservicesLearning.PaymentService.model.PaymentResponse;

public interface PaymentService {

	long doPayment(PaymentRequest paymentRequest);

	PaymentResponse getPaymentDetailsByOrderId(long orderId);

}
