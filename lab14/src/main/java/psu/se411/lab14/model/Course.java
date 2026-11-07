package psu.se411.lab14.model;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Course(
	@Id
	@Min(value = 0, message = "Id cannot be negative") Long id,
	@NotBlank(message = "Code cannot be empty") String code,
	@Size(min = 2, message = "Name must be at least 2 characters") String name,
	String description,
	@Min(value = 1, message = "Credits must be at least 1")
	@Max(value = 10, message = "Credits must be at most 10") Integer credits
) {
}