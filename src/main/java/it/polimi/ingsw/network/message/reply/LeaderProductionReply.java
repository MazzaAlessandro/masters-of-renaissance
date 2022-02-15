package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Asks the player which Resource he wants to gain from a Leader Card's production effect
 */
public class LeaderProductionReply extends Message {
    private final List<Integer> selectedLeaders;
    public LeaderProductionReply(List<Integer> selectedLeaders) {
        super(Type.LEADERPRODUCTION, null);
        this.selectedLeaders = new ArrayList<>(selectedLeaders);
    }

    public List<Integer> getSelectedLeaders() {
        return selectedLeaders;
    }

    @Override
    public String toString() {
        return "LeaderProductionReply{}";
    }
}
