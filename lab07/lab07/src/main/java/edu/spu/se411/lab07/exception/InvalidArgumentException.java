package edu.spu.se411.lab07.exception;

/**
 * Thrown when a booking value is outside the range allowed by the business rules.
 */
public class InvalidArgumentException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidArgumentException(String message) {
        super(message);
    }
}
