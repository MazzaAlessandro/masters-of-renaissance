package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Color;
import java.util.List;

/**
 * Represents the specific color of DevelopmentCard required to activate the LeaderCard
 */
public class RequiresColors extends Requirements {
    private final List<Color> requires;

    public RequiresColors(List<Color> requires) {
        this.requires = requires;
    }

    public List<Color> getRequires() {
        return requires;
    }

    @Override
    public String toString() {
        return "{\"RequiresColors\":{"
                + "\"colors\":" + requires
                + "}}";
    }
}
