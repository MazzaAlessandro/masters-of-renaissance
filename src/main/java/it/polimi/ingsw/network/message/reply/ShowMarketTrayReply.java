package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to show the Market Tray to the player
 */
public class ShowMarketTrayReply extends Message {

    private final List<List<Color>> rows;
    private final Color slideMarble;

    public ShowMarketTrayReply(List<List<Color>> rows, Color slideMarble) {
        super(Type.SHOWMARKETTRAY, null);
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
        return "{\"ShowMarketTrayReply\":{"
                + "\"rows\":" + rows
                + ",\"slideMarble\":\"" + slideMarble + "\""
                + "}}";
    }

}
