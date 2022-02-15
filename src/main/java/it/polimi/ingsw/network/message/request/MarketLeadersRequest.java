package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Sends to the server the chosen amount of white marble to convert for each color
 */
public class MarketLeadersRequest extends Message {
    private final List<Integer> numberToConvert;
    private final List<Color> colors;

    public MarketLeadersRequest(String player, List<Integer> numberToConvert, List<Color> colors) {
        super(Type.MARKETLEADERS, player);
        this.numberToConvert = new ArrayList<>(numberToConvert);
        this.colors = new ArrayList<>(colors);
    }

    public List<Integer> getNumberToConvert() {
        return numberToConvert;
    }

    public List<Color> getColors() {
        return colors;
    }

    @Override
    public String toString() {
        return "{\"MarketLeadersRequest\":{"
                + "\"numberToConvert\":" + numberToConvert
                + ",\"colors\":" + colors
                + "}}";
    }
}
