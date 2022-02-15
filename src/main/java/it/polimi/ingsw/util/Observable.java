package it.polimi.ingsw.util;

import it.polimi.ingsw.network.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Observable to implement Observer pattern
 */
public abstract class Observable {
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Add an {@param observer} to the observer list
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Remove an {@param observer} to the observer list
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify the {@param message} to all observers on the observer list
     */
    public void updateAllObserver(Message message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
