package it.polimi.ingsw.util;

import it.polimi.ingsw.network.message.Message;

/**
 * Interface Observer to implement Observer pattern
 */
public interface Observer {

    /**
     * Does actions described in the {@param message} when observable call update
     */
    void update(Message message);
}
