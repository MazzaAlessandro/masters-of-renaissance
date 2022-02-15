package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Sends a warning to the player (often used to notify errors or invalid inputs)
 */
public class AlertReply extends Message {
    private final String text;
    public AlertReply(String text) {
        super(Type.ALERT, null);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "{\"AlertReply\":{"
                + "\"text\":\"" + text + "\""
                + "}}";
    }
}
