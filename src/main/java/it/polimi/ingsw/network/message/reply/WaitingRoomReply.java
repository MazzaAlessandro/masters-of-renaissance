package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Asks to the player if he wants to create a new match or join an already existing one
 */
public class WaitingRoomReply extends Message {

    public WaitingRoomReply() {
        super(Type.WAITINGROOM, null);
    }

    @Override
    public String toString() {
        return "WaitingRoomReply{}";
    }
}
