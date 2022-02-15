package it.polimi.ingsw.network.message.reply;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Asks the player the amount of white marbles he wants to convert to another color with the Leader Card's special ability
 * This is sent only if the player has 2 different Leader Card with the WHITEMARBLE ability active
 */
public class MarketLeadersReply extends Message {
    private final List<Color> leadersColor;
    private final List<Color> takenColors;
    public MarketLeadersReply(List<Color> leadersColor, List<Color> takenColors) {
        super(Type.MARKETLEADERS, null);
        this.leadersColor = new ArrayList<>(leadersColor);
        this.takenColors = new ArrayList<>(takenColors);
    }

    public List<Color> getLeadersColor() {
        return leadersColor;
    }

    public List<Color> getTakenColors() {
        return takenColors;
    }

    @Override
    public String toString() {
        return "{\"MarketLeadersReply\":{"
                + "\"leadersColor\":" + leadersColor
                + ",\"takenColors\":" + takenColors
                + "}}";
    }
}
