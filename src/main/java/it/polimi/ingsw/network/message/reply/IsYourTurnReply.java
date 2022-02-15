package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever a player's turn starts to that player
 */
public class IsYourTurnReply extends Message {

    public IsYourTurnReply() {
        super(Type.ISYOURTURN, null);
    }

    @Override
    public String toString() {
        return "{IsYourTurnReply}";
    }
}
