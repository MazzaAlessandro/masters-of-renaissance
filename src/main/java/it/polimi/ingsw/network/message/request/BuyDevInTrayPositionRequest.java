package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Sends the position of the card chosen by the player to the server
 */
public class BuyDevInTrayPositionRequest extends Message {
    private final int position;

    public BuyDevInTrayPositionRequest(String player, int position) {
        super(Type.BUYDEVTRAYPOSITION, player);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "{\"BuyDevInTrayPositionRequest\":{"
                + "\"position\":\"" + position + "\""
                + "}}";
    }
}
