package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Notifies to the player that he's waiting for the other players to join
 */
public class WaitForOtherPlayersReply extends Message {
    public WaitForOtherPlayersReply() {
        super(Type.WAITPLAYERS, null);
    }

    @Override
    public String toString() {
        return "{WaitForOtherPlayersReply}";
    }
}
