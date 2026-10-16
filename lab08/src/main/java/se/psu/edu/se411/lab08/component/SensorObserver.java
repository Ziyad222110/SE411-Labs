package se.psu.edu.se411.lab08.component;

import se.psu.edu.se411.lab08.observer.IObserver;
import se.psu.edu.se411.lab08.observer.ISubject;

/**
 * Reusable base for observers of sensors (code reuse):
 * update() checks the subject type once, then delegates to onReading(),
 * which each concrete observer implements differently (polymorphism).
 */
public abstract class SensorObserver implements IObserver {

    private final String name;

    protected SensorObserver(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public final void update(ISubject subject) {
        if (subject instanceof Sensor) {
            onReading((Sensor) subject);
        }
    }

    /** Called whenever an observed sensor's reading changes. */
    protected abstract void onReading(Sensor sensor);
}
