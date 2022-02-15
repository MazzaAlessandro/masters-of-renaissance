package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever a player needs to see another player's Leader Cards
 */
public class ShowOthersLeaderRequest extends Message {

    private String wantedPlayer;

    public ShowOthersLeaderRequest(String player, String wantedPlayer) {
        super(Type.SHOWOTHERSLEADERS, player);
        this.wantedPlayer = wantedPlayer;
    }

    public String getWantedPlayer() {
        return wantedPlayer;
    }

    @Override
    public String toString() {
        return "{\"ShowOthersLeaderRequest\":{"
                + "\"wantedPlayer\":\"" + wantedPlayer + "\""
                + "}}";
    }
}
