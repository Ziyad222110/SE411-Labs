package edu.spu.se411.lab07.util;

/**
 * Global configuration: all rates and business-rule limits in one place.
 */
public final class Config {

    // ---- Flight ----
    public static final double EXTRA_LUGGAGE_RATE = 5.0;      // per kg
    public static final double MIN_LUGGAGE_WEIGHT = 0.0;      // kg
    public static final double MAX_LUGGAGE_WEIGHT = 40.0;     // kg

    // ---- Train ----
    public static final double TRAIN_STANDARD_RATE = 0.25;    // per km
    public static final double TRAIN_FIRST_CLASS_RATE = 0.50; // per km
    public static final int MIN_TRAIN_DISTANCE = 1;           // km
    public static final int MAX_TRAIN_DISTANCE = 2000;        // km

    // ---- Car rental ----
    public static final int MIN_RENTAL_DAYS = 1;
    public static final int MAX_RENTAL_DAYS = 30;

    private Config() {
        // constants only; no instances
    }
}
