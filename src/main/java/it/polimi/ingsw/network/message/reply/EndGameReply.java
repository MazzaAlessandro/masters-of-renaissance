package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Asks the player if he wants to play again or to quit the game once a match ended
 */
public class EndGameReply extends Message {

    public EndGameReply(){
        super(Type.ENDGAME, null);
    }

    @Override
    public String toString() {
        return "EndGameReply{}";
    }
}
