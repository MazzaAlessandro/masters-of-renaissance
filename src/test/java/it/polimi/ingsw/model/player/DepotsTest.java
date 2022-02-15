package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.SpecialAbility;
import it.polimi.ingsw.model.enumerations.StorableResources;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepotsTest {

    private Depots depots;

    @Before
    public void setUp(){
        depots = new Depots();
    }

    @Test
    public void getValue() {
        assertEquals(0, depots.getValue().getAmount());

        Resources toStore, toTake;
        toStore = new Resources(1,0,0,0);
        depots.storeResources(toStore);
        assertEquals(1, depots.getValue().getAmount());

        toStore = new Resources(0,2,0,3);
        depots.storeResources(toStore);
        assertEquals(6, depots.getValue().getAmount());

        LeaderCard leaderCard1;
        leaderCard1 = new LeaderCard(null, null, null, null, 1, SpecialAbility.EXTRADEPOT, Color.BLUE, null, 4);
        depots.addLeaderCardLayer(leaderCard1);
        toStore = new Resources(0,0,0,2);
        depots.storeResources(toStore);
        assertEquals(8, depots.getValue().getAmount());

        depots.addLeaderCardLayer(leaderCard1);
        toStore = new Resources(2,0,0,0);
        depots.storeResources(toStore);
        assertEquals(10, depots.getValue().getAmount());

        toTake = new Resources(2,2,0,2);
        depots.takeResources(toTake);
        assertEquals(4, depots.getValue().getAmount());

    }

    @Test
    public void addLeaderCardLayer() {
        LeaderCard leaderCard1, leaderCard2;
        leaderCard1 = new LeaderCard(null, null, null, null, 1, SpecialAbility.EXTRADEPOT, Color.BLUE, null, 4);
        leaderCard2 = new LeaderCard(null, null, null, null, 1, SpecialAbility.EXTRADEPOT, Color.YELLOW, null, 4 );
        depots.addLeaderCardLayer(leaderCard1);
        depots.addLeaderCardLayer(leaderCard2);
        assertEquals(5, depots.getLayers().size());
    }

    @Test
    public void takeResources() {
        Resources toStore, toTakeOK;
        toStore = new Resources(1,2,3,0);
        depots.storeResources(toStore);
        assertEquals(6, depots.getValue().getAmount());

        toTakeOK = new Resources(0,2,0,0);
        depots.takeResources(toTakeOK);
        assertEquals(4, depots.getValue().getAmount());
        assertSame(depots.getLayers().get(0).getLayerType(), StorableResources.COINS);
        assertEquals(1, depots.getLayers().get(0).getAmountStored());
        //assertTrue(depots.getLayers().get(1).isEmpty());
        assertEquals(0, depots.getLayers().get(1).getAmountStored());
        assertSame(depots.getLayers().get(2).getLayerType(), StorableResources.SERVANTS);
        assertEquals(3, depots.getLayers().get(2).getAmountStored());

        toTakeOK = new Resources(1,0,3,0);
        depots.takeResources(toTakeOK);
        /*assertTrue(depots.getLayers().get(0).isEmpty());
        assertTrue(depots.getLayers().get(1).isEmpty());
        assertTrue(depots.getLayers().get(2).isEmpty());*/
        assertEquals(0, depots.getLayers().get(0).getAmountStored());
        assertEquals(0, depots.getLayers().get(1).getAmountStored());
        assertEquals(0, depots.getLayers().get(2).getAmountStored());

        toStore = new Resources(3,0,1,2);
        depots.storeResources(toStore);
        toTakeOK = new Resources(3,3,3,3);
        depots.takeResources(toTakeOK);
        /*assertTrue(depots.getLayers().get(0).isEmpty());
        assertTrue(depots.getLayers().get(1).isEmpty());
        assertTrue(depots.getLayers().get(2).isEmpty());*/
        assertEquals(0, depots.getLayers().get(0).getAmountStored());
        assertEquals(0, depots.getLayers().get(1).getAmountStored());
        assertEquals(0, depots.getLayers().get(2).getAmountStored());
    }

    @Test
    public void storeResources() {
        Resources toStore;
        toStore = new Resources(1,0,0,0);
        depots.storeResources(toStore);
        assertSame(depots.getLayers().get(2).getLayerType(), StorableResources.COINS);
        assertEquals(1, depots.getLayers().get(2).getAmountStored());
        assertTrue(depots.getLayers().get(0).isEmpty());
        assertTrue(depots.getLayers().get(1).isEmpty());

        toStore = new Resources(0,2,0,0);
        depots.storeResources(toStore);
        assertSame(depots.getLayers().get(1).getLayerType(), StorableResources.COINS);
        assertEquals(1, depots.getLayers().get(1).getAmountStored());
        assertSame(depots.getLayers().get(2).getLayerType(), StorableResources.STONES);
        assertEquals(2, depots.getLayers().get(2).getAmountStored());
        assertTrue(depots.getLayers().get(0).isEmpty());

        toStore = new Resources(1,1,0,1);
        depots.storeResources(toStore);
        assertSame(depots.getLayers().get(0).getLayerType(), StorableResources.SHIELDS);
        assertEquals(1, depots.getLayers().get(0).getAmountStored());
        assertSame(depots.getLayers().get(1).getLayerType(), StorableResources.COINS);
        assertEquals(2, depots.getLayers().get(1).getAmountStored());
        assertSame(depots.getLayers().get(2).getLayerType(), StorableResources.STONES);
        assertEquals(3, depots.getLayers().get(2).getAmountStored());

        LeaderCard leaderCard1, leaderCard2;
        leaderCard1 = new LeaderCard(null, null, null, null, 1, SpecialAbility.EXTRADEPOT, Color.BLUE, null, 4);
        leaderCard2 = new LeaderCard(null, null, null, null, 1, SpecialAbility.EXTRADEPOT, Color.YELLOW, null, 3);
        depots.addLeaderCardLayer(leaderCard1);
        toStore = new Resources(0,0,1,0);
        depots.storeResources(toStore);
        assertSame(depots.getLayers().get(0).getLayerType(), StorableResources.SERVANTS);
        assertEquals(1, depots.getLayers().get(0).getAmountStored());
        assertSame(depots.getLayers().get(1).getLayerType(), StorableResources.COINS);
        assertEquals(2, depots.getLayers().get(1).getAmountStored());
        assertSame(depots.getLayers().get(2).getLayerType(), StorableResources.STONES);
        assertEquals(3, depots.getLayers().get(2).getAmountStored());
        assertSame(depots.getLayers().get(3).getLayerType(), StorableResources.SHIELDS);
        assertEquals(1, depots.getLayers().get(3).getAmountStored());

        depots.addLeaderCardLayer(leaderCard2);
        toStore = new Resources(1,0,1,1);
        depots.storeResources(toStore);
        assertSame(depots.getLayers().get(0).getLayerType(), StorableResources.COINS);
        assertEquals(1, depots.getLayers().get(0).getAmountStored());
        assertSame(depots.getLayers().get(1).getLayerType(), StorableResources.SERVANTS);
        assertEquals(2, depots.getLayers().get(1).getAmountStored());
        assertSame(depots.getLayers().get(2).getLayerType(), StorableResources.STONES);
        assertEquals(3, depots.getLayers().get(2).getAmountStored());
        assertSame(depots.getLayers().get(3).getLayerType(), StorableResources.SHIELDS);
        assertEquals(2, depots.getLayers().get(3).getAmountStored());
        assertSame(depots.getLayers().get(4).getLayerType(), StorableResources.COINS);
        assertEquals(2, depots.getLayers().get(4).getAmountStored());
    }

    @Test
    public void canBeStored() {
        Resources toStoreOK, toStoreKO;
        toStoreOK = new Resources(1,2,3,0);
        toStoreKO = new Resources(1,1,1,1);
        assertTrue(depots.canBeStored(toStoreOK));
        assertFalse(depots.canBeStored(toStoreKO));
        toStoreKO = new Resources(2,2,2,0);
        assertFalse(depots.canBeStored(toStoreKO));

        depots.storeResources(toStoreOK);
        toStoreKO = new Resources(1,0,0,0);
        assertFalse(depots.canBeStored(toStoreKO));

        LeaderCard leaderCard1, leaderCard2;
        leaderCard1 = new LeaderCard(null, null, null, null, 1, SpecialAbility.EXTRADEPOT, Color.BLUE, null, 4 );
        leaderCard2 = new LeaderCard(null, null, null, null, 1, SpecialAbility.EXTRADEPOT, Color.YELLOW, null, 3 );
        depots.addLeaderCardLayer(leaderCard1);
        toStoreOK = new Resources(0,0,0, 2);
        toStoreKO = new Resources(1,0,0,0);
        assertTrue(depots.canBeStored(toStoreOK));
        assertFalse(depots.canBeStored(toStoreKO));

        depots.storeResources(toStoreOK);
        depots.addLeaderCardLayer(leaderCard2);
        toStoreOK = new Resources(2,0,0,0);
        assertTrue(depots.canBeStored(toStoreOK));
        depots.storeResources(toStoreOK);
        toStoreKO = new Resources(0,0,0, 1);
        assertFalse(depots.canBeStored(toStoreKO));
    }
}