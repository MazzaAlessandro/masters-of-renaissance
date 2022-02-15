package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionPower;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.enumerations.SpecialAbility;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.reply.EndGameReply;
import it.polimi.ingsw.network.message.reply.VaticanReportReply;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class MatchController {
    private Player currentPlayer;
    private final ValidatorController validator;
    private final VirtualView virtualView;
    private final Game game;
    private PlayerState nextState;
    private int matchId;

    private final List<Integer> selectedProduction = new ArrayList<>();
    private int selectedDevCardTrayPosition;

    public MatchController(ValidatorController validator, VirtualView virtualView, Game game) {
        this.validator = validator;
        this.virtualView = virtualView;
        this.game = game;
    }

    public int getMatchId(){
        return  this.matchId;
    }

    public void setMatchId(int matchId){
        this.matchId = matchId;
    }

    public Game getGame() {
        return game;
    }

    public ValidatorController getValidator() {
        return validator;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    public void setCurrentPlayer(Player currPlayer) {
        this.currentPlayer = currPlayer;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public abstract void wakeUp();

    /**
     * Add {@param startingResources } to the player's strongbox
     */
    public void addStartingResources(List<Color> startingResources) {
        Server.LOG.info("Added Starting Resources");
        currentPlayer.getBoard().getWarehouseDepots().storeResources(currentPlayer.getBoard().takeResources(startingResources));
    }


    /**
     * Starts the useOrDiscardLeader session of the turn
     */
    public void useOrDiscardLeader() {
        currentPlayer.setFirstTurn(false);
        if (currentPlayer.getState().equals(PlayerState.ACTIVE)) nextState = PlayerState.PERFORM_ACTION;
        else nextState = PlayerState.WAIT;
        Server.LOG.info("Your next state is " + nextState);
        activeLeadersCall();
    }

    /**
     * Checks if currentPlayer has leaderCards activable, if true, puts them in a list
     */
    public void activeLeadersCall() {
        List<LeaderCard> ownedLeader = currentPlayer.getBoard().getLeaderCards();
        List<LeaderCard> activableLeaders = new ArrayList<>();

        for (LeaderCard card : ownedLeader) {
            if (!card.isActive() && validator.isLeaderCardActivable(currentPlayer, card))
                activableLeaders.add(card);
        }

        if (activableLeaders.size() > 0) {
            virtualView.askToActiveLeaders(getCurrentPlayer().getBoard().getActiveLeadersFancyCli(), getCurrentPlayer().getBoard().getInactiveLeadersFancyCli());
        } else discardLeadersCall();
    }

    /**
     * @param selectedLeadersInteger a list of integer which represent the owned leaderCards
     *
     * Checks if leaderCards selected are activable, if true sets the leaderCard(s) active for the rest of the game
     */
    public void activeLeadersAction(List<Integer> selectedLeadersInteger) {
        List<LeaderCard> activableLeaders = new ArrayList<>();
        boolean haveActivate = false;

        for (int i = 0; i < currentPlayer.getBoard().getLeaderCards().size(); i++) {
            if (! currentPlayer.getBoard().getLeaderCards().get(i).isActive())
                activableLeaders.add(currentPlayer.getBoard().getLeaderCards().get(i));
        }

        switch (selectedLeadersInteger.size()) {
            case 1:
                if (selectedLeadersInteger.get(0) == 0)
                    haveActivate = true;

                else if (selectedLeadersInteger.get(0) == 1 && activableLeaders.size() > 0 && validator.isLeaderCardActivable(currentPlayer, activableLeaders.get(0))) {
                    int index = currentPlayer.getBoard().getLeaderCards().indexOf( activableLeaders.get(0));
                    currentPlayer.getBoard().getLeaderCards().get(index).setActive(true);
                    currentPlayer.getBoard().activateLeaderCard(activableLeaders.get(0));
                    haveActivate = true;
                } else if (selectedLeadersInteger.get(0) == 2 && activableLeaders.size() == 2 && validator.isLeaderCardActivable(currentPlayer, activableLeaders.get(1))) {
                    int index = currentPlayer.getBoard().getLeaderCards().indexOf( activableLeaders.get(1));
                    currentPlayer.getBoard().getLeaderCards().get(index).setActive(true);
                    currentPlayer.getBoard().activateLeaderCard(activableLeaders.get(1));
                    haveActivate = true;
                }

                break;
            case 2:
                if (activableLeaders.size() == 2 && validator.isLeaderCardActivable(currentPlayer, activableLeaders.get(0)) && validator.isLeaderCardActivable(currentPlayer, activableLeaders.get(1))){

                    currentPlayer.getBoard().getLeaderCards().get(0).setActive(true);
                    currentPlayer.getBoard().activateLeaderCard(currentPlayer.getBoard().getLeaderCards().get(0));
                    currentPlayer.getBoard().getLeaderCards().get(1).setActive(true);
                    currentPlayer.getBoard().activateLeaderCard(currentPlayer.getBoard().getLeaderCards().get(1));
                    haveActivate = true;

                }
                break;
            default:
                break;
        }
        if (haveActivate){
            discardLeadersCall();
        }else {
            virtualView.updateAlert("Can't active selected leaders, retry");
            activeLeadersCall();
        }
    }

    /**
     * Creates a list of leaderCards which are discardable, then currentPlayer select which leaderCard(s) wants to discard
     * if currentPlayer doesn't want to discard any leaderCard he has to select the turnAction
     */
    public void discardLeadersCall() {
        List<LeaderCard> ownedLeader = currentPlayer.getBoard().getLeaderCards();
        List<LeaderCard> discardableLeaders = new ArrayList<>();

        for (LeaderCard card : ownedLeader) {
            if (!card.isActive()) discardableLeaders.add(card);
        }

        if (discardableLeaders.size() > 0) {
            virtualView.askToDiscardLeaders(getCurrentPlayer().getBoard().getActiveLeadersFancyCli(), getCurrentPlayer().getBoard().getInactiveLeadersFancyCli());
        } else if (nextState.equals(PlayerState.PERFORM_ACTION)) {
            Server.LOG.info(currentPlayer.getNickname() + " now performs an action");
            virtualView.askTurnAction();
        } else {
            Server.LOG.info("Turn has ended");
            nextTurn();
        }
    }

    /**
     * If currentPlayer types "1" he wants to discard the first leaderCard, if he types "1 2", he wants to discard both
     * while if currentPlayer types 2 he wants to discard the second one. This method discards leaderCards
     * currentPlayer have chosen to discard
     *
     * @param selectedLeadersInteger represents the list (1 2) of LeaderCards which currentPlayer typed to discard
     */
    public void discardLeaderAction(List<Integer> selectedLeadersInteger) {
        List<LeaderCard> discardableLeaders = new ArrayList<>();
        boolean havaDiscard = false;
        for (int i = 0; i < currentPlayer.getBoard().getLeaderCards().size(); i++) {
            if (validator.isLeaderCardDiscardable(currentPlayer.getBoard().getLeaderCards().get(i)))
                discardableLeaders.add(currentPlayer.getBoard().getLeaderCards().get(i));
        }
        switch (selectedLeadersInteger.size()) {
            case 1:
                if (selectedLeadersInteger.get(0) == 0) {
                    havaDiscard = true;
                } else if (selectedLeadersInteger.get(0) == 1 && discardableLeaders.size() > 0) {
                    currentPlayer.getBoard().getLeaderCards().remove(discardableLeaders.get(0));
                    currentPlayer.getBoard().increaseFaith(1);
                    havaDiscard = true;
                } else if (selectedLeadersInteger.get(0) == 2 && discardableLeaders.size() == 2) {
                    currentPlayer.getBoard().getLeaderCards().remove(discardableLeaders.get(1));
                    currentPlayer.getBoard().increaseFaith(1);
                    havaDiscard = true;
                }

                break;
            case 2:
                if (discardableLeaders.size() == 2) {
                    currentPlayer.getBoard().getLeaderCards().remove(discardableLeaders.get(0));
                    currentPlayer.getBoard().getLeaderCards().remove(discardableLeaders.get(1));
                    currentPlayer.getBoard().increaseFaith(2);
                    havaDiscard = true;
                }
                break;
            default:
                break;
        }
        if (havaDiscard) {

            if (nextState.equals(PlayerState.WAIT)) {
                Server.LOG.info("Turn has ended");
                nextTurn();
            } else {
                Server.LOG.info(currentPlayer.getNickname() + " now performs an action");
                virtualView.askTurnAction();
            }
        } else {
            virtualView.updateAlert("Can't discard selected leaders, retry");
            virtualView.askToDiscardLeaders(getCurrentPlayer().getBoard().getActiveLeadersFancyCli(), getCurrentPlayer().getBoard().getInactiveLeadersFancyCli());
        }
    }


    /**
     * Activates one of the main turn actions based on {@param state}
     * If {@param state} equals 1, it triggers the takeResources action
     * If {@param state} equals 2, it triggers the buyOneDevCard action
     * If {@param state} equals 3, it triggers the activateProduction action
     * If anything else is typed, it asks again for a valid choice
     */
    public void performTurnAction(int state) {
        switch (state) {
            case 1:
                currentPlayer.setState(PlayerState.TAKE_RESOURCES);
                Server.LOG.info(currentPlayer.getNickname() + " TAKE_RESOURCES");
                takeResourcesCall();
                break;
            case 2:
                currentPlayer.setState(PlayerState.BUY_ONE_DEV_CARD);
                Server.LOG.info(currentPlayer.getNickname() + " BUY_ONE_DEV_CARD");
                buyOneDevCardCall();
                break;
            case 3:
                currentPlayer.setState(PlayerState.ACTIVATE_PRODUCTION);
                Server.LOG.info(currentPlayer.getNickname() + " ACTIVATE_PRODUCTION");
                activateProductionCall();
                break;
            default:
                virtualView.updateAlert("Invalid choice");
                virtualView.askTurnAction();
                break;

        }
    }

    /**
     * Asks the player which position of the market he wants to buy
     */
    public void takeResourcesCall() {
        virtualView.askPositionForMarket(game.getMarketTray().getRows(), game.getMarketTray().getSlideMarble());
    }

    /**
     * Given the {@param position}, it builds a corresponding list of Color
     * If there white marbles are part of the chosen {@param position} and {@param currentPlayer} has Leader Cards with the WHITEMARBLE ability, it triggers the effect accordingly
     */
    public void takeResourceLeaders(int position) {
        List<Color> colorList = game.getMarketTray().takeMarble(position);
        int numberOfWhites = Collections.frequency(colorList, Color.WHITE);
        int numberOfLeaders = 0;

        if (numberOfWhites > 0) {
            for (LeaderCard card : currentPlayer.getBoard().getLeaderCards()) {
                if (card.getSpecialAbility().equals(SpecialAbility.WHITEMARBLE) & card.isActive()) numberOfLeaders++;
            }
            switch (numberOfLeaders) {
                case (1):
                    LeaderCard wantedCard = null;
                    for (LeaderCard card : currentPlayer.getBoard().getLeaderCards()) {
                        if (card.getSpecialAbility().equals(SpecialAbility.WHITEMARBLE) & card.isActive()) {
                            wantedCard = card;
                            break;
                        }
                    }
                    assert wantedCard != null;
                    Color leaderColor = wantedCard.getSpecialAbilityColor();
                    List<Color> newColorList = new ArrayList<>();
                    for (Color color : colorList) {
                        if (color == Color.WHITE) newColorList.add(leaderColor);
                        else newColorList.add(color);
                    }
                    takeResourceAction(newColorList, 0, 0);
                    break;

                case (2):
                    List<Color> leadersColor = new ArrayList<>() {{
                        add(currentPlayer.getBoard().getLeaderCards().get(0).getSpecialAbilityColor());
                        add(currentPlayer.getBoard().getLeaderCards().get(1).getSpecialAbilityColor());
                    }};
                    virtualView.askNumberOfWhiteToConvert(leadersColor, colorList);
                    break;

                default:
                    takeResourceAction(colorList, 0, 0);
                    break;
            }
        } else {
            takeResourceAction(colorList, 0, 0);
        }
    }

    /**
     * Converts the list of {@param colors} in the corresponding Resources set
     * {@param numberOfColor1} and {@param numberOfColor2} declare how many whites get changed by the Leader Cards' ability
     */
    public void takeResourceAction(List<Color> colors, int numberOfColor1, int numberOfColor2) {
        if (numberOfLeaderCardsOwnedByType(SpecialAbility.WHITEMARBLE) == 2) {
            Color leaderColor1 = currentPlayer.getBoard().getLeaderCards().get(0).getSpecialAbilityColor();
            Color leaderColor2 = currentPlayer.getBoard().getLeaderCards().get(1).getSpecialAbilityColor();
            for (Color color : colors) {
                if ((color == Color.WHITE) && (numberOfColor1 > 0)){
                    colors.set(colors.indexOf(color), leaderColor1);
                    numberOfColor1--;
                }
                else if ((color == Color.WHITE) && (numberOfColor2 > 0)){
                    colors.set(colors.indexOf(color), leaderColor2);
                    numberOfColor2--;
                }
            }
        }

        Resources resources = currentPlayer.getBoard().takeResources(colors);
        if (validator.canBeStoredInDepots(currentPlayer.getBoard().getWarehouseDepots(), resources)) {
            currentPlayer.getBoard().getWarehouseDepots().storeResources(resources);

            useOrDiscardLeader();
        } else {
            virtualView.askResourcesToDiscard(resources.resourceToInteger());
        }
    }

    public List<Integer> getSelectedProduction() {
        return selectedProduction;
    }

    public int getSelectedDevCardTrayPosition() {
        return selectedDevCardTrayPosition;
    }

    /**
     * Asks the player which Development Card he wants to buy from the Tray
     */
    public void buyOneDevCardCall() {
        //selectedDevCardTrayPosition = 0;
        virtualView.askDevelopmentCardInTray(game.getDevCardsTray().getFreeDevCardsInfo());
    }

    /**
     * @param color represent resources by their color
     * @return resource corresponding to the color
     */
    private Resources colorToResource(Color color) {
        Resources r;

        switch (color) {
            case YELLOW:
                r = new Resources(1, 0, 0, 0);
                break;
            case BLUE:
                r = new Resources(0, 0, 0, 1);
                break;
            case PURPLE:
                r = new Resources(0, 0, 1, 0);
                break;
            case GREY:
                r = new Resources(0, 1, 0, 0);
                break;
            default:
                r = new Resources(0, 0, 0, 0);
                break;
        }
        return r;
    }

    /**
     * Given the Development Card placed in {@param position}, checks if the players has enough resources to buy and a slot to place it on the Board.
     * If the player can get the card, it asks the slot to place the card
     */
    public void buyOneDevCardSlotCall(int position) {
        DevelopmentCard wantedCard = game.getDevCardsTray().select(position);
        Resources devCardCost = new Resources(wantedCard.getCost().getCoins(), wantedCard.getCost().getStones(), wantedCard.getCost().getServants(), wantedCard.getCost().getShields());
        for (LeaderCard card : currentPlayer.getBoard().getLeaderCards()) {
            if (card.isActive() && card.getSpecialAbility().equals(SpecialAbility.DISCOUNT))
                devCardCost.subtract(colorToResource(card.getSpecialAbilityColor()));
        }
        if (validator.isDevelopmentCardBuyable(currentPlayer, devCardCost)
                &&
                validator.isDevelopmentCardStorable(currentPlayer, wantedCard)) {
            selectedDevCardTrayPosition = position;
            virtualView.askDevelopmentCardSlot();
        } else {
            virtualView.updateAlert("Can't buy this Development Card, try another one");
            virtualView.askDevelopmentCardInTray(game.getDevCardsTray().getFreeDevCardsInfo());
        }
    }

    /**
     * Places the previously chosen Development Card in the slot {@param slotPosition}, spending the needed resources
     */
    public void buyOneDevCardAction(int slotPosition) {
        DevelopmentCard wanted = game.getDevCardsTray().select(this.selectedDevCardTrayPosition);
        game.getDevCardsTray().buy(wanted);
        currentPlayer.getBoard().buyDevCard(wanted, currentPlayer.getBoard().getDevelopmentCards().get(slotPosition - 1));
        useOrDiscardLeader();
    }

    /**
     * Asks the player which one of the owned productions he wants to activate
     */
    public void activateProductionCall() {
        this.selectedProduction.clear();
        //List<ProductionPower> productionPowers = new ArrayList<>();
        List<Integer> productionPosition = new ArrayList<>() {{
            add(0);
        }};
        int index = 1;
        for (List<DevelopmentCard> developmentCardsSlot : currentPlayer.getBoard().getDevelopmentCards()) {
            if (developmentCardsSlot.size() > 0) {
                //productionPowers.add(developmentCardsSlot.get(developmentCardsSlot.size() - 1).getProductionPower());
                productionPosition.add(index);
            }
            index++;
        }
        index = 4;
        for (LeaderCard leaderCard : currentPlayer.getBoard().getLeaderCards()) {
            if (leaderCard.getSpecialAbility().equals(SpecialAbility.MOREPOWER) && leaderCard.isActive()) {
                //productionPowers.add(leaderCard.getSpecialAbilityProductionPower());
                leaderCard.resetSpecialAbilityProductionPower();
                productionPosition.add(index);
            }
            index++;
        }
        virtualView.askProductionToActivate(productionPosition, getCurrentPlayer().getBoard().getActiveLeadersFancyCli(), getCurrentPlayer().getBoard().getTopLevelDevCards());
    }

    /**
     * Sums all the chosen production's costs and products, activating them all at the same time
     */
    public void activateProductionAction() {
        Resources cost = new Resources(0, 0, 0, 0);
        Resources production = new Resources(0, 0, 0, 0);
        for (ProductionPower productionPower : integerListToProductionPowerList(this.selectedProduction)) {
            cost.add(productionPower.getInputResources());
            production.add(productionPower.getOutputResources());
        }
        if (validator.isProductionActivatable(currentPlayer, cost)) {
            currentPlayer.getBoard().spendResources(cost);
            currentPlayer.getBoard().addToStrongbox(production);
            for(LeaderCard leaderCard : currentPlayer.getBoard().getLeaderCards()){
                if(leaderCard.getSpecialAbility()==SpecialAbility.MOREPOWER) leaderCard.resetSpecialAbilityProductionPower();
            }
            useOrDiscardLeader();
        } else activateProductionCall();
    }

    /**
     * @return a List of the chosen ProductionPowers based on the list of integers specified by {@param input}
     */
    private List<ProductionPower> integerListToProductionPowerList(List<Integer> input) {
        List<ProductionPower> productionPowers = new ArrayList<>();
        for (Integer i : input) {
            List<DevelopmentCard> slot0 = currentPlayer.getBoard().getDevelopmentCards().get(0);
            List<DevelopmentCard> slot1 = currentPlayer.getBoard().getDevelopmentCards().get(1);
            List<DevelopmentCard> slot2 = currentPlayer.getBoard().getDevelopmentCards().get(2);
            switch (i) {
                case (0):
                    productionPowers.add(currentPlayer.getBoard().getBasicProductionPower());
                    break;
                case (1):
                    productionPowers.add(slot0.get(slot0.size() - 1).getProductionPower());
                    break;
                case (2):
                    productionPowers.add(slot1.get(slot1.size() - 1).getProductionPower());
                    break;
                case (3):
                    productionPowers.add(slot2.get(slot2.size() - 1).getProductionPower());
                    break;
                case (4):
                    productionPowers.add(currentPlayer.getBoard().getLeaderCards().get(0).getSpecialAbilityProductionPower());
                    break;
                case (5):
                    productionPowers.add(currentPlayer.getBoard().getLeaderCards().get(1).getSpecialAbilityProductionPower());
                    break;
            }
        }
        return productionPowers;
    }


    /**
     * @param specialAbility represents specialAbility of leaderCard
     * @return number of leaderCards currentPlayer owns with the same special ability
     */
    private int numberOfLeaderCardsOwnedByType(SpecialAbility specialAbility) {
        int result = 0;
        for (LeaderCard card : currentPlayer.getBoard().getLeaderCards()) {
            if (card.getSpecialAbility().equals(specialAbility) && card.isActive()) result++;
        }
        return result;
    }


    /**
     * Triggers the Vatican Report phase for all players
     */
    public void vaticanReport() {
        Server.LOG.info("Vatican Report");
        int vaticanReportNumber = 0;
        if (!game.getVaticanReportTiles()[2] && !game.getVaticanReportTiles()[1] && game.getVaticanReportTiles()[0])
            vaticanReportNumber = 1;
        if (!game.getVaticanReportTiles()[2] && game.getVaticanReportTiles()[1] && game.getVaticanReportTiles()[0])
            vaticanReportNumber = 2;
        if (!game.getVaticanReportTiles()[vaticanReportNumber]) {
            game.setVaticanReportTileTrue(vaticanReportNumber);
            for (Player player : game.getPlayers()) {
                player.getBoard().setPopeFavorTiles(vaticanReportNumber);
            }
            //virtualView.askVaticanReportActive(vaticanReportNumber);
            virtualView.broadcast(new VaticanReportReply(vaticanReportNumber), matchId);
        }
    }


    public abstract void nextTurn();

    public void endGame(){
        virtualView.broadcast((new EndGameReply()), matchId);
    }
}
