package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.List;

/**
 * Sends to the server the chosen starting Resources
 */
public class StartingResourcesRequest extends Message {
    private final List<Color> resourcesList;

    public StartingResourcesRequest(String player, List<Color> chosenResources) {
        super(Type.STARTINGRESOURCES, player);
        resourcesList = chosenResources;
    }

    public List<Color> getResourcesList() {
        return resourcesList;
    }

    @Override
    public String toString() {
        return "StartingResourcesRequest{" +
                "resourcesList=" + resourcesList +
                '}';
    }
}
