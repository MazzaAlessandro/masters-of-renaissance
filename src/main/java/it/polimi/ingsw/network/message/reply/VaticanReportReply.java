package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

/**
 * Notifies that a Vatican Report triggered
 */
public class VaticanReportReply extends Message {

    private final int vaticanReportNumber;

    public int getVaticanReportNumber() {
        return vaticanReportNumber;
    }


    public VaticanReportReply(int vaticanReportNumber) {
        super(Type.VATICANREPORT, null);
        this.vaticanReportNumber = vaticanReportNumber;
    }

    @Override
    public String toString() {
        return "VaticanReportReply{}";
    }
}
