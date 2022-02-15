package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Asks the player the nickname
 */
public class LoginReply extends Message {

    private final boolean isAllowed;

    public LoginReply(boolean isJoin) {
        super(Type.LOGIN, "Game");
        this.isAllowed = isJoin;
    }

    public boolean isAllowed() {
        return this.isAllowed;
    }



    @Override
    public String toString() {
        return "{\"LoginReply\":{"
                + "\"isAllowed\":\"" + isAllowed + "\""
                + "}}";
    }
}
