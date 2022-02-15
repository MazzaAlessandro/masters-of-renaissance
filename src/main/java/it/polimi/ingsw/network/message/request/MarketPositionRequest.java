package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Sends the chosen market row to the server
 */
public class MarketPositionRequest extends Message {
    private final int position;

    public MarketPositionRequest(String player, int position) {
        super(Type.MARKETPOSITION, player);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "{\"MarketPositionRequest\":{"
                + "\"position\":\"" + position + "\""
                + "}}";
    }
}
