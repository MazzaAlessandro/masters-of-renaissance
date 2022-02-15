package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to show another player's Leader Cards
 */
public class ShowOtherLeadersReply extends Message {

    private final List<List<String>> activeLeaders;
    private final List<List<String>> inactiveLeaders;

    public ShowOtherLeadersReply(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        super(Type.SHOWOTHERSLEADERS, null);
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
        return "{\"ShowOtherLeadersReply\":{"
                + "\"activeLeaders\":" + activeLeaders
                + ",\"inactiveLeaders\":" + inactiveLeaders
                + "}}";
    }
}
