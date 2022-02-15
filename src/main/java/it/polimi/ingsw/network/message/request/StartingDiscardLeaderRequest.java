package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.List;

/**
 * Sends the Leader Cards chosen to be discarded to the server
 */
public class StartingDiscardLeaderRequest extends Message {
    private final List<Integer> leaders;
    public StartingDiscardLeaderRequest(String player, List<Integer> leaders) {
        super(Type.STARTINGDISCARD, player);
        this.leaders = leaders;
    }

    public List<Integer> getLeaders() {
        return leaders;
    }

    @Override
    public String toString() {
        return "StartingDiscardLeaderRequest{" +
                "leaders=" + leaders +
                '}';
    }
}
