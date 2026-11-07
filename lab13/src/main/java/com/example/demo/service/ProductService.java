package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

/** Business logic layer: the controller talks to this service, never directly to the repository. */
@Service
public class ProductService {

	private final ProductRepository productRepository;

	// Spring injects the ProductRepository bean from its IoC container (constructor injection)
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}
}
