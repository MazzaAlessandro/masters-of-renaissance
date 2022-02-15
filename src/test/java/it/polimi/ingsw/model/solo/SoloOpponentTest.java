package it.polimi.ingsw.model.solo;

import it.polimi.ingsw.model.cards.DevCardsTray;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SoloOpponentTest {
    private SoloOpponent soloOpponent;
    @Before
    public void setUp() {
        DevCardsTray devCardsTray = new DevCardsTray();
        soloOpponent = new SoloOpponent(devCardsTray);
    }
    @Test
    public void increaseBlackCross() {
        assertEquals(0,soloOpponent.getBlackCross());
        soloOpponent.increaseBlackCross(10);
        assertEquals(10,soloOpponent.getBlackCross());
    }

    @Test
    public void pickActionTokens() {
        assertEquals(0,soloOpponent.getBlackCross());
        for (int i = 0; i < 5; i++) {
            soloOpponent.pickActionToken();
        }
        assertTrue(soloOpponent.getBlackCross() > 0);

    }
}