package com.microservicesLearning.OrderService.service;

import com.microservicesLearning.OrderService.model.OrderRequest;
import com.microservicesLearning.OrderService.model.OrderResponse;

public interface OrderService {

	long placeOrder(OrderRequest orderRequest);

	OrderResponse getOrderDetails(long orderId);

}
