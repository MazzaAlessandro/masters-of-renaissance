package it.polimi.ingsw.view.sender;

import it.polimi.ingsw.network.message.Message;

public interface Sender {

    /**
     * Send a {@param message} to the recipient
     */
    void send(Message message);

    /**
     * Send a special request {@param message}, to see part of model, to the recipient
     */
    void showRequest(Message message);

    /**
     * Send a {@param message} to everyone connected
     */
    void broadcast(Message message, int matchId);
}
