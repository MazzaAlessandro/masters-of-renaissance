package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to notify whenever the player needs to see Lorenzo's move
 */
public class ShowLorenzoFaithTrackRequest extends Message {

    public ShowLorenzoFaithTrackRequest(String player) {
        super(Type.SHOWLORENZOFAITHTRACK, player);
    }

    @Override
    public String toString() {
        return "ShowLorenzoFaithTrackRequest{}";
    }
}
