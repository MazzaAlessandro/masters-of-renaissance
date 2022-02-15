package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to show another player's Owned Resources
 */
public class ShowOtherOwnedResourcesReply extends Message {
    private final List<List<Integer>> depots;
    private final List<Integer> strongBox;

    public ShowOtherOwnedResourcesReply(List<List<Integer>> depots, List<Integer> strongBox) {
        super(Type.SHOWOTHEROWNEDRESOURCES, null);
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
        return "{\"ShowOtherOwnedResourcesReply\":{"
                + "\"depots\":" + depots
                + ",\"strongBox\":" + strongBox
                + "}}";
    }
}
