package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Asks the player which of the possible productions he wants to activate (basic, Development Cards or Leader Cards)
 */
public class ActivateProductionReply extends Message {

    private final List<Integer> productionPosition;
    private final List<List<String>> activeLeaders;
    private final List<List<String>> devCards;

    public ActivateProductionReply(List<Integer> productionPosition, List<List<String>> activeLeaders, List<List<String>> devCards) {
        super(Type.ACTIVATEPRODUCTION, null);
        this.productionPosition = new ArrayList<>(productionPosition);
        this.activeLeaders = activeLeaders;
        this.devCards = devCards;
    }

    public List<Integer> getProductionPosition() {
        return productionPosition;
    }

    public List<List<String>> getActiveLeaders() {
        return activeLeaders;
    }

    public List<List<String>> getDevCards() {
        return devCards;
    }

    @Override
    public String toString() {
        return "ActivateProductionReply{" +
                "productionPosition=" + productionPosition +
                '}';
    }
}
