package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Asks the player which action he wants to do during his turn
 */
public class TurnActionReply extends Message {
    public TurnActionReply(){ super(Type.TURNACTION, null);}

    @Override
    public String toString() {
        return "{TurnActionReply}";
    }
}
