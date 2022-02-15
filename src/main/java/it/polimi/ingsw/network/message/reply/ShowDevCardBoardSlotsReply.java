package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to show the Development Cards to the player
 */
public class ShowDevCardBoardSlotsReply extends Message {
    private final List<List<String>> topCards;
    private final List<String> middleCards;
    private final List<String> bottomCards;

    public ShowDevCardBoardSlotsReply(List<List<String>> topCards, List<String> middleCards, List<String> bottomCards) {
        super(Type.SHOWDEVCARDBOARDSLOTS, null);
        this.topCards = new ArrayList<>(topCards);
        this.middleCards = new ArrayList<>(middleCards);
        this.bottomCards = new ArrayList<>(bottomCards);
    }

    public List<List<String>> getTopCards() {
        return topCards;
    }

    public List<String> getMiddleCards() {
        return middleCards;
    }

    public List<String> getBottomCards() {
        return bottomCards;
    }

    @Override
    public String toString() {
        return "{\"ShowDevCardBoardSlotsReply\":{"
                + "\"topCards\":" + topCards
                + ",\"middleCards\":" + middleCards
                + ",\"bottomCards\":" + bottomCards
                + "}}";
    }

}
