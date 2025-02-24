package com.microservicesLearning.ProductService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicesLearning.ProductService.model.ProductRequest;
import com.microservicesLearning.ProductService.model.ProductResponse;
import com.microservicesLearning.ProductService.service.ProductService;


@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/addProduct")
	public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productDetails){
		long productId = productService.addProduct(productDetails);
		return new ResponseEntity<Long>(productId,HttpStatus.CREATED);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable long productId) {
		ProductResponse productResponse = productService.getProductById(productId);
		return new ResponseEntity<ProductResponse>(productResponse,HttpStatus.FOUND);	
	}
	
	@PutMapping("/reduceQuantity/{id}")
	public ResponseEntity<Void> reduceQuantity(
			@PathVariable("id") long productId,
			@RequestParam long quantity
	){
		productService.reduceQunatity(productId,quantity);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
