package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever the player needs to see his Faith Track
 */
public class ShowFaithTrackRequest extends Message {
    public ShowFaithTrackRequest(String player) {
        super(Type.SHOWFAITHTRACK, player);
    }

    @Override
    public String toString() {
        return "{ShowFaithTrackRequest}";
    }
}
