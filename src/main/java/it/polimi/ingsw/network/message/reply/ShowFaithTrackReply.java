package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to show the Faith Track to the player
 */
public class ShowFaithTrackReply extends Message {
    private final int faithMarkerPosition;
    private final List<Boolean> popeFavors;

    public ShowFaithTrackReply(int faithMarkerPosition, List<Boolean> popeFavors) {
        super(Type.SHOWFAITHTRACK, null);
        this.faithMarkerPosition = faithMarkerPosition;
        this.popeFavors = new ArrayList<>(popeFavors);
    }

    public int getFaithMarkerPosition() {
        return faithMarkerPosition;
    }

    public List<Boolean> getPopeFavor() {
        return popeFavors;
    }

    @Override
    public String toString() {
        return "{\"ShowFaithTrackReply\":{"
                + "\"FaithMarkerPosition\":\"" + faithMarkerPosition + "\""
                + ",\"PopeFavors\":" + popeFavors
                + "}}";
    }
}
