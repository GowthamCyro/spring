package com.microservicesLearning.PaymentService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicesLearning.PaymentService.entity.TransactionDetails;


@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long>{
	
	TransactionDetails findByOrderId(long orderId);

}
