package edu.spu.se411.lab07.model;

import edu.spu.se411.lab07.exception.InvalidArgumentException;
import edu.spu.se411.lab07.exception.MissingInformationException;
import edu.spu.se411.lab07.util.Config;

import java.time.LocalDate;

public class FlightBooking extends Booking {

    private final double basePrice;   // set when the booking is created
    private Double luggageWeight;     // entered later by the customer (null = not provided)

    public FlightBooking(String bookingId, String customerName, LocalDate travelDate,
                         String destination, double basePrice) {
        super(bookingId, customerName, travelDate, destination);
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public Double getLuggageWeight() {
        return luggageWeight;
    }

    public void setLuggageWeight(Double luggageWeight) {
        this.luggageWeight = luggageWeight;
    }

    @Override
    public double computeTotalPrice() throws MissingInformationException, InvalidArgumentException {
        validateRange(luggageWeight, Config.MIN_LUGGAGE_WEIGHT, Config.MAX_LUGGAGE_WEIGHT, "luggage weight (kg)");
        return basePrice + luggageWeight * Config.EXTRA_LUGGAGE_RATE;
    }

    @Override
    public String getBookingType() {
        return "FlightBooking";
    }
}
