package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever a player needs to see another player's board
 */
public class ShowOtherPlayerRequest extends Message {

    public ShowOtherPlayerRequest(String player) {
        super(Type.SHOWOTHERPLAYER, player);
    }

    @Override
    public String toString() {
        return "{ShowOtherPlayerRequest}";
    }
}
