package edu.spu.se411.lab07.model;

import edu.spu.se411.lab07.util.Config;

/**
 * Train seat classes. Each class knows its own per-km rate from Config,
 * so TrainBooking needs no if/switch on the seat class.
 */
public enum SeatClass {
    STANDARD(Config.TRAIN_STANDARD_RATE),
    FIRST_CLASS(Config.TRAIN_FIRST_CLASS_RATE);

    private final double ratePerKm;

    SeatClass(double ratePerKm) {
        this.ratePerKm = ratePerKm;
    }

    public double getRatePerKm() {
        return ratePerKm;
    }
}
