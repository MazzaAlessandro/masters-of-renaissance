package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Asks the player which row of marbles he wants to take from the market
 */
public class MarketPositionReply extends Message {
    private final List<List<Color>> rows;
    private final Color slideMarble;
    public MarketPositionReply(List<List<Color>> rows, Color slideMarble) {
        super(Type.MARKETPOSITION, null);
        this.rows = new ArrayList<>();
        for (List<Color> row:rows) {
            this.rows.add(new ArrayList<>(row));
        }
        this.slideMarble = slideMarble;
    }

    public List<List<Color>> getRows() {
        return rows;
    }

    public Color getSlideMarble() {
        return slideMarble;
    }

    @Override
    public String toString() {
        return "MarketPositionReply{" +
                "rows=" + rows +
                ", slideMarble=" + slideMarble +
                '}';
    }
}
