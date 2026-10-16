package edu.spu.se411.lab07.model;

import edu.spu.se411.lab07.exception.InvalidArgumentException;
import edu.spu.se411.lab07.exception.MissingInformationException;

import java.time.LocalDate;

public abstract class Booking {

    private final String bookingId;
    private final String customerName;
    private final LocalDate travelDate;
    private final String destination;

    protected Booking(String bookingId, String customerName, LocalDate travelDate, String destination) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.travelDate = travelDate;
        this.destination = destination;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public String getDestination() {
        return destination;
    }

    public abstract double computeTotalPrice() throws MissingInformationException, InvalidArgumentException;

    public abstract String getBookingType();

    /**
     * Shared validation used by every subclass (code reuse):
     * - value missing (null)      -> MissingInformationException
     * - value outside [min, max]  -> InvalidArgumentException
     */
    protected void validateRange(Number value, double min, double max, String fieldName)
            throws MissingInformationException, InvalidArgumentException {
        if (value == null) {
            throw new MissingInformationException(String.format(
                    "%s %s: %s has not been provided", getBookingType(), bookingId, fieldName));
        }
        double v = value.doubleValue();
        if (v < min || v > max) {
            throw new InvalidArgumentException(String.format(
                    "%s %s: %s = %s is out of range [%s, %s]",
                    getBookingType(), bookingId, fieldName, value, min, max));
        }
    }

    @Override
    public String toString() {
        return String.format("%s[id=%s, customer=%s, date=%s, destination=%s]",
                getBookingType(), bookingId, customerName, travelDate, destination);
    }
}