package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.List;

/**
 * Used to show the Development Card Tray to the player
 */
public class ShowDevCardTrayReply extends Message {
    private final List<List<String>> tray;
    public ShowDevCardTrayReply(List<List<String>> tray) {
        super(Type.SHOWDEVCARDTRAY, null);
        this.tray = tray;
    }

    public List<List<String>> getTray() {
        return tray;
    }

    @Override
    public String toString() {
        return "ShowDevCardTrayReply{" +
                "tray=" + tray +
                '}';
    }
}
