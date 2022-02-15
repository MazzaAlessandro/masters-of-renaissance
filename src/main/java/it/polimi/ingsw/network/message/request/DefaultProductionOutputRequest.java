package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.List;

/**
 * Sends the chosen output for the Default Production to the server
 */
public class DefaultProductionOutputRequest extends Message {
    private final List<Color> colors;
    public DefaultProductionOutputRequest(String player, List<Color> colors) {
        super(Type.DEFAULTPRODUCTIONOUTPUT, player);
        this.colors = colors;
    }

    public List<Color> getColors() {
        return colors;
    }

    @Override
    public String toString() {
        return "DefaultProductionOutputRequest{" +
                "colors=" + colors +
                '}';
    }
}
