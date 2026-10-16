package edu.spu.se411.lab07.exception;

/**
 * Thrown when a value required to compute a booking's price
 * (luggage weight, distance, rental days) has not been provided yet.
 */
public class MissingInformationException extends Exception {

    private static final long serialVersionUID = 1L;

    public MissingInformationException(String message) {
        super(message);
    }
}
