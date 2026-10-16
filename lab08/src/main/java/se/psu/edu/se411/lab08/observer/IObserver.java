package se.psu.edu.se411.lab08.observer;

public interface IObserver {
    /** Updates the data with the new data */
    void update(ISubject subject);
}