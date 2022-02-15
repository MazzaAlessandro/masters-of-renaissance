package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Notifies the chosen nickname to the server
 */
public class LoginRequest extends Message {
    public LoginRequest(String sender) {
        super(Type.LOGIN, sender);
    }

    @Override
    public String toString() {
        return "{Login:{"+
                "sender:"+getSender()+
                "}}";
    }
}
