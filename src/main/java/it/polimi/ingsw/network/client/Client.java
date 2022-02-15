package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.Message;

import java.util.logging.Logger;

public interface Client {
    Logger LOG = Logger.getLogger(ClientMultiThread.class.getName());

    /**
     * This method allows the client to listen to the server
     */
    void startServerListener();

    /**
     * Send an object {@param message} (request) to the server
     */
    void sendMessage(Message message);

    /**
     * Send an object {@param message} (request) to the server and attend one reply
     */
    void showRequest(Message message);

    /**
     * @return the message(reply) received from the server
     */
    Message receivedMessage();

    /**
     * Closes connection and ends thread
     */
    void stopConnection();

    /**
     * Initiates periodic polling communication to the server
     *
     * @param status {code True} start polling communication otherwise stop polling communication
     */
    void setPooling(boolean status);
}
