package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to show the Leader Cards productions to the player
 */
public class ShowLeadersProductionReply extends Message {
    private final List<List<String>> activeLeaders;

    public ShowLeadersProductionReply(List<List<String>> activeLeaders) {
        super(Type.SHOWLEADERSPRODUCTION, null);
        this.activeLeaders = new ArrayList<>(activeLeaders);
    }

    public List<List<String>> getActiveLeaders() {
        return activeLeaders;
    }

    @Override
    public String toString() {
        return "ShowBoardReply{" +
                '}';
    }
}
