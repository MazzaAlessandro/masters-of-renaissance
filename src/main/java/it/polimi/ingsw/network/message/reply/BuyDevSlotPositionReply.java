package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Asks the player the slots to store the bought Development Card
 */
public class BuyDevSlotPositionReply extends Message {
    public BuyDevSlotPositionReply() {
        super(Type.BUYDEVSLOTPOSITION, null);
    }

    @Override
    public String toString() {
        return "{Select SlotPosition where store the chosen card}";
    }
}
