package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;


/**
 * Sends to the server the chosen turn action
 */
public class TurnActionRequest extends Message {
    private final int action;

    public TurnActionRequest(String player, int selectedAction){
        super(Type.TURNACTION, player);
        this.action = selectedAction;
    }

    public int getAction(){ return action; }

    @Override
    public String toString() {
        return "{\"TurnActionRequest\":{"
                + "\"Selected action\":" + action
                + "}}";
    }
}
