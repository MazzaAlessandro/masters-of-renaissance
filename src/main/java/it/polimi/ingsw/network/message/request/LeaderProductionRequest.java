package it.polimi.ingsw.network.message.request;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;

import java.util.List;

/**
 * Sends the chosen output to the server
 */
public class LeaderProductionRequest extends Message {
    private final List<Color> colors;
    private final List<Integer> selectedLeaders;

    public LeaderProductionRequest(String player, List<Color> colors, List<Integer> selectedLeaders) {
        super(Type.LEADERPRODUCTION, player);
        this.colors = colors;
        this.selectedLeaders = selectedLeaders;
    }

    public List<Color> getColors() {
        return colors;
    }

    public List<Integer> getSelectedLeaders() {
        return selectedLeaders;
    }

    @Override
    public String toString() {
        return "LeaderProductionRequest{" +
                "colors=" + colors +
                '}';
    }
}
