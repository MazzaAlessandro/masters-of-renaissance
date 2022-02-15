package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever the player needs to see the Development Card Tray
 */
public class ShowDevCardTrayRequest extends Message {
    public ShowDevCardTrayRequest(String player) {
        super(Type.SHOWDEVCARDTRAY, player);
    }

    @Override
    public String toString() {
        return "{ShowDevCardTrayRequest}";
    }
}
