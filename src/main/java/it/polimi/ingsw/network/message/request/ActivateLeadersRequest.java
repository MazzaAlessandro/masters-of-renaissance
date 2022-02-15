package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Saves the LeaderCards the player chose to activate to check if he can activate them
 */
public class ActivateLeadersRequest extends Message {
    private final List<Integer> leaders;

    public ActivateLeadersRequest(String player, List<Integer> leaders) {
        super(Type.ACTIVELEADERS, player);
        this.leaders = new ArrayList<>(leaders);
    }

    public List<Integer> getLeaders() {
        return leaders;
    }

    @Override
    public String toString() {
        return "{\"ActivateLeadersRequest\":{"
                + "\"leaders\":" + leaders
                + "}}";
    }
}
