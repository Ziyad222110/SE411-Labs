package se.psu.edu.se411.lab08.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Reusable base class for ANY subject (code reuse):
 * holds the observer list and implements register / unregister / notify / clone.
 * Concrete subjects only need to call notifyObservers() when their state changes.
 */
public abstract class AbstractSubject implements ISubject, Cloneable {

    private List<IObserver> observers = new ArrayList<>();

    @Override
    public void register(IObserver o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public void unregister(IObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        // iterate over a copy so an observer may unregister itself during update()
        for (IObserver o : new ArrayList<>(observers)) {
            o.update(this);
        }
    }

    public int getObserverCount() {
        return observers.size();
    }

    /**
     * The clone copies the subject's state but NOT its observers:
     * it starts with its own, empty observer list.
     */
    @Override
    public AbstractSubject clone() {
        try {
            AbstractSubject copy = (AbstractSubject) super.clone();
            copy.observers = new ArrayList<>();
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e); // cannot happen: we implement Cloneable
        }
    }
}
