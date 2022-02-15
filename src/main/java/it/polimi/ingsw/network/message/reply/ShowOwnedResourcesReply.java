package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to show the player's owned resources
 */
public class ShowOwnedResourcesReply extends Message {
    private final List<List<Integer>> depots;
    private final List<Integer> strongBox;

    public ShowOwnedResourcesReply(List<List<Integer>> depots, List<Integer> strongBox) {
        super(Type.SHOWOWNEDRESOURCES, null);
        this.depots = new ArrayList<>(depots);
        this.strongBox = new ArrayList<>(strongBox);
    }

    public List<List<Integer>> getDepots() {
        return depots;
    }

    public List<Integer> getStrongBox() {
        return strongBox;
    }

    @Override
    public String toString() {
        return "{\"ShowOwnedResourcesReply\":{"
                + "\"depots\":" + depots
                + ",\"strongBox\":" + strongBox
                + "}}";
    }
}
