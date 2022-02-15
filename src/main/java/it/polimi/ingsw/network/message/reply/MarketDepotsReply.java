package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.List;

/**
 * Asks the player which of the bought Resources he bought he wants to discard.
 * This is sent only if the Resources do not fit in the Depots
 */
public class MarketDepotsReply extends Message {
    private final List<Integer> inputResources;
    public MarketDepotsReply(List<Integer> inputResources) {
        super(Type.MARKETDEPOTS, null);
        this.inputResources = inputResources;
    }

    public List<Integer> getInputResources() {
        return inputResources;
    }

    @Override
    public String toString() {
        return "MarketDepotsReply{" +
                "inputResources=" + inputResources +
                '}';
    }
}
