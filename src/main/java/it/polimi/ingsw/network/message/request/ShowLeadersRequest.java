package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever the player needs to see his Leader Cards
 */
public class ShowLeadersRequest extends Message {
    public ShowLeadersRequest(String player) {
        super(Type.SHOWLEADERS, player);
    }

    @Override
    public String toString() {
        return "{ShowLeadersRequest}";
    }
}
