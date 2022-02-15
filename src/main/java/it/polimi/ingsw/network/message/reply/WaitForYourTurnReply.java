package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Notifies to the player that he's waiting for his turn to start
 */
public class WaitForYourTurnReply extends Message {

    public WaitForYourTurnReply() {
        super(Type.WAITTURN, null);

    }

    @Override
    public String toString() {
        return "{WaitForYourTurnReply}";
    }
}
