package edu.spu.se411.lab07.model;

import edu.spu.se411.lab07.exception.InvalidArgumentException;
import edu.spu.se411.lab07.exception.MissingInformationException;
import edu.spu.se411.lab07.util.Config;

import java.time.LocalDate;

public class CarRentalBooking extends Booking {

    private final double dailyRate;   // set when the booking is created
    private Integer rentalDays;       // entered later by the customer (null = not provided)

    public CarRentalBooking(String bookingId, String customerName, LocalDate travelDate,
                            String destination, double dailyRate) {
        super(bookingId, customerName, travelDate, destination);
        this.dailyRate = dailyRate;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public Integer getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(Integer rentalDays) {
        this.rentalDays = rentalDays;
    }

    @Override
    public double computeTotalPrice() throws MissingInformationException, InvalidArgumentException {
        validateRange(rentalDays, Config.MIN_RENTAL_DAYS, Config.MAX_RENTAL_DAYS, "rental days");
        return dailyRate * rentalDays;
    }

    @Override
    public String getBookingType() {
        return "CarRentalBooking";
    }
}
