package it.polimi.ingsw.model.cards;

/**
 * Represent cards in general, both development cards and leader cards
 */
public abstract class Card {
    protected String face;
    protected int victoryPoints;


    public int getVictoryPoints() {
        return victoryPoints;
    }

    public String getFace() {
        return face;
    }
}

