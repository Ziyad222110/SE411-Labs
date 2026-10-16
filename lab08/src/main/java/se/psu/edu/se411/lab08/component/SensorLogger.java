package se.psu.edu.se411.lab08.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Observer #2: prints every change and also records it in the log file (SLF4J).
 * Named SensorLogger to avoid clashing with org.slf4j.Logger.
 */
public class SensorLogger extends SensorObserver {

    private static final Logger logger = LoggerFactory.getLogger(SensorLogger.class);

    private int updatesReceived = 0;

    public SensorLogger(String name) {
        super(name);
    }

    @Override
    protected void onReading(Sensor sensor) {
        updatesReceived++;
        System.out.printf("[Logger    %-8s] #%02d %s%n", getName(), updatesReceived, sensor);
        logger.info("{} received update #{}: {}", getName(), updatesReceived, sensor);
    }

    public int getUpdatesReceived() {
        return updatesReceived;
    }
}
