package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever a player needs to see another player's Faith Track
 */
public class ShowOtherFaithTrackRequest extends Message {
    private final String wantedPlayer;

    public ShowOtherFaithTrackRequest(String player, String wantedPlayer) {
        super(Type.SHOWOTHERFAITHTRACK, player);
        this.wantedPlayer = wantedPlayer;
    }

    public String getWantedPlayer() {
        return wantedPlayer;
    }

    @Override
    public String toString() {
        return "{\"ShowOtherFaithTrackRequest\":{"
                + "\"wantedPlayer\":\"" + wantedPlayer + "\""
                + "}}";
    }
}
