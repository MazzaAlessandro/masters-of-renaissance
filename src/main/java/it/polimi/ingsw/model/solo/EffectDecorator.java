package it.polimi.ingsw.model.solo;

/**
 * Decorator pattern for add functionality to some tokens
 */
public abstract class EffectDecorator extends SoloActionEffect {
    private SoloActionToken soloActionToken;

    @Override
    abstract void doAction(SoloOpponent opponent);

}
