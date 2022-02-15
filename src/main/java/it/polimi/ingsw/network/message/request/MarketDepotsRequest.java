package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Sends to the server the chosen amount of Resources to discard
 */
public class MarketDepotsRequest extends Message {
    private final List<Integer> total;
    private final List<Integer> discard;

    public MarketDepotsRequest(String player, List<Integer> total, List<Integer> discard) {
        super(Type.MARKETDEPOTS, player);
        this.total = new ArrayList<>(total);
        this.discard = new ArrayList<>(discard);
    }

    public List<Integer> getTotal() {
        return total;
    }

    public List<Integer> getDiscard() {
        return discard;
    }

    @Override
    public String toString() {
        return "MarketDepotsRequest{" +
                "total=" + total +
                ", discard=" + discard +
                '}';
    }
}
