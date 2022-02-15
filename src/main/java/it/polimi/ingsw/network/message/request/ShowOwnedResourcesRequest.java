package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever the player needs to see his owned resources
 */
public class ShowOwnedResourcesRequest extends Message {

    public ShowOwnedResourcesRequest(String player) {
        super(Type.SHOWOWNEDRESOURCES, player);
    }

    @Override
    public String toString() {
        return "{ShowOwnedResourcesRequest}";
    }
}
