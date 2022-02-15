package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.StorableResources;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResourcesTest {
    private Resources resources;

    @Before
    public void setUp(){
        resources = new Resources();
    }

    @Test
    public void typeVariety() {
        assertEquals(0, resources.typeVariety());

        resources.setResources(1,0,0,0,0);
        assertEquals(1, resources.typeVariety());

        resources.setResources(2,0,0,0,3);
        assertEquals(2, resources.typeVariety());

        resources.setResources(2,0,3,0,0);
        assertEquals(2, resources.typeVariety());

        resources.setResources(1,1,1,1,1);
        assertEquals(5, resources.typeVariety());
    }

    @Test
    public void add() {
        assertTrue(resources.isEmpty());

        Resources toAdd = new Resources(1,2,3,4,5);
        resources.add(toAdd);
        assertFalse(resources.isEmpty());
        assertEquals(1, resources.getCoins());
        assertEquals(2, resources.getStones());
        assertEquals(3, resources.getServants());
        assertEquals(4, resources.getShields());
        assertEquals(5, resources.getFaith());

        toAdd = new Resources(5,4,3,2,1);
        resources.add(toAdd);
        assertFalse(resources.isEmpty());
        assertEquals(6, resources.getCoins());
        assertEquals(6, resources.getStones());
        assertEquals(6, resources.getServants());
        assertEquals(6, resources.getShields());
        assertEquals(6, resources.getFaith());
    }

    @Test
    public void subtract() {
        assertTrue(resources.isEmpty());

        resources.setResources(6,6,6,6,6);
        Resources toSubtract = new Resources(5,4,3,2,1);
        resources.subtract(toSubtract);
        assertFalse(resources.isEmpty());
        assertEquals(1, resources.getCoins());
        assertEquals(2, resources.getStones());
        assertEquals(3, resources.getServants());
        assertEquals(4, resources.getShields());
        assertEquals(5, resources.getFaith());

        toSubtract = new Resources(2,2,2,2,2);
        resources.subtract(toSubtract);
        assertFalse(resources.isEmpty());
        assertEquals(0, resources.getCoins());
        assertEquals(0, resources.getStones());
        assertEquals(1, resources.getServants());
        assertEquals(2, resources.getShields());
        assertEquals(3, resources.getFaith());

        toSubtract = new Resources(3,3,3,3,3);
        resources.subtract(toSubtract);
        assertTrue(resources.isEmpty());
    }

    @Test
    public void subtractAmount() {
        assertTrue(resources.isEmpty());

        resources.setResources(6,6,6,6, 0);
        resources.subtractAmount(3, StorableResources.COINS);
        assertEquals(3, resources.getCoins());
        assertEquals(6, resources.getStones());
        assertEquals(6, resources.getServants());
        assertEquals(6, resources.getShields());

        resources.subtractAmount(2, StorableResources.STONES);
        resources.subtractAmount(2, StorableResources.STONES);
        assertEquals(3, resources.getCoins());
        assertEquals(2, resources.getStones());
        assertEquals(6, resources.getServants());
        assertEquals(6, resources.getShields());

        resources.subtractAmount(5, StorableResources.COINS);
        resources.subtractAmount(5, StorableResources.STONES);
        resources.subtractAmount(3, StorableResources.SERVANTS);
        resources.subtractAmount(3, StorableResources.SHIELDS);
        assertEquals(0, resources.getCoins());
        assertEquals(0, resources.getStones());
        assertEquals(3, resources.getServants());
        assertEquals(3, resources.getShields());

        resources.subtractAmount(5, StorableResources.SERVANTS);
        resources.subtractAmount(5, StorableResources.SHIELDS);
        assertTrue(resources.isEmpty());
    }

    @Test
    public void getAmount() {
        assertEquals(0, resources.getAmount());

        resources.setResources(1,0,0,0,0);
        assertEquals(1, resources.getAmount());

        resources.setResources(4,3,0,0,0);
        assertEquals(7, resources.getAmount());

        resources.setResources(1,2,3,4,5);
        assertEquals(10, resources.getAmount());
    }

    @Test
    public void getBiggerResourceType() {
        assertSame(resources.getBiggerResourceType(), StorableResources.COINS);

        resources.setResources(0,1,0,0,0);
        assertSame(resources.getBiggerResourceType(), StorableResources.STONES);

        resources.setResources(1,2,3,3,0);
        assertSame(resources.getBiggerResourceType(), StorableResources.SERVANTS);

        resources.setResources(0,1,0,0,3);
        assertSame(resources.getBiggerResourceType(), StorableResources.STONES);
    }

    @Test
    public void getResourceAmount() {
        resources.setResources(1,2,3,4,0);
        assertEquals(1, resources.getResourceAmount(StorableResources.COINS));
        assertEquals(2, resources.getResourceAmount(StorableResources.STONES));
        assertEquals(3, resources.getResourceAmount(StorableResources.SERVANTS));
        assertEquals(4, resources.getResourceAmount(StorableResources.SHIELDS));
    }

    @Test
    public void biggerThan() {
        resources.setResources(1,2,3,4,0);
        Resources bigger, smaller;
        bigger = new Resources(2,2,2,2,0);
        smaller = new Resources(1,1,1,1,0);

        assertTrue(resources.biggerThan(smaller));
        assertFalse(resources.biggerThan(bigger));
    }

    @Test
    public void testEquals() {
        resources.setResources(3,2,5,6,1);

        Resources equalsOK, equalsKO;
        equalsOK = new Resources(3,2,5,6,1);
        equalsKO = new Resources(1,1,1,1,1);

        assertTrue(resources.equals(equalsOK));
        assertFalse(resources.equals(equalsKO));
    }

    @Test
    public void isEmpty() {
        assertTrue(resources.isEmpty());

        resources.setResources(1,0,0,0,0);
        assertFalse(resources.isEmpty());
    }
}