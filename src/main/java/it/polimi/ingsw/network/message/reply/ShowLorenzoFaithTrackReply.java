package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Used to show the Faith Track and the Action done by Lorenzo in the single-player game
 */
public class ShowLorenzoFaithTrackReply extends Message {

    int blackCrossPosition;
    String actionToken;

    public ShowLorenzoFaithTrackReply(int blackCrossPosition, String actionToken) {
        super(Type.SHOWLORENZOFAITHTRACK, null);
        this.blackCrossPosition = blackCrossPosition;
        this.actionToken = actionToken;
    }

    public int getBlackCrossPosition() {
        return blackCrossPosition;
    }

    public String getActionToken() {
        return actionToken;
    }

    @Override
    public String toString() {
        return "ShowLorenzoFaithTrackReply{" +
                "blackCrossPosition=" + blackCrossPosition +
                ", actionToken='" + actionToken + '\'' +
                '}';
    }
}
