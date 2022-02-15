package it.polimi.ingsw.model.solo;

import it.polimi.ingsw.model.enumerations.ActionType;
import it.polimi.ingsw.model.enumerations.Color;


/**
 * Represents the Token in the game and its action when they are activated
 */
public class SoloActionToken {
    private String icon;
    private final ActionType type;
    private SoloActionEffect effect;
    private final Color color;

    /**
     * @param type  pass 1 of 3 possible parameter
     * @param color is not null if the type is equal to DISCARDDEVCARDS
     *              and represents the color of the development cards to be discarded.
     */
    public SoloActionToken(ActionType type, Color color) {
        this.type = type;
        this.color = color;
    }

    /**
     * Set the correct effect according to type of ActionToken.
     */
    public void setEffect() {
        if (this.type == ActionType.DISCARDDEVCARDS) {
            effect = new DiscardDevCardDecorator(this.color);
        } else {
            effect = new ConcreteEffect(type);
        }
    }

    public SoloActionEffect getEffect() {
        return this.effect;
    }

    public ActionType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    /**
     * @return all important information of this ActionToken, in json like format
     */
    @Override
    public String toString() {
        if (type.equals(ActionType.DISCARDDEVCARDS)) {
            return "{\"type\":\"" + type + "\""
                    + ", \"color\":\"" + color + "\""
                    + "}";
        } else {
            return "{\"type\":\"" + type + "\""
                    + "}";
        }

    }


}

