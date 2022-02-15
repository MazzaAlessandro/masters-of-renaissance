package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever a player needs to see another player's Development Cards Slots
 */
public class ShowOtherDevCardBoardSlotsRequest extends Message {
    private final String wantedPlayer;

    public ShowOtherDevCardBoardSlotsRequest(String player, String wantedPlayer) {
        super(Type.SHOWOTHERDEVCARDBOARDSLOTS, player);
        this.wantedPlayer = wantedPlayer;
    }

    public String getWantedPlayer() {
        return wantedPlayer;
    }

    @Override
    public String toString() {
        return "{\"ShowOtherDevCardBoardSlotsRequest\":{"
                + "\"wantedPlayer\":\"" + wantedPlayer + "\""
                + "}}";
    }
}
