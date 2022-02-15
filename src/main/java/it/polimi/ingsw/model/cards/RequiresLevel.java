package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enumerations.Color;

/**
 * Represents the specific level of DevelopmentCard required to activate the LeaderCard
 */
public class RequiresLevel extends Requirements {
    private final Color requiredColor;
    private final int requiredLevel;

    public RequiresLevel(Color requiredColor, int requiredLevel) {
        this.requiredColor = requiredColor;
        this.requiredLevel = requiredLevel;
    }


    public Color getRequiredColor() {
        return requiredColor;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    @Override
    public String toString() {
        return "{\"RequiresLevel\":{"
                + "\"requiredColor\":\"" + requiredColor + "\""
                + ",\"requiredLevel\":\"" + requiredLevel + "\""
                + "}}";
    }
}
