package com.microservicesLearning.OrderService.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservicesLearning.OrderService.entity.Order;
import com.microservicesLearning.OrderService.exception.CustomException;
import com.microservicesLearning.OrderService.external.client.PaymentService;
import com.microservicesLearning.OrderService.external.client.ProductService;
import com.microservicesLearning.OrderService.external.response.PaymentResponse;
import com.microservicesLearning.OrderService.model.OrderRequest;
import com.microservicesLearning.OrderService.model.OrderResponse;
import com.microservicesLearning.OrderService.model.PaymentRequest;
import com.microservicesLearning.OrderService.model.ProductResponse;
import com.microservicesLearning.OrderService.repository.OrderRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${microservices.product}")
	private String productServiceUrl;
	
	@Value("${microservices.payment}")
	private String paymentServiceUrl;
	
	@Override
	public long placeOrder(OrderRequest orderRequest) {
		
		// Order Entity -> Save the data with Status Order Created
		// Product Service -> Block Products (Reduce the quantity)
		// Payment Service -> Payments -> (Success -> Complete , Failed -> Cancelled)
		
		log.info("Started Placing Order....");
		
		productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());
		
		log.info("Placing Order Request: {}",orderRequest);
		
		Order order = Order.builder()
				.productId(orderRequest.getProductId())
				.amount(orderRequest.getTotalAmount())
				.quantity(orderRequest.getQuantity())
				.orderStatus("CREATED")
				.orderDate(Instant.now())
				.build();
		
		order = orderRepository.save(order);
		
		log.info("Calling Payment Service to complete the payment");
		
		PaymentRequest paymentRequest = PaymentRequest.builder()
										.orderId(order.getId())
										.amount(order.getAmount())
										.paymentMode(orderRequest.getPaymentMode())
										.build();
		
		String orderStatus = null;
		try {
			paymentService.doPayment(paymentRequest);
			log.info("Payment done Successfully. Changing the Order Status to Completed");
			orderStatus = "PLACED";
		} catch (Exception e) {
			log.error("Error occurred in payment. Changing the order status to Failed");
			orderStatus = "PAYMENT_FAILED";
		}
		
		order.setOrderStatus(orderStatus);
		orderRepository.save(order);
		
		log.info("Order Placed Successfully with Order Id: {}",order.getId());
		return order.getId();
	}

	@Override
	public OrderResponse getOrderDetails(long orderId) {
		log.info("Get Order Details for Order Id : {}",orderId);
		
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomException("Order Not Found For given OrderId",HttpStatus.BAD_REQUEST,400));
		
		log.info("Invoking Product Service to fetch the product for id: {}",order.getProductId());
		
		ProductResponse productResponse = restTemplate.getForObject(productServiceUrl + order.getProductId(),ProductResponse.class);
		
//		ProductResponse productResponse = productService.getProductById(order.getProductId()).getBody();
		
		log.info("Get Payment Information from the payment Serivce for OrderId : {}",order.getId());
		
//		PaymentResponse paymentResponse = restTemplate.getForObject("http://PAYMENT-SERVICE/payment/"+order.getId(),PaymentResponse.class);
		
		PaymentResponse paymentResponse = paymentService.getPaymentDetailsByOrderId(order.getId()).getBody();
		
		log.info(paymentResponse);
		
		OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder()
										.productName(productResponse.getProductName())
										.productId(productResponse.getProductId())
										.build();
		
		OrderResponse orderResponse = OrderResponse.builder()
				.orderId(order.getId())
				.orderDate(order.getOrderDate())
				.orderStatus(order.getOrderStatus())
				.amount(order.getAmount())
				.productDetails(productDetails)
				.paymentResponse(paymentResponse)
				.build();
		
		return orderResponse;
	}

}
