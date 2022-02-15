package it.polimi.ingsw.view.sender;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class SenderSocketServer implements Sender{
    private ClientHandler clientHandler;
    private final Server server;

    public SenderSocketServer(Server server) {
        this.server = server;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void send(Message message) {
        clientHandler.send(message);
    }

    @Override
    public void showRequest(Message message) {
        clientHandler.send(message);
    }

    @Override
    public void broadcast(Message message, int matchId) {
        server.broadcast(message, matchId);
    }

}
