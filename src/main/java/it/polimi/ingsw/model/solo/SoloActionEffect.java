package it.polimi.ingsw.model.solo;

import it.polimi.ingsw.model.enumerations.ActionType;

/**
 * Represents the effect of a SoloActionToken
 */
public abstract class SoloActionEffect {
    ActionType type;

    /**
     * Apply ActionToken effect.
     *
     * @param opponent is instance of the opponent in solo
     */
    abstract void doAction(SoloOpponent opponent);

}
