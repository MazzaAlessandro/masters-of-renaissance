package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Asks the player which LeaderCards he wants to discard from the starting ones
 */
public class StartingDiscardLeaderReply extends Message {
    private final List<List<String>> activeLeaders;
    private final List<List<String>> inactiveLeaders;

    public StartingDiscardLeaderReply(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        super(Type.STARTINGDISCARD, null);
        this.activeLeaders = new ArrayList<>(activeLeaders);
        this.inactiveLeaders = new ArrayList<>(inactiveLeaders);
    }

    public List<List<String>> getActiveLeaders() {
        return activeLeaders;
    }

    public List<List<String>> getInactiveLeaders() {
        return inactiveLeaders;
    }

    @Override
    public String toString() {
        return "{\"StartingDiscardLeaderReply\":{"
                + "\"activeLeaders\":" + activeLeaders
                + ",\"inactiveLeaders\":" + inactiveLeaders
                + "}}";
    }
}
