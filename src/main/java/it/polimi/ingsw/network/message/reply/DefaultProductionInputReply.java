package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Asks the player which Resources he wants to use as an input for the Default Production
 */
public class DefaultProductionInputReply extends Message {
    public DefaultProductionInputReply() {
        super(Type.DEFAULTPRODUCTIONINPUT, null);
    }

    @Override
    public String toString() {
        return "DefaultProductionInputReply{}";
    }
}
