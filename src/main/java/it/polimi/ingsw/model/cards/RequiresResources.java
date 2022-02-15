package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resources;

/**
 * Represents the amount of resources required to activate the LeaderCard
 */
public class RequiresResources extends Requirements {
    private final Resources required;

    public RequiresResources(Resources required) {
        this.required = required;
    }

    public Resources getRequired() {
        return required;
    }

    @Override
    public String toString() {
        return "{\"RequiresResources\":{"
                + "\"resources\":" + required
                + "}}";
    }
}
