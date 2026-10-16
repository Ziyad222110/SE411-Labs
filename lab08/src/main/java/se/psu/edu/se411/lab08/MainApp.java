package se.psu.edu.se411.lab08;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.psu.edu.se411.lab08.component.Dashboard;
import se.psu.edu.se411.lab08.component.HumiditySensor;
import se.psu.edu.se411.lab08.component.SensorLogger;
import se.psu.edu.se411.lab08.component.TemperatureSensor;

public class MainApp {

    static Logger logger = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        logger.info("Application is starting...");
        System.out.println("=== Observer Pattern: Sensor Monitoring ===");

        try {
            // ---- 1. Subjects ----
            TemperatureSensor temp = new TemperatureSensor("Lab-Temp");
            HumiditySensor humidity = new HumiditySensor("Lab-Humidity");

            // ---- 2. Observers ----
            Dashboard dashboard = new Dashboard("Main");
            SensorLogger sensorLogger = new SensorLogger("FileLog");

            // ---- 3. Register both observers to both sensors ----
            temp.register(dashboard);
            temp.register(sensorLogger);
            humidity.register(dashboard);
            humidity.register(sensorLogger);
            logger.info("Observers registered: temp={}, humidity={}",
                    temp.getObserverCount(), humidity.getObserverCount());

            // ---- 4. Clone: copies the state, but NOT the observers ----
            TemperatureSensor tempClone = temp.clone();
            tempClone.setName("Backup-Temp");
            System.out.printf("Original '%s' observers: %d | Clone '%s' observers: %d%n",
                    temp.getName(), temp.getObserverCount(),
                    tempClone.getName(), tempClone.getObserverCount());

            Dashboard backupDashboard = new Dashboard("Backup");
            tempClone.register(backupDashboard);        // the clone has its OWN observer
            logger.info("Clone created with {} observer(s) of its own", tempClone.getObserverCount());

            // ---- 5. Simulate readings ----
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                System.out.printf("%n--- Reading cycle %d ---%n", i + 1);

                temp.setReading(20 + random.nextDouble() * 15);
                humidity.setReading(40 + random.nextDouble() * 20);
                tempClone.setReading(20 + random.nextDouble() * 15); // only "Backup" dashboard reacts

                // Demonstrate unregister half-way through
                if (i == 4) {
                    humidity.unregister(sensorLogger);
                    System.out.println(">> FileLog unregistered from humidity sensor");
                    logger.info("FileLog unregistered from {}", humidity.getName());
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.error("Simulation interrupted", e);
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            System.out.printf("%nFileLog received %d updates in total.%n", sensorLogger.getUpdatesReceived());
            logger.info("FileLog received {} updates in total", sensorLogger.getUpdatesReceived());

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            logger.error("Unexpected exception", e);
        } finally {
            logger.info("Application is stopping...");
            System.out.println("=== Application stopped ===");
        }
    }
}
