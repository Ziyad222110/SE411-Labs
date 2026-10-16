package edu.spu.se411.lab07;

import edu.spu.se411.lab07.exception.InvalidArgumentException;
import edu.spu.se411.lab07.exception.MissingInformationException;
import edu.spu.se411.lab07.model.Booking;
import edu.spu.se411.lab07.model.CarRentalBooking;
import edu.spu.se411.lab07.model.FlightBooking;
import edu.spu.se411.lab07.model.SeatClass;
import edu.spu.se411.lab07.model.TrainBooking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainApp {

    static Logger logger = LoggerFactory.getLogger(MainApp.class);

    /**
     * Polymorphism requirement: works for ANY booking type.
     * The actual pricing rule executed depends on the runtime type of the object.
     */
    public static double computeTotalPrice(Booking booking)
            throws MissingInformationException, InvalidArgumentException {
        return booking.computeTotalPrice();
    }

    public static void main(String[] args) {
        logger.info("Application is starting...");
        System.out.println("=== Travel Agency Booking System ===");

        try {
            List<Booking> bookings = new ArrayList<>();

            // ---------- Valid bookings ----------
            FlightBooking flight = new FlightBooking("F001", "Ahmed Ali",
                    LocalDate.of(2026, 11, 5), "Dubai", 800.0);
            flight.setLuggageWeight(23.0);
            bookings.add(flight);

            TrainBooking trainStd = new TrainBooking("T001", "Sara Khalid",
                    LocalDate.of(2026, 11, 10), "Dammam", SeatClass.STANDARD);
            trainStd.setDistanceKm(400);
            bookings.add(trainStd);

            TrainBooking trainFirst = new TrainBooking("T002", "Omar Saleh",
                    LocalDate.of(2026, 11, 12), "Makkah", SeatClass.FIRST_CLASS);
            trainFirst.setDistanceKm(900);
            bookings.add(trainFirst);

            CarRentalBooking car = new CarRentalBooking("C001", "Noura Fahad",
                    LocalDate.of(2026, 11, 15), "Abha", 150.0);
            car.setRentalDays(7);
            bookings.add(car);

            // ---------- Bookings that break the business rules ----------
            FlightBooking flightNoLuggage = new FlightBooking("F002", "Khalid Nasser",
                    LocalDate.of(2026, 12, 1), "Cairo", 650.0);           // luggage never set
            bookings.add(flightNoLuggage);

            FlightBooking flightHeavy = new FlightBooking("F003", "Lina Hassan",
                    LocalDate.of(2026, 12, 3), "London", 2500.0);
            flightHeavy.setLuggageWeight(55.0);                           // > 40 kg
            bookings.add(flightHeavy);

            TrainBooking trainNoDistance = new TrainBooking("T003", "Faisal Omar",
                    LocalDate.of(2026, 12, 5), "Madinah", SeatClass.STANDARD); // distance never set
            bookings.add(trainNoDistance);

            TrainBooking trainTooFar = new TrainBooking("T004", "Reem Saad",
                    LocalDate.of(2026, 12, 7), "Jazan", SeatClass.FIRST_CLASS);
            trainTooFar.setDistanceKm(2500);                              // > 2000 km
            bookings.add(trainTooFar);

            CarRentalBooking carNoDays = new CarRentalBooking("C002", "Yousef Ali",
                    LocalDate.of(2026, 12, 9), "Taif", 120.0);            // days never set
            bookings.add(carNoDays);

            CarRentalBooking carZeroDays = new CarRentalBooking("C003", "Hind Majed",
                    LocalDate.of(2026, 12, 11), "AlUla", 200.0);
            carZeroDays.setRentalDays(0);                                 // < 1 day
            bookings.add(carZeroDays);

            // ---------- Process every booking polymorphically ----------
            double grandTotal = 0;
            for (Booking b : bookings) {
                try {
                    double price = computeTotalPrice(b);
                    grandTotal += price;
                    System.out.printf("%-75s -> %10.2f SAR%n", b, price);
                    logger.info("Computed price for {}: {}", b.getBookingId(), price);
                } catch (MissingInformationException e) {
                    System.out.println("MISSING INFO : " + e.getMessage());
                    logger.error("MissingInformationException: {}", e.getMessage(), e);
                } catch (InvalidArgumentException e) {
                    System.out.println("INVALID VALUE: " + e.getMessage());
                    logger.error("InvalidArgumentException: {}", e.getMessage(), e);
                }
            }

            System.out.printf("%nTotal of valid bookings: %.2f SAR%n", grandTotal);
            logger.info("Total of valid bookings: {}", grandTotal);

        } catch (Exception e) {
            // anything unexpected
            System.out.println("Unexpected error: " + e.getMessage());
            logger.error("Unexpected exception", e);
        } finally {
            logger.info("Application is stopping...");
            System.out.println("=== Application stopped ===");
        }
    }
}
