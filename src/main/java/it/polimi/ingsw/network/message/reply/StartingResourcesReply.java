package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Asks the player which Resources he wants as starting Resources
 */
public class StartingResourcesReply extends Message {

    int amountResources;

    public StartingResourcesReply(int amount) {
        super(Type.STARTINGRESOURCES, null);
        this.amountResources = amount;
    }

    public int getAmountResources() {
        return amountResources;
    }

    @Override
    public String toString() {
        return "StartingResourcesReply{}";
    }
}
