package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.solo.SoloOpponent;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {
    private Deck deck;

    @Before
    public void setUp() {
        deck = new Deck();
    }




    @Test
    public void pickCards(){

        assertTrue(deck.getCards().size() == 16);

        int i=1;
        int ok=15;
        deck.pickCard();
        assertTrue(deck.getCards().size() == ok);

        while(i<=15){
            ok--;
            i++;
            deck.pickCard();
            assertTrue(deck.getCards().size() == ok);

        }

    }

}

