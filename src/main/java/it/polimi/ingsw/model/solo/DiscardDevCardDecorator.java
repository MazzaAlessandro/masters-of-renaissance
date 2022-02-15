package it.polimi.ingsw.model.solo;

import it.polimi.ingsw.model.enumerations.Color;

/**
 * Additional function added by the decorator for tokens that discard development cards
 */
public class DiscardDevCardDecorator extends EffectDecorator {
    Color color;
    public DiscardDevCardDecorator(Color color){
        this.color = color;
    }

    @Override
    void doAction(SoloOpponent opponent) {
        opponent.getDevCardsTray().tokenDiscardDevCards(this.color);
    }

}
