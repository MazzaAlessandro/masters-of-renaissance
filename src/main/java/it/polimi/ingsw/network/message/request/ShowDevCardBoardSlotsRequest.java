package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever the player needs to see his Development Cards
 */
public class ShowDevCardBoardSlotsRequest extends Message {
    public ShowDevCardBoardSlotsRequest(String player) {
        super(Type.SHOWDEVCARDBOARDSLOTS, player);
    }

    @Override
    public String toString() {
        return "{ShowDevCardBoardSlotsRequest}";
    }
}
