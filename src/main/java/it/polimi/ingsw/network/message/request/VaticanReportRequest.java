package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

public class VaticanReportRequest extends Message {

    public VaticanReportRequest(String player) {
        super(Type.VATICANREPORT, player);
    }

    @Override
    public String toString() {
        return "VaticanReportRequest{}";
    }
}
