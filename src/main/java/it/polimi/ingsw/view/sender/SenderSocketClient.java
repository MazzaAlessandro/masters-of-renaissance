package it.polimi.ingsw.view.sender;

import it.polimi.ingsw.network.client.ClientMultiThread;
import it.polimi.ingsw.network.message.Message;

public class SenderSocketClient implements Sender{
    private final ClientMultiThread client;

    public SenderSocketClient(ClientMultiThread client) {
        this.client = client;
    }

    @Override
    public void send(Message message) {
        client.sendMessage(message);
    }

    @Override
    public void showRequest(Message message){
        client.showRequest(message);
    }

    @Override
    public void broadcast(Message message, int matchId) {
        client.sendMessage(message);
    }
}
