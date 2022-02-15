package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Sends to the server the chosen amount of players for the match
 */
public class InitializeGameRequest extends Message {
private final int maxPlayersNumber;

    public InitializeGameRequest( int maxPlayers,String sender) {
        super(Type.INITIALIZE, sender);
        this.maxPlayersNumber = maxPlayers;
    }

    public int getMaxPlayersNumber() {
        return maxPlayersNumber;
    }

    @Override
    public String toString() {
        return "{\"InitializeGameRequest\":{"
                + "\"maxPlayersNumber\":\"" + maxPlayersNumber + "\""
                + "}}";
    }
}
