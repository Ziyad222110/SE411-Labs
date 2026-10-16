package se.psu.edu.se411.lab08.component;

import se.psu.edu.se411.lab08.observer.AbstractSubject;

/**
 * Generic sensor: a subject whose state is a numeric reading.
 * All observer handling is inherited from AbstractSubject.
 */
public abstract class Sensor extends AbstractSubject {

    private String name;
    private final String unit;
    private double reading = 0.0;

    protected Sensor(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    /** Observers are notified only when the reading actually changes. */
    public void setReading(double newReading) {
        if (Double.compare(this.reading, newReading) != 0) {
            this.reading = newReading;
            notifyObservers();
        }
    }

    public double getReading() { return reading; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUnit() { return unit; }

    /** Each sensor type describes itself (polymorphism). */
    public abstract String getType();

    @Override
    public Sensor clone() {
        return (Sensor) super.clone();   // reuse AbstractSubject.clone()
    }

    @Override
    public String toString() {
        return String.format("%s '%s' = %.2f %s", getType(), name, reading, unit);
    }
}
