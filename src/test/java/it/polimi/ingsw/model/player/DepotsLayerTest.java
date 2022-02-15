package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.enumerations.StorableResources;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepotsLayerTest {

    private DepotsLayer testLayer;

    @Before
    public void setUp(){
    }

    @Test
    public void addToTotal() {
        Resources total = new Resources();
        assertEquals(0, total.getAmount());

        testLayer = new DepotsLayer(3, StorableResources.COINS);
        testLayer.setAmountStored(3);
        total.add(testLayer.addToTotal());
        assertEquals(3, total.getAmount());
        assertEquals(3, testLayer.getAmountStored());

        Resources toTake = new Resources(1, 0,0,0);
        testLayer.takeFromLayer(toTake);
        total = testLayer.addToTotal();
        assertEquals(2, total.getAmount());
    }

    @Test
    public void isEmpty() {
        testLayer = new DepotsLayer(1);
        assertTrue(testLayer.isEmpty());

        testLayer = new DepotsLayer(3, StorableResources.COINS);
        testLayer.setAmountStored(3);
        assertFalse(testLayer.isEmpty());

        Resources toTake = new Resources(3,0,0,0);
        testLayer.takeFromLayer(toTake);
        //assertTrue(testLayer.isEmpty());
        assertEquals(0, testLayer.getAmountStored());
    }

    @Test
    public void takeFromLayer() {
        Resources toTake = new Resources(1, 0,0,0);
        testLayer = new DepotsLayer(3, StorableResources.COINS);
        testLayer.setAmountStored(3);
        testLayer.takeFromLayer(toTake);
        assertEquals(2, testLayer.getAmountStored());

        toTake = new Resources(1, 2, 3,5);
        testLayer.takeFromLayer(toTake);
        assertEquals(1, testLayer.getAmountStored());

        toTake = new Resources(1, 0,0,0);
        testLayer.takeFromLayer(toTake);
        assertEquals(0, testLayer.getAmountStored());

        toTake = new Resources(1, 0,0,0);
        testLayer.takeFromLayer(toTake);
        assertEquals(0, testLayer.getAmountStored());
    }
}