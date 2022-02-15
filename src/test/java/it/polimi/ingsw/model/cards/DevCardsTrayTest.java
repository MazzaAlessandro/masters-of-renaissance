package it.polimi.ingsw.model.cards;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DevCardsTrayTest {
    DevCardsTray cardsTray;

    @Before
    public void setUp() {cardsTray = new DevCardsTray();}

    /**
     * Development card of level 1 needs Victory points in between 1 and 4 and cost is max 4 resources (could be same or different type)
     * Development card of level 2 needs Victory points in between 5 and 8 and cost is max 6 resources (could be same or different type)
     * Development card of level 3 needs Victory points in between 9 and 12 and cost is max 8 resources (could be same or different type)
     */


    @Test
    public void checkCards(){

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertTrue(cardsTray.getTray().get(i*3).get(j).getCost().getAmount() <= 4); //lv1 (0, 3, 6, 9)
                assertTrue(cardsTray.getTray().get(i*3).get(j).getVictoryPoints()>=1);
                assertTrue(cardsTray.getTray().get(i*3).get(j).getVictoryPoints() <=4);

                assertTrue(cardsTray.getTray().get(i*3 +1).get(j).getCost().getAmount() <= 6); //lv2 (1, 4, 7, 10)
                assertTrue(cardsTray.getTray().get(i*3 +1).get(j).getVictoryPoints()>=5);
                assertTrue(cardsTray.getTray().get(i*3 +1).get(j).getVictoryPoints() <= 8);

                assertTrue(cardsTray.getTray().get(i*3 +2).get(j).getCost().getAmount() <= 8); //lv3 (2, 5, 8, 11)
                assertTrue(cardsTray.getTray().get(i*3 +2).get(j).getVictoryPoints()>=9);
                assertTrue(cardsTray.getTray().get(i*3 +2).get(j).getVictoryPoints() <= 12);

            }
        }



    }
}
