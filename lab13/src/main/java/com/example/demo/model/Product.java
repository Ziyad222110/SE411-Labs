package com.example.demo.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

/**
 * Product model. Price uses BigDecimal instead of Double:
 * Double cannot store values like 0.1 exactly (0.1 + 0.2 != 0.3),
 * BigDecimal stores exact decimal values, which is what we want for money.
 */
public record Product(
        @Id Integer id,
        String name,
        String description,
        BigDecimal price
) {
}
