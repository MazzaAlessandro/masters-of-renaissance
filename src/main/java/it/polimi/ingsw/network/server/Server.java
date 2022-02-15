package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.ClientMultiThread;
import it.polimi.ingsw.network.message.Message;

import java.util.logging.Logger;
/**
 * Class that implements the server and client communication through multithreading and socket
 */
public interface Server {
    Logger LOG = Logger.getLogger(ClientMultiThread.class.getName());

    /**
     * Start the server on port {@param port}
     */
    void start(int port);

    /**
     * Check if the {@param nickname } is already in use by another client
     *
     * @return {@code true} if {@param nickname } is already in use {@code false} otherwise.
     */
    boolean isNicknameTaken(String nickname);

    /**
     * Takes a {@param nickname} and converts it to associated ClientHandlerMultiThread
     *
     * @return associated ClientHandlerMultiThread
     */
    ClientHandler nicknameToClientHandler(String nickname);

    /**
     * Send a {@param message} to all clients on the server
     */
    void broadcast(Message message, int matchId);
}
