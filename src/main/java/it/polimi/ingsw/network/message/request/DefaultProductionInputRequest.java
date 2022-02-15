package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.List;

/**
 * Sends the chosen input for the Default Production to the server
 */
public class DefaultProductionInputRequest extends Message {
    private final List<Color> colors;
    public DefaultProductionInputRequest(String player, List<Color> colors) {
        super(Type.DEFAULTPRODUCTIONINPUT, player);
        this.colors = colors;
    }

    public List<Color> getColors() {
        return colors;
    }

    @Override
    public String toString() {
        return "DefaultProductionInputRequest{" +
                "colors=" + colors +
                '}';
    }
}
