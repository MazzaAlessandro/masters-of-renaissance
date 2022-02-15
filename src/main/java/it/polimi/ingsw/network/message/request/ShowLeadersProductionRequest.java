package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever the player needs to see his Leader Card productions
 */
public class ShowLeadersProductionRequest extends Message {
    public ShowLeadersProductionRequest(String player) {
        super(Type.SHOWLEADERSPRODUCTION, player);
    }
    @Override
    public String toString() {
        return "{ShowLeadersProductionRequest}";
    }
}
