package it.polimi.ingsw.view.sender;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.util.Observable;

public class SenderDirectly extends Observable implements Sender {

    @Override
    public void send(Message message) {
        updateAllObserver(message);
    }

    @Override
    public void showRequest(Message message){
        updateAllObserver(message);
    }

    @Override
    public void broadcast(Message message, int matchId) {
        updateAllObserver(message);
    }
}
