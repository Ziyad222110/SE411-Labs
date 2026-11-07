package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	private final ProductService productService;

	// Spring injects the ProductService bean from its IoC container (constructor injection)
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	/** 11) GET /api/v1/products -> all products */
	@GetMapping()
	public ResponseEntity<Iterable<Product>> findAll() {
		return ResponseEntity.ok(productService.findAll());
	}

	/**
	 * 12) GET /api/v1/products/{id} -> one product, or 404 if it does not exist.
	 * 14) @Positive: a negative or zero id is rejected with 400 Bad Request
	 *     before the method body runs (spring-boot-starter-validation).
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable @Positive(message = "id must be a positive number") Integer id) {
		return productService.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}
}
