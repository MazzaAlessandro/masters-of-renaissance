package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Asks the player which Resource he wants as an output for the Default Production
 */
public class DefaultProductionOutputReply extends Message {
    public DefaultProductionOutputReply() {
        super(Type.DEFAULTPRODUCTIONOUTPUT, null);
    }

    @Override
    public String toString() {
        return "DefaultProductionOutputReply{}";
    }
}
