package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Sends the chosen action to the server
 */
public class EndGameRequest extends Message {

    private int chosenAction;

    public EndGameRequest(String sender, int chosenAction) {
        super(Type.ENDGAME, sender);
        this.chosenAction = chosenAction;
    }

    public int getChosenAction() {
        return chosenAction;
    }

    @Override
    public String toString() {
        return "EndGameRequest{" +
                "chosenAction=" + chosenAction +
                '}';
    }
}
