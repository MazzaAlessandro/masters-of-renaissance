package it.polimi.ingsw.network.message;

import java.io.Serializable;

/**
 * Class representing the objects through which the server and client communicate
 * Need implements Serializable for send object via ObjectOutputStream
 */
public abstract class Message implements Serializable {
    private final Type type;
    private final String player;

    public Message(Type type, String player) {
        this.type = type;
        this.player = player;
    }

    public Type getType() {
        return type;
    }

    public String getSender() {
        return player;
    }

    @Override
    public String toString() {
        return "{\"Message\":{"
                + "\"type\":\"" + type + "\""
                + ", \"player\":\"" + player + "\""
                + "}}";
    }
}
