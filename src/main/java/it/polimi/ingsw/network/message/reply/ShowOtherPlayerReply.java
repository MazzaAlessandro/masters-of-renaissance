package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to show another player's board
 */
public class ShowOtherPlayerReply extends Message {
    private final List<String> players;

    public ShowOtherPlayerReply(List<String> players) {
        super(Type.SHOWOTHERPLAYER, null);
        this.players = new ArrayList<>(players);
    }

    public List<String> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "{\"ShowOtherPlayerReply\":{"
                + "\"players\":" + players
                + "}}";
    }
}
