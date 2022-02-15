package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Periodically sent to the server to notify if the client is still connected
 * If the server doesn't receive the ping after a certain amount of time it means the player got disconnected
 */
public class Ping extends Message {
    public Ping(String sender) {
        super(Type.PING, sender);
    }
}
