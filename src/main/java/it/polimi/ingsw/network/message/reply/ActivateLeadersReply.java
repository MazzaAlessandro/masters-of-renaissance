package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Asks which LeaderCards the player wants to activate
 */
public class ActivateLeadersReply extends Message {
    private final List<List<String>> activeLeaders;
    private final List<List<String>> inactiveLeaders;
    public ActivateLeadersReply(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders){
        super(Type.ACTIVELEADERS, null);
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
        return "{\"ActivateLeadersReply\":{"
                + "\"activeLeaders\":" + activeLeaders
                + ",\"inactiveLeaders\":" + inactiveLeaders
                + "}}";
    }
}
