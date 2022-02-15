package it.polimi.ingsw.model.solo;

import it.polimi.ingsw.model.enumerations.ActionType;

/**
 * Implementation of token effects where decorator is not needed
 */
public class ConcreteEffect extends SoloActionEffect {

    public ConcreteEffect(ActionType type) {
        this.type = type;
    }

    @Override
    void doAction(SoloOpponent opponent) {
        switch (type) {
            case MOVE2FORWARD:
                opponent.increaseBlackCross(2);
                break;
            case MOVEANDSHUFFLE:
                opponent.increaseBlackCross(1);
                opponent.shuffleActionTokens();
                break;
        }
    }
}
