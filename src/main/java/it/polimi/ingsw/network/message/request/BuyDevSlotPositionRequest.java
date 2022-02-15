package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Sends the chosen Development Card Slot to the server
 */
public class BuyDevSlotPositionRequest extends Message {
    private final int slotPosition;

    public BuyDevSlotPositionRequest(String player, int slotPosition) {
        super(Type.BUYDEVSLOTPOSITION, player);
        this.slotPosition = slotPosition;
    }

    public int getSlotPosition() {
        return slotPosition;
    }

    @Override
    public String toString() {
        return "{\"BuyDevSlotPositionRequest\":{"
                + ",\"slotPosition\":\"" + slotPosition + "\""
                + "}}";
    }

}
