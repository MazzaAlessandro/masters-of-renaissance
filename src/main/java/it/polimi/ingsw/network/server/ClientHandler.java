package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;

/**
 * Handles the communication between server and clients
 * Send reply to client and forwards requests to GameController through the Observer pattern
 */
public interface ClientHandler extends Runnable {

    String getNickname();

    /**
     * Async send {@param message} server to client
     */
    void send(Message message);

    /**
     * Mark clientHandler offline
     */
    void disconnect();

    void run();
}
