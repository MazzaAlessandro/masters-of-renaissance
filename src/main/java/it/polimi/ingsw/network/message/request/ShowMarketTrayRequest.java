package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever the player needs to see the Market Tray
 */
public class ShowMarketTrayRequest extends Message {
    public ShowMarketTrayRequest(String player) {
        super(Type.SHOWMARKETTRAY, player);
    }

    @Override
    public String toString() {
        return "{ShowMarketTrayRequest}";
    }
}
