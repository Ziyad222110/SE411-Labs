package edu.spu.se411.lab07.model;

import edu.spu.se411.lab07.exception.InvalidArgumentException;
import edu.spu.se411.lab07.exception.MissingInformationException;
import edu.spu.se411.lab07.util.Config;

import java.time.LocalDate;

public class TrainBooking extends Booking {

    private final SeatClass seatClass;
    private Integer distanceKm;       // entered later by the system (null = not provided)

    public TrainBooking(String bookingId, String customerName, LocalDate travelDate,
                        String destination, SeatClass seatClass) {
        super(bookingId, customerName, travelDate, destination);
        this.seatClass = seatClass;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public Integer getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Integer distanceKm) {
        this.distanceKm = distanceKm;
    }

    @Override
    public double computeTotalPrice() throws MissingInformationException, InvalidArgumentException {
        validateRange(distanceKm, Config.MIN_TRAIN_DISTANCE, Config.MAX_TRAIN_DISTANCE, "distance (km)");
        return distanceKm * seatClass.getRatePerKm();
    }

    @Override
    public String getBookingType() {
        return "TrainBooking";
    }
}
