package com.microservicesLearning.ProductService.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservicesLearning.ProductService.entity.Product;
import com.microservicesLearning.ProductService.exception.ProductServiceCustomException;
import com.microservicesLearning.ProductService.model.ProductRequest;
import com.microservicesLearning.ProductService.model.ProductResponse;
import com.microservicesLearning.ProductService.repository.ProductRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public long addProduct(ProductRequest productDetails) {
		log.info("Adding Product....");
		
		Product product = Product.builder()
							.productName(productDetails.getName())
							.price(productDetails.getPrice())
							.quantity(productDetails.getQuantity())
							.build();
		
		productRepository.save(product);
		
		log.info("Product Created ....");
		
		return product.getProductId();
	}

	@Override
	public ProductResponse getProductById(long productId) {
		log.info("Get the Product for Product Id");
		
		
		
		Product product = productRepository.findById(productId)
							.orElseThrow(() -> new ProductServiceCustomException("Product with given Id Not Found",HttpStatus.NOT_FOUND));
		
		log.info("Product Found ....");
		
		ProductResponse productResponse = new ProductResponse();
		BeanUtils.copyProperties(product, productResponse);  // only works when both have the same properties
		
		log.info("Returning product: {}", productResponse);
		
		return productResponse;
		
	}

	@Override
	public void reduceQunatity(long productId, long quantity) {
		log.info("Reduce Quantity {} for Id:{}",quantity,productId);
		
		Product product = productRepository.findById(productId)
				.orElseThrow(()-> new ProductServiceCustomException("Product with given Id Not Found",HttpStatus.NOT_FOUND));
		
		if(product.getQuantity()<quantity) {
			throw new ProductServiceCustomException("Product does not have Enough Quantity",HttpStatus.INSUFFICIENT_STORAGE);
		}
		
		product.setQuantity(product.getQuantity()-quantity);
		productRepository.save(product);
		log.info("Product Quantity updated Successfully");
		
	}

}
