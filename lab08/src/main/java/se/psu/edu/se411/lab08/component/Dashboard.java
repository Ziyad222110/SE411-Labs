package se.psu.edu.se411.lab08.component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Observer #1: keeps the latest reading of every sensor it watches and displays it.
 */
public class Dashboard extends SensorObserver {

    private final Map<String, Double> latestReadings = new LinkedHashMap<>();

    public Dashboard(String name) {
        super(name);
    }

    @Override
    protected void onReading(Sensor sensor) {
        latestReadings.put(sensor.getName(), sensor.getReading());
        display(sensor);
    }

    private void display(Sensor sensor) {
        System.out.printf("[Dashboard %-8s] %-11s %-14s -> %6.2f %s%n",
                getName(), sensor.getType(), sensor.getName(), sensor.getReading(), sensor.getUnit());
    }

    public Map<String, Double> getLatestReadings() {
        return latestReadings;
    }
}
