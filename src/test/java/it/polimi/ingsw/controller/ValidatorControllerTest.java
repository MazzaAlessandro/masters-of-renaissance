package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.cards.DevCardsTray;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.LeaderCardsRequirements;
import it.polimi.ingsw.model.player.Depots;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.solo.SoloOpponent;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ValidatorControllerTest {
    private ValidatorController validator;

    @Before
    public void setUp() {
        validator = new ValidatorController();
    }

    @Test
    public void isOpponentEndingFaithTrack() {
        DevCardsTray devCardsTray = new DevCardsTray();
        SoloOpponent opponent = new SoloOpponent(devCardsTray);
        assertFalse(validator.isOpponentEndingFaithTrack(opponent));
        opponent.increaseBlackCross(24);
        assertTrue(validator.isOpponentEndingFaithTrack(opponent));
    }

    @Test
    public void canBeStoredInDepots() {
        Depots depots = new Depots();
        Resources ok1,ok2,ok3,no1,no2,no3;
        ok1 = new Resources(0,0,0,0);
        ok2 = new Resources(1,2,0,3);
        ok3 = new Resources(0,1,2,2);
        no1 = new Resources(0,2,2,2);
        no2 = new Resources(0,1,2,4);
        no3 = new Resources(1,1,1,1);

        assertTrue(validator.canBeStoredInDepots(depots, ok1));
        assertTrue(validator.canBeStoredInDepots(depots, ok2));
        assertTrue(validator.canBeStoredInDepots(depots, ok3));
        assertFalse(validator.canBeStoredInDepots(depots, no1));
        assertFalse(validator.canBeStoredInDepots(depots, no2));
        assertFalse(validator.canBeStoredInDepots(depots, no3));

    }

    @Test
    public void isLeaderCardActivable() {
        Player player = new Player("nick",0);
        LeaderCard resourcesOk,resourcesNo,developmentCardOk,developmentCardNo,developmentCardsOk,developmentCardsNo;
        Resources resources = new Resources(2,1,0,0);
        Resources resourcesBig = new Resources(2,1,1,0);
        DevelopmentCard devcard1,devcard2;
        devcard1 = new DevelopmentCard(null, Color.GREEN,1,null,false,0);
        devcard2 = new DevelopmentCard(null, Color.GREEN,2,null,false,0);
        player.getBoard().getDevelopmentCards().get(0).add(devcard1);
        player.getBoard().getDevelopmentCards().get(0).add(devcard2);
        player.getBoard().getStrongBox().add(resources);
        resourcesOk = new LeaderCard(LeaderCardsRequirements.RESOURCES,resources,null,null,0,null,null,null,0);
        resourcesOk.setRequirements();
        resourcesNo = new LeaderCard(LeaderCardsRequirements.RESOURCES,resourcesBig,null,null,0,null,null,null,0);
        resourcesNo.setRequirements();
        developmentCardOk = new LeaderCard(LeaderCardsRequirements.DEVELOPMENTCARD,null,null,Color.GREEN,2,null,null,null,0);
        developmentCardOk.setRequirements();
        developmentCardNo = new LeaderCard(LeaderCardsRequirements.DEVELOPMENTCARD,null,null,Color.GREEN,3,null,null,null,0);
        developmentCardNo.setRequirements();
        List<Color> colorOk = new ArrayList<>();
        colorOk.add(Color.GREEN);
        colorOk.add(Color.GREEN);
        List<Color> colorNo = new ArrayList<>();
        colorNo.add(Color.BLUE);
        developmentCardsOk = new LeaderCard(LeaderCardsRequirements.DEVELOPMENTCARDS,null,colorOk,null,0,null,null,null,0 );
        developmentCardsOk.setRequirements();
        developmentCardsNo = new LeaderCard(LeaderCardsRequirements.DEVELOPMENTCARDS,null,colorNo,null,0,null,null,null,0 );
        developmentCardsNo.setRequirements();


        assertTrue(validator.isLeaderCardActivable(player,resourcesOk));
        assertFalse(validator.isLeaderCardActivable(player,resourcesNo));
        assertTrue(validator.isLeaderCardActivable(player,developmentCardOk));
        assertFalse(validator.isLeaderCardActivable(player,developmentCardNo));
        assertTrue(validator.isLeaderCardActivable(player,developmentCardsOk));
        assertFalse(validator.isLeaderCardActivable(player,developmentCardsNo));


    }

    @Test
    public void isPositionSafeForMarketTray() {
        for (int i = 1; i < 8; i++) {
            assertTrue(validator.isPositionSafeForMarketTray(i));
        }
        assertFalse(validator.isPositionSafeForMarketTray(0));
        assertFalse(validator.isPositionSafeForMarketTray(-1));
        assertFalse(validator.isPositionSafeForMarketTray(8));
    }


    @Test
    public void isProductionActivatable() {
        Player player1 = new Player("nick1",0);
        Player player2 = new Player("nick2",1);
        Resources resources = new Resources(2,1,0,0);
        Resources resourcesBig = new Resources(2,1,1,0);
        player1.getBoard().getStrongBox().add(resources);
        player2.getBoard().getStrongBox().add(resourcesBig);
        assertTrue(validator.isProductionActivatable(player1,resources));
        assertFalse(validator.isProductionActivatable(player1,resourcesBig));
        assertTrue(validator.isProductionActivatable(player2,resources));
        assertTrue(validator.isProductionActivatable(player2,resourcesBig));
    }

    @Test
    public void isDevelopmentCardBuyable() {
        Player player = new Player("nick",0);
        Resources resourcesOwned = new Resources(0,1,1,1);
        player.getBoard().getStrongBox().add(resourcesOwned);
        Resources costOk1 = new Resources(0,1,1,1);
        Resources costOk2 = new Resources(0,1,1,0);
        Resources costNo1 = new Resources(1,0,0,0);
        Resources costNo2 = new Resources(0,0,0,2);
        DevelopmentCard devcard1,devcard2,devcard3,devcard4;
        devcard1 = new DevelopmentCard(costOk1, null,1,null,false,0);
        devcard2 = new DevelopmentCard(costOk2, null,2,null,false,0);
        devcard3 = new DevelopmentCard(costNo1, null,3,null,false,0);
        devcard4 = new DevelopmentCard(costNo2, null,1,null,false,0);
        assertTrue(validator.isDevelopmentCardBuyable(player,devcard1.getCost()));
        assertTrue(validator.isDevelopmentCardBuyable(player,devcard2.getCost()));
        assertFalse(validator.isDevelopmentCardBuyable(player,devcard3.getCost()));
        assertFalse(validator.isDevelopmentCardBuyable(player,devcard4.getCost()));
    }

    @Test
    public void isDevelopmentCardStorable() {
        Player player = new Player("nick",0);
        DevelopmentCard devcard1,devcard2,devcard3,devcard4,devcard5;
        devcard1 = new DevelopmentCard(null, null,1,null,false,0);
        devcard2 = new DevelopmentCard(null, null,2,null,false,0);
        devcard3 = new DevelopmentCard(null, null,3,null,false,0);
        devcard4 = new DevelopmentCard(null, null,2,null,false,0);
        devcard5 = new DevelopmentCard(null, null,1,null,false,0);

        player.getBoard().getDevelopmentCards().get(0).add(devcard1);
        player.getBoard().getDevelopmentCards().get(0).add(devcard2);
        player.getBoard().getDevelopmentCards().get(0).add(devcard3);
        assertFalse(validator.isDevelopmentCardStorable(player,devcard4));
        assertTrue(validator.isDevelopmentCardStorable(player,devcard5));

    }

    @Test
    public void isMiniSlotFree() {
        Player player = new Player("nick",0);
        DevelopmentCard devcard1,devcard2,devcard3,devcard4,devcard5;
        devcard1 = new DevelopmentCard(null, null,1,null,false,0);
        devcard2 = new DevelopmentCard(null, null,2,null,false,0);
        devcard3 = new DevelopmentCard(null, null,3,null,false,0);
        devcard4 = new DevelopmentCard(null, null,2,null,false,0);
        devcard5 = new DevelopmentCard(null, null,1,null,false,0);
        player.getBoard().getDevelopmentCards().get(0).add(devcard1);
        player.getBoard().getDevelopmentCards().get(0).add(devcard2);
        player.getBoard().getDevelopmentCards().get(0).add(devcard3);
        assertFalse(validator.isMiniSlotFree(player.getBoard().getDevelopmentCards().get(0),devcard4));
        assertFalse(validator.isMiniSlotFree(player.getBoard().getDevelopmentCards().get(0),devcard5));
        assertTrue(validator.isMiniSlotFree(player.getBoard().getDevelopmentCards().get(1),devcard5));
        assertFalse(validator.isMiniSlotFree(player.getBoard().getDevelopmentCards().get(1),devcard4));


    }




    @Test
    public void isLeaderCardDiscardable() {
        LeaderCard leader1 = new LeaderCard(null,null,null,null,0,null,null,null,false,0,null );
        LeaderCard leader2 = new LeaderCard(null,null,null,null,0,null,null,null,false,0, null);
        LeaderCard leader3 = new LeaderCard(null,null,null,null,0,null,null,null,true,0, null);
        assertTrue(validator.isLeaderCardDiscardable(leader1));
        assertTrue(validator.isLeaderCardDiscardable(leader2));
        assertFalse(validator.isLeaderCardDiscardable(leader3));
    }

    @Test
    public void isLeaderActiveDiscardInputSafe() {
        List <Integer> input1 = new ArrayList<>(){{add(1);}};
        List <Integer> input2 = new ArrayList<>(){{add(2);}};
        Player player1 = new Player("player1",2);
        Player player2 = new Player("player2", 1);
        LeaderCard leader = new LeaderCard(null, null, null, null, 0, null, null, null, false, 0, null);
        player1.getBoard().getLeaderCards().add(leader);
        assertTrue(validator.isLeaderActiveDiscardInputSafe(input1,player1));
        assertTrue(validator.isLeaderActiveDiscardInputSafe(input1,player2));
        assertFalse(validator.isLeaderActiveDiscardInputSafe(input2, player1));
        assertTrue(validator.isLeaderActiveDiscardInputSafe(input2, player2));
    }

    @Test
    public void isLeaderStartingDiscardInputSafe() {
        List <Integer> input1 = new ArrayList<>(){{
            add(1);
            add(1);
            }};
        List <Integer> input2 = new ArrayList<>(){{
            add(0);
            add(4);
            }};
        List <Integer> input3 = new ArrayList<>(){{
            add(0);
            add(1);
        }};

        assertFalse(validator.isLeaderStartingDiscardInputSafe(input1));
        assertFalse(validator.isLeaderStartingDiscardInputSafe(input2));
        assertTrue(validator.isLeaderStartingDiscardInputSafe(input3));
    }

    @Test
    public void isNumberOfWhitesToConvertSafe() {
        List <Color> threeWhite = new ArrayList<>(){{
            add(Color.WHITE);
            add(Color.WHITE);
            add(Color.WHITE);
        }};
        List <Color> twoWhite = new ArrayList<>(){{
            add(Color.WHITE);
            add(Color.WHITE);
        }};
        assertFalse(validator.isNumberOfWhitesToConvertSafe(4,threeWhite));
        assertFalse(validator.isNumberOfWhitesToConvertSafe(3,twoWhite));
        assertTrue(validator.isNumberOfWhitesToConvertSafe(3,threeWhite));
    }

    @Test
    public void isMatchEnded() {
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("a", 1);
        Player player2 = new Player("b", 2);
        Player player3 = new Player("c", 3);
        Player player4 = new Player("d", 4);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        assertFalse(validator.isMatchEnded(players));
        player4.getBoard().setFaithMarker(24);
        assertTrue(validator.isMatchEnded(players));

        player4.getBoard().setFaithMarker(0);
        Resources cardCost = new Resources();
        DevelopmentCard devcard1, devcard2, devcard3, devcard4, devcard5, devcard6, devcard7;
        devcard1 = new DevelopmentCard(cardCost, null, 1, null, false, 0);
        devcard2 = new DevelopmentCard(cardCost, null, 2, null, false, 0);
        devcard3 = new DevelopmentCard(cardCost, null, 3, null, false, 0);
        devcard4 = new DevelopmentCard(cardCost, null, 1, null, false, 0);
        devcard5 = new DevelopmentCard(cardCost, null, 2, null, false, 0);
        devcard6 = new DevelopmentCard(cardCost, null, 3, null, false, 0);
        devcard7 = new DevelopmentCard(cardCost, null, 1, null, false, 0);


        player1.getBoard().buyDevCard(devcard1, player1.getBoard().getDevelopmentCards().get(0));
        player1.getBoard().buyDevCard(devcard2, player1.getBoard().getDevelopmentCards().get(0));
        player1.getBoard().buyDevCard(devcard3, player1.getBoard().getDevelopmentCards().get(0));
        player1.getBoard().buyDevCard(devcard4, player1.getBoard().getDevelopmentCards().get(1));
        player1.getBoard().buyDevCard(devcard5, player1.getBoard().getDevelopmentCards().get(1));
        player1.getBoard().buyDevCard(devcard6, player1.getBoard().getDevelopmentCards().get(1));
        player1.getBoard().buyDevCard(devcard7, player1.getBoard().getDevelopmentCards().get(2));
        assertTrue(validator.isMatchEnded(player1));
        assertTrue(validator.isMatchEnded(players));
    }


    @Test
    public void isDevelopmentCardColorFinished() {
        DevCardsTray tray = new DevCardsTray();
        tray.buy(tray.select(0));
        tray.buy(tray.select(0));
        tray.buy(tray.select(0));
        tray.buy(tray.select(1));
        assertFalse(validator.isDevelopmentCardColorFinished(Color.GREEN,tray));
        tray.buy(tray.select(0));
        tray.buy(tray.select(1));
        tray.buy(tray.select(1));
        tray.buy(tray.select(1));
        tray.buy(tray.select(2));
        tray.buy(tray.select(2));
        tray.buy(tray.select(2));
        assertFalse(validator.isDevelopmentCardColorFinished(Color.GREEN, tray));
        tray.buy(tray.select(2));
        List<DevelopmentCard> list = tray.getColor(Color.GREEN);
        assertTrue(validator.isDevelopmentCardColorFinished(Color.GREEN, tray));
    }

    @Test
    public void isOneDevelopmentCardColorFinished() {
        DevCardsTray tray = new DevCardsTray();
        tray.buy(tray.select(0));
        tray.buy(tray.select(0));
        assertFalse(validator.isDevelopmentCardColorFinished(Color.GREEN,tray));
        tray.buy(tray.select(0));
        tray.buy(tray.select(0));
        tray.buy(tray.select(1));
        tray.buy(tray.select(1));
        tray.buy(tray.select(1));
        tray.buy(tray.select(1));
        tray.buy(tray.select(2));
        tray.buy(tray.select(2));
        tray.buy(tray.select(2));
        tray.buy(tray.select(2));
        assertTrue(validator.isDevelopmentCardColorFinished(Color.GREEN, tray));
    }

    @Test
    public void isNumberOfPlayerSafe() {
        int true1 = 1;
        int true2 = 4;
        int false1 = 5;
        int false2 = 0;
        assertTrue(validator.isNumberOfPlayerSafe(true1));
        assertTrue(validator.isNumberOfPlayerSafe(true2));
        assertFalse(validator.isNumberOfPlayerSafe(false1));
        assertFalse(validator.isNumberOfPlayerSafe(false2));
    }

    @Test
    public void isDiscardSafe() {
        Resources  disc1 = new Resources(0, 2,1, 0);
        Resources  disc2 = new Resources(0,1,0,1);
        Resources own1 = new Resources(0,1,1,0);
        Resources own2 = new Resources(1,1,1,1);
        assertFalse(validator.isDiscardSafe(own1.resourceToInteger(), disc1.resourceToInteger()));
        assertTrue(validator.isDiscardSafe(own2.resourceToInteger(), disc2.resourceToInteger()));
    }

    @Test
    public void isPositionSafeForDevCardsTray() {
        int positionOk = 1;
        int positionKO1, positionKO2;
        positionKO1 = -1;
        positionKO2 = 12;
        assertTrue(validator.isPositionSafeForDevCardsTray(positionOk));
        assertFalse(validator.isPositionSafeForDevCardsTray(positionKO1));
        assertFalse(validator.isPositionSafeForDevCardsTray(positionKO2));
    }

    @Test
    public void isSlotInputSafeForStoreDevCard() {
        int true1 = 1;
        int true2 = 3;
        int false1 = 4;
        int false2 = 0;
        assertTrue(validator.isSlotInputSafeForStoreDevCard(true1));
        assertTrue(validator.isSlotInputSafeForStoreDevCard(true2));
        assertFalse(validator.isSlotInputSafeForStoreDevCard(false1));
        assertFalse(validator.isSlotInputSafeForStoreDevCard(false2));
    }

    @Test
    public void isSlotSafe(){
        Player player = new Player("a", 1);
        Resources cardCost = new Resources();
        DevelopmentCard devcard1, devcard2, devcard3;
        devcard1 = new DevelopmentCard(cardCost, null, 1, null, false, 0);
        devcard2 = new DevelopmentCard(cardCost, null, 2, null, false, 0);
        devcard3 = new DevelopmentCard(cardCost, null, 3, null, false, 0);

        assertFalse(validator.isSlotSafe(player.getBoard().getDevelopmentCards().get(0).size(), devcard3.getLevel()));
        assertFalse(validator.isSlotSafe(player.getBoard().getDevelopmentCards().get(0).size(), devcard2.getLevel()));
        assertTrue(validator.isSlotSafe(player.getBoard().getDevelopmentCards().get(0).size(), devcard1.getLevel()));
    }



    @Test
    public void isSelectedInputValid() {
        List<Color> colors1 = new ArrayList<>(){{
            add(Color.WHITE);
            add(Color.GREY);
        }};
        List<Color> colors2 = new ArrayList<>(){{
            add(Color.BLUE);
            add(Color.PURPLE);
        }};
        List<Color> colors3 = new ArrayList<>(){{
            add(Color.BLUE);
            add(Color.GREY);
            add(Color.YELLOW);
        }};

        assertFalse(validator.isSelectedInputValid(colors1));
        assertFalse(validator.isSelectedInputValid(colors3));
        assertTrue(validator.isSelectedInputValid(colors2));

    }

    @Test
    public void isSelectedOutputValid() {
        List<Color> colors1 = new ArrayList<>(){{
            add(Color.RED);
        }};
        List<Color> colors2 = new ArrayList<>(){{
            add(Color.BLUE);
        }};
        List<Color> colors3 = new ArrayList<>(){{
            add(Color.BLUE);
            add(Color.GREY);
            add(Color.YELLOW);
        }};
        assertFalse(validator.isSelectedOutputValid(colors1));
        assertFalse(validator.isSelectedOutputValid(colors3));
        assertTrue(validator.isSelectedOutputValid(colors2));
    }

    @Test
    public void isSelectedProductionValid() {
        List<Integer> selected1 = new ArrayList<>(){{
            add(1);
            add(0);
            add(3);
            add(2);
        }};
        List<Integer> selected2 = new ArrayList<>(){{
            add(1);
            add(1);
            add(2);
            add(3);
        }};
        List<Integer> selected3 = new ArrayList<>(){{
            add(0);
            add(1);
            add(2);
            add(3);
            add(4);
        }};
        List<Integer> available = new ArrayList<>(){{
            add(0);
            add(1);
            add(2);
            add(3);
        }};

        assertTrue(validator.isSelectedProductionValid(selected1, available));
        assertFalse(validator.isSelectedProductionValid(selected2, available));
        assertFalse(validator.isSelectedProductionValid(selected3, available));

    }

    @Test
    public void isLeadersProductionValid() {
        List<Color> colors1 = new ArrayList<>(){{
           add(Color.BLUE);
           add(Color.PURPLE);
        }};
        List<Color> colors2 = new ArrayList<>(){{
           add(Color.PURPLE);
           add(Color.BLUE);
           add(Color.GREY);
        }};

        List<Integer> leader = new ArrayList<>(){{
            add(1);
            add(2);
        }};

        assertFalse(validator.isLeadersProductionValid(leader, colors2));
        assertTrue(validator.isLeadersProductionValid(leader, colors1));

    }

    @Test
    public void isVaticanReportTriggered() {
        boolean[] popeFavorTiles = {false, false, false};
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("a", 1);
        Player player2 = new Player("b", 2);
        players.add(player1);
        players.add(player2);
        player1.getBoard().setFaithMarker(8);
        player2.getBoard().setFaithMarker(7);
        assertTrue(validator.isVaticanReportTriggered(players, popeFavorTiles));

        popeFavorTiles[0] = true;
        player1.getBoard().setFaithMarker(12);
        player2.getBoard().setFaithMarker(13);
        assertFalse(validator.isVaticanReportTriggered(players, popeFavorTiles));

        popeFavorTiles[1] = true;
        player1.getBoard().setFaithMarker(23);
        player2.getBoard().setFaithMarker(24);
        assertTrue(validator.isVaticanReportTriggered(players, popeFavorTiles));
    }

    @Test
    public void isVaticanReportTriggeredSolo() {
        boolean[] popeFavorTiles = {false, false, false};
        SoloOpponent lorenzo = new SoloOpponent(null);
        Player player1 = new Player("a", 1);
        player1.getBoard().setFaithMarker(7);
        lorenzo.increaseBlackCross(8);
        assertTrue(validator.isVaticanReportTriggeredSolo(player1, lorenzo, popeFavorTiles));

        popeFavorTiles[0] = true;
        player1.getBoard().setFaithMarker(16);
        lorenzo.increaseBlackCross(3);
        assertTrue(validator.isVaticanReportTriggeredSolo(player1, lorenzo, popeFavorTiles));

        popeFavorTiles[1] = true;
        player1.getBoard().setFaithMarker(18);
        lorenzo.increaseBlackCross(3);
        assertFalse(validator.isVaticanReportTriggeredSolo(player1, lorenzo, popeFavorTiles));
    }
}