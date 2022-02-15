package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever a player needs to see another player's owned resources
 */
public class ShowOtherOwnedResourcesRequest extends Message {
    private final String wantedPlayer;

    public ShowOtherOwnedResourcesRequest(String player, String wantedPlayer) {
        super(Type.SHOWOTHEROWNEDRESOURCES, player);
        this.wantedPlayer = wantedPlayer;
    }

    public String getWantedPlayer() {
        return wantedPlayer;
    }

    @Override
    public String toString() {
        return "{\"ShowOtherOwnedResourcesRequest\":{"
                + "\"wantedPlayer\":\"" + wantedPlayer + "\""
                + "}}";
    }
}
