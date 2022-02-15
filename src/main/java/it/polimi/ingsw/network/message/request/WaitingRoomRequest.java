package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Sends to the server the action chosen by the player in the Waiting Room
 */
public class WaitingRoomRequest extends Message {

    private int chosenAction;

    public WaitingRoomRequest(String sender, int chosenAction) {
        super(Type.WAITINGROOM, sender);
        this.chosenAction = chosenAction;
    }

    public int getChosenAction(){
        return  chosenAction;
    }

    @Override
    public String toString() {
        return "WaitingRoomRequest{" +
                "chosenAction=" + chosenAction +
                '}';
    }
}
