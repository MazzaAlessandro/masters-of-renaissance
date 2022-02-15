package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.SpecialAbility;
import it.polimi.ingsw.model.enumerations.StorableResources;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class BoardTest {
    private Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void getColorsByLevel() {
        List<Color> list = board.getColorsByLevel(3);
        assertEquals(0, list.size());

        DevelopmentCard devcard1, devcard2;
        devcard1 = new DevelopmentCard(null, Color.GREEN,1,null,false,0);
        devcard2 = new DevelopmentCard(null, Color.GREEN,2,null,false,0);

        board.getDevelopmentCards().get(0).add(devcard1);
        board.getDevelopmentCards().get(0).add(devcard2);

        list = board.getColorsByLevel(1);
        assertEquals(1, list.size());
        list = board.getColorsByLevel(2);
        assertEquals(1, list.size());
        list = board.getColorsByLevel(3);
        assertEquals(0, list.size());

        board.getDevelopmentCards().get(1).add(devcard1);
        list = board.getColorsByLevel(1);
        assertEquals(2, list.size());

    }


    @Test
    public void getTotalResources() {
        assertEquals(0, board.getTotalResources().getAmount());

        Resources toStore, toTake;
        toStore = new Resources(1,1,1,0);
        board.getWarehouseDepots().storeResources(toStore);
        assertEquals(3, board.getTotalResources().getAmount());

        toStore = new Resources(3,3,5,7);
        board.getStrongBox().add(toStore);
        assertEquals(21, board.getTotalResources().getAmount());

        toTake = new Resources(2,2,2,2);
        board.spendResources(toTake);
        assertEquals(0, board.getWarehouseDepots().getValue().getAmount());
        assertEquals(13, board.getStrongBox().getAmount());
        assertEquals(13, board.getTotalResources().getAmount());
    }

    @Test
    public void increaseFaith() {
        assertEquals(0, board.getFaithMarker());

        board.increaseFaith(1);
        assertEquals(1, board.getFaithMarker());

        board.increaseFaith(10);
        board.increaseFaith(13);
        assertEquals(24, board.getFaithMarker());

        board.increaseFaith(5);
        assertEquals(24, board.getFaithMarker());
    }

    @Test
    public void setPopeFavorTiles() {
        board.setPopeFavorTiles(0);
        board.setPopeFavorTiles(1);
        board.setPopeFavorTiles(2);
        assertFalse(board.getPopeFavorTiles()[0]);
        assertFalse(board.getPopeFavorTiles()[1]);
        assertFalse(board.getPopeFavorTiles()[2]);

        board.setFaithMarker(7);
        board.setPopeFavorTiles(0);
        board.setPopeFavorTiles(1);
        board.setPopeFavorTiles(2);
        assertTrue(board.getPopeFavorTiles()[0]);
        assertFalse(board.getPopeFavorTiles()[1]);
        assertFalse(board.getPopeFavorTiles()[2]);

        board.setFaithMarker(15);
        board.setPopeFavorTiles(0);
        board.setPopeFavorTiles(1);
        board.setPopeFavorTiles(2);
        assertTrue(board.getPopeFavorTiles()[0]);
        assertTrue(board.getPopeFavorTiles()[1]);
        assertFalse(board.getPopeFavorTiles()[2]);

        board.setFaithMarker(24);
        board.setPopeFavorTiles(0);
        board.setPopeFavorTiles(1);
        board.setPopeFavorTiles(2);
        assertTrue(board.getPopeFavorTiles()[0]);
        assertTrue(board.getPopeFavorTiles()[1]);
        assertTrue(board.getPopeFavorTiles()[2]);
    }

    @Test
    public void getVictoryPoints() {
        assertEquals(0, board.getVictoryPoints());
        board.increaseFaith(3);
        assertEquals(1, board.getVictoryPoints());
        board.increaseFaith(3);
        assertEquals(2, board.getVictoryPoints());
        board.increaseFaith(3);
        assertEquals(4, board.getVictoryPoints());
        board.increaseFaith(3);
        assertEquals(6, board.getVictoryPoints());
        board.increaseFaith(3);
        assertEquals(9, board.getVictoryPoints());
        board.increaseFaith(3);
        assertEquals(12, board.getVictoryPoints());
        board.increaseFaith(3);
        assertEquals(16, board.getVictoryPoints());
        board.increaseFaith(3);
        assertEquals(20, board.getVictoryPoints());
        board.increaseFaith(3);
        assertEquals(20, board.getVictoryPoints());

        board.setFaithMarker(7);
        board.setPopeFavorTiles(0);
        assertEquals(4, board.getVictoryPoints());

        board.setFaithMarker(15);
        board.setPopeFavorTiles(1);
        board.setPopeFavorTiles(2);
        assertEquals(14, board.getVictoryPoints());

        board.setFaithMarker(24);
        board.setPopeFavorTiles(0);
        board.setPopeFavorTiles(1);
        board.setPopeFavorTiles(2);
        assertEquals(29, board.getVictoryPoints());

        DevelopmentCard devcard1,devcard2;
        devcard1 = new DevelopmentCard(null, Color.GREEN,1,null,false,2);
        devcard2 = new DevelopmentCard(null, Color.GREEN,2,null,false,4);
        board.getDevelopmentCards().get(0).add(devcard1);
        board.getDevelopmentCards().get(0).add(devcard2);
        board.getDevelopmentCards().get(1).add(devcard1);
        assertEquals(37, board.getVictoryPoints());

        LeaderCard leaderCard1, leaderCard2;
        leaderCard1 = new LeaderCard(null, null, null, null, 1, null, null, null, 4);
        leaderCard2 = new LeaderCard(null, null, null, null, 1, null, null, null, 3 );
        board.getLeaderCards().add(leaderCard1);
        board.getLeaderCards().add(leaderCard2);
        assertEquals(37, board.getVictoryPoints());
        board.getLeaderCards().get(0).setActive(true);
        assertEquals(41, board.getVictoryPoints());
        board.getLeaderCards().get(1).setActive(true);
        assertEquals(44, board.getVictoryPoints());


        Resources toStore;
        toStore = new Resources(1,0,0,0);
        board.getStrongBox().add(toStore);
        assertEquals(44, board.getVictoryPoints());
        toStore = new Resources(1,1,1,1);
        board.getStrongBox().add(toStore);
        assertEquals(45, board.getVictoryPoints());
        board.getWarehouseDepots().storeResources(toStore);
        toStore = new Resources(3,3,5,7);
        board.getStrongBox().add(toStore);
        assertEquals(49, board.getVictoryPoints());
    }

    @Test
    public void spendResources() {
        Resources toStore, toTake;
        toStore = new Resources(1,1,1,0);
        board.getWarehouseDepots().storeResources(toStore);
        toStore = new Resources(3,3,5,7);
        board.getStrongBox().add(toStore);
        toTake = new Resources(1,1,1,1);
        board.spendResources(toTake);
        assertEquals(0, board.getWarehouseDepots().getValue().getAmount());
        assertEquals(17, board.getStrongBox().getAmount());
        assertEquals(6, board.getStrongBox().getShields());

        toTake = new Resources(5,5,7,7);
        board.spendResources(toTake);
        assertEquals(0, board.getStrongBox().getAmount());
    }

    @Test
    public void takeResources() {
        Resources boughtResources;
        List<Color> colorList = new ArrayList<>();
        colorList.add(Color.WHITE);
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.YELLOW);
        colorList.add(Color.GREY);
        colorList.add(Color.PURPLE);
        boughtResources = board.takeResources(colorList);
        assertEquals(1, board.getFaithMarker());
        assertEquals(1, boughtResources.getCoins());
        assertEquals(1, boughtResources.getShields());
        assertEquals(1, boughtResources.getServants());
        assertEquals(1, boughtResources.getStones());

        List<Color> colorList2 = new ArrayList<>();
        colorList2.add(Color.YELLOW);
        colorList2.add(Color.YELLOW);
        colorList2.add(Color.YELLOW);
        colorList2.add(Color.YELLOW);
        colorList2.add(Color.YELLOW);
        boughtResources = board.takeResources(colorList2);
        assertEquals(1, board.getFaithMarker());
        assertEquals(5, boughtResources.getCoins());
        assertEquals(0, boughtResources.getShields());
        assertEquals(0, boughtResources.getServants());
        assertEquals(0, boughtResources.getStones());

        colorList = new ArrayList<>();
        colorList.add(Color.BLUE);
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.GREEN);
        colorList.add(Color.GREY);
        colorList.add(Color.BLUE);
        boughtResources = board.takeResources(colorList);
        assertEquals(2, board.getFaithMarker());
        assertEquals(0, boughtResources.getCoins());
        assertEquals(3, boughtResources.getShields());
        assertEquals(0, boughtResources.getServants());
        assertEquals(1, boughtResources.getStones());
    }

    @Test
    public void addToStrongbox() {
        assertTrue(board.getStrongBox().isEmpty());

        Resources toAdd = new Resources(1,2,3,4,0);
        board.addToStrongbox(toAdd);
        assertEquals(1, board.getStrongBox().getCoins());
        assertEquals(2, board.getStrongBox().getStones());
        assertEquals(3, board.getStrongBox().getServants());
        assertEquals(4, board.getStrongBox().getShields());
        assertEquals(0, board.getStrongBox().getFaith());

        toAdd = new Resources(4,3,2,1,3);
        board.addToStrongbox(toAdd);
        assertEquals(5, board.getStrongBox().getCoins());
        assertEquals(5, board.getStrongBox().getStones());
        assertEquals(5, board.getStrongBox().getServants());
        assertEquals(5, board.getStrongBox().getShields());
        assertEquals(0, board.getStrongBox().getFaith());
        assertEquals(3, board.getFaithMarker());
    }

    @Test
    public void buyDevCard() {
        DevelopmentCard devcard1, devcard2;
        Resources cardCost = new Resources(1,1,1,1);
        Resources toAdd = new Resources(100,100,100,100,0);
        board.addToStrongbox(toAdd);
        devcard1 = new DevelopmentCard(cardCost, Color.GREEN,1,null,false,2);
        devcard2 = new DevelopmentCard(cardCost, Color.BLUE,1,null,false,4);
        board.buyDevCard(devcard1, board.getDevelopmentCards().get(0));
        assertEquals(99, board.getStrongBox().getCoins());
        assertEquals(99, board.getStrongBox().getStones());
        assertEquals(99, board.getStrongBox().getServants());
        assertEquals(99, board.getStrongBox().getShields());
        assertSame(board.getDevelopmentCards().get(0).get(0), devcard1);

        board.buyDevCard(devcard2, board.getDevelopmentCards().get(1));
        assertEquals(98, board.getStrongBox().getCoins());
        assertEquals(98, board.getStrongBox().getStones());
        assertEquals(98, board.getStrongBox().getServants());
        assertEquals(98, board.getStrongBox().getShields());
        assertSame(board.getDevelopmentCards().get(1).get(0), devcard2);
    }

    @Test
    public void activateLeaderCard() {
        LeaderCard leaderCard1 = new LeaderCard(null, null, null, null, 1, SpecialAbility.EXTRADEPOT, Color.BLUE, null, 4 );
        LeaderCard leaderCard2 = new LeaderCard(null, null, null, null, 1, SpecialAbility.DISCOUNT, Color.GREY, null, 3);
        board.getLeaderCards().add(leaderCard1);
        board.getLeaderCards().add(leaderCard2);

        board.activateLeaderCard(leaderCard1);
        assertEquals(4, board.getWarehouseDepots().getLayers().size());
        assertSame(board.getWarehouseDepots().getLayers().get(3).getLayerType(), StorableResources.SHIELDS);

        board.activateLeaderCard(leaderCard2);
        Resources cardCost = new Resources(1,1,1,1);
        Resources toAdd = new Resources(100,100,100,100,0);
        DevelopmentCard devcard1 = new DevelopmentCard(cardCost, Color.GREEN,1,null,false,2);
        board.addToStrongbox(toAdd);
        board.buyDevCard(devcard1, board.getDevelopmentCards().get(0));
        assertEquals(99, board.getStrongBox().getCoins());
        assertEquals(100, board.getStrongBox().getStones());
        assertEquals(99, board.getStrongBox().getServants());
        assertEquals(99, board.getStrongBox().getShields());
        assertSame(board.getDevelopmentCards().get(0).get(0), devcard1);
    }
}