package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.List;

/**
 * Sends the available productions and the ones chosen by the player to the server
 */
public class ActivateProductionRequest extends Message {
    private final List<Integer> productionPosition;
    private final List<Integer> possiblePosition;
    public ActivateProductionRequest(String player, List<Integer> productionPosition, List<Integer> possiblePosition) {
        super(Type.ACTIVATEPRODUCTION, player);
        this.productionPosition = productionPosition;
        this.possiblePosition = possiblePosition;
    }

    public List<Integer> getProductionPosition() {
        return productionPosition;
    }

    public List<Integer> getPossiblePosition() {
        return possiblePosition;
    }

    @Override
    public String toString() {
        return "ActivateProductionRequest{" +
                "productionPosition=" + productionPosition +
                '}';
    }
}
