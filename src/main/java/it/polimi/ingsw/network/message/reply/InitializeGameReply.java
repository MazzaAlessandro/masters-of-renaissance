package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Asks to the host how many players the match should allow
 */
public class InitializeGameReply extends Message {
    public InitializeGameReply() {
        super(Type.INITIALIZE, null);
    }
}
