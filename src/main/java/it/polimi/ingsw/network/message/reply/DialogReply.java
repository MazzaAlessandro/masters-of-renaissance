package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Sends a basic message to the player (often used to convey infos like whenever somebody disconnects from the server)
 */
public class DialogReply extends Message {
    private final String text;
    public DialogReply(String text, String nickname) {
        super(Type.DIALOG, nickname);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "{\"DialogReply\":{"
                + "\"text\":\"" + text + "\""
                + "}}";
    }
}
