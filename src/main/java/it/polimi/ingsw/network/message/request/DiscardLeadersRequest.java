package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Sends to the server the Leader Cards chosen by the player to be discarded
 */
public class DiscardLeadersRequest extends Message {
    private final List<Integer> leaders;

    public DiscardLeadersRequest(String player, List<Integer> leaders) {
        super(Type.DISCARDLEADERS, player);
        this.leaders = new ArrayList<>(leaders);
    }

    public List<Integer> getLeaders() {
        return leaders;
    }

    @Override
    public String toString() {
        return "{\"DiscardLeadersRequest\":{"
                + "\"leaders\":" + leaders
                + "}}";
    }
}
