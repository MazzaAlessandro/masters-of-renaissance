package it.polimi.ingsw.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarketTrayTest {
    private MarketTray marketTray;
    
    @Before
    public void setUp(){
        marketTray=new MarketTray();
    }

    @Test
    public void takeMarble() {
        for (int i = 1; i < 5; i++) {
            assertTrue(marketTray.takeMarble(i).size() == 3);
        }
        for (int i = 5; i < 8; i++) {
            assertTrue(marketTray.takeMarble(i).size() == 4);
        }
    }
}