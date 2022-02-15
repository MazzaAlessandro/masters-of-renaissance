package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.List;

/**
 * Asks the player which card he wants to buy from the Tray
 */
public class BuyDevInTrayPositionReply extends Message {
    private final List<List<String>> tray;
    public BuyDevInTrayPositionReply(List<List<String>> tray) {
        super(Type.BUYDEVTRAYPOSITION, null);
        this.tray = tray;
    }

    public List<List<String>> getTray() {
        return tray;
    }

    @Override
    public String toString() {
        return "BuyDevInTrayPositionReply{" +
                "tray=" + tray +
                '}';
    }
}
