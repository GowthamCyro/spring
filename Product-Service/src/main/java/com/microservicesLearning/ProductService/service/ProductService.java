package com.microservicesLearning.ProductService.service;

import com.microservicesLearning.ProductService.model.ProductRequest;
import com.microservicesLearning.ProductService.model.ProductResponse;

public interface ProductService {

	long addProduct(ProductRequest productDetails);

	ProductResponse getProductById(long productId);

	void reduceQunatity(long productId, long quantity);

}
