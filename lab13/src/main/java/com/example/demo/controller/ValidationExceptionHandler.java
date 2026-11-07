package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

/**
 * 14) Turns validation failures into a clear JSON 400 Bad Request response, e.g.
 * {"status":400,"error":"Bad Request","messages":["id must be a positive number"]}
 */
@RestControllerAdvice
public class ValidationExceptionHandler {

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(HandlerMethodValidationException ex) {
		List<String> messages = ex.getAllErrors().stream()
				.map(error -> error.getDefaultMessage())
				.toList();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
				"status", 400,
				"error", "Bad Request",
				"messages", messages));
	}
}
