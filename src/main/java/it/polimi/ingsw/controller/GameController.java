package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.MoreThanOneStrategy;
import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.SoloStrategy;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.reply.DialogReply;
import it.polimi.ingsw.network.message.request.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerMultiGame;
import it.polimi.ingsw.util.Observer;
import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;
import java.util.List;

public class GameController implements Observer {
    private final Game game;
    private MatchController matchController;
    private final ValidatorController validator;
    private VirtualView virtualView;
    private int maxPlayers;
    private int matchId;
    private boolean isGameInitialized;

    public GameController() {
        this.game = new Game();
        this.validator = new ValidatorController();
        isGameInitialized = false;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public MatchController getMatchController() {
        return matchController;
    }

    public void setMatchController(MatchController matchController) {
        this.matchController = matchController;
    }

    public void setVirtualView(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public boolean isGameInitialized() {
        return isGameInitialized;
    }

    public void initialize() {
        this.isGameInitialized = true;
        this.game.init(this.maxPlayers);
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    public Game getGame() {
        return game;
    }

    public ValidatorController getValidator() {
        return validator;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Player getCurrentSender(String nickname){
        for (Player player: game.getPlayers()) {
            if (player.getNickname().equals(nickname)) return player;
        }
        return matchController.getCurrentPlayer();
    }

    /**
     * Increases all player's faith by the given {@param amount} except for the player with the specified {@param nickname}
     */
    public void increaseAllOthersPlayersFaith(String nickname, int amount) {
        if(maxPlayers > 1) {
            for (Player player : game.getPlayers()) {
                if (!player.getNickname().equals(nickname)){
                    player.getBoard().increaseFaith(amount);
                    virtualView.setReceiver(player.getNickname());
                    virtualView.updateDialog("The player " + matchController.getCurrentPlayer().getNickname() + " discarded some Resources.\n You get " + amount + " faith point!");
                }
            }
            virtualView.setReceiver(nickname);
        }
        else game.getLorenzo().increaseBlackCross(amount);
    }

    /**
     * Adds to the possible Productions the ones given by the Leader Card's ability
     * @param selectedLeaders are the cards chosen to activate the production
     */
    private void setOutputInLeadersProduction(List<Integer> selectedLeaders) {
        if (selectedLeaders.contains(4) && selectedLeaders.contains(5)) {
            List<Integer> Leaders = new ArrayList<>() {{
                add(4);
                add(5);
            }};
            virtualView.askLeaderProductionOutput(Leaders);
        } else if (selectedLeaders.contains(4) && !selectedLeaders.contains(5)) {
            List<Integer> Leaders = new ArrayList<>() {{
                add(4);
            }};
            virtualView.askLeaderProductionOutput(Leaders);
        } else if (!selectedLeaders.contains(4) && selectedLeaders.contains(5)) {
            List<Integer> Leaders = new ArrayList<>() {{
                add(5);
            }};
            virtualView.askLeaderProductionOutput(Leaders);
        }
    }

    /**
     * Updates the situation of the game depending on the type of the received {@param message}
     */
    @Override
    public void update(Message message) {
        String nickname = message.getSender();
        virtualView.setReceiver(nickname);
        switch (message.getType()) {
            case INITIALIZE:
                InitializeGameRequest initializeGameRequest = (InitializeGameRequest) message;
                if (validator.isNumberOfPlayerSafe(initializeGameRequest.getMaxPlayersNumber())) {
                    setMaxPlayers(initializeGameRequest.getMaxPlayersNumber());
                    initialize();
                    Server.LOG.info("Initialize Game, number of player is set to " + this.maxPlayers);
                    if (initializeGameRequest.getMaxPlayersNumber() == 1){
                        virtualView.askIsYourTurn();
                        this.matchController = new MatchControllerSolo(validator, virtualView, game);
                        matchController.setMatchId(matchId);
                        matchController.setCurrentPlayer(game.getPlayers().get(0));
                        this.game.setGameStrategy(new SoloStrategy());
                        this.game.setGameState(GameState.IN_GAME);
                        game.startMatch();
                        matchController.wakeUp();
                    }
                    else{
                        virtualView.askWaitForOtherPlayers();
                    }

                } else {
                    virtualView.updateAlert("Invalid input!");
                    virtualView.askMaxPlayers();
                }
                break;

            case LOGIN://needed for the SoloApp
                game.addPlayers(new Player(message.getSender(), 0));
                break;

            case STARTINGRESOURCES:
                StartingResourcesRequest startingResourcesRequest = (StartingResourcesRequest) message;
                matchController.addStartingResources(startingResourcesRequest.getResourcesList());
                virtualView.askStartingDiscardLeaders(matchController.getCurrentPlayer().getBoard().getActiveLeadersFancyCli(), matchController.getCurrentPlayer().getBoard().getInactiveLeadersFancyCli());
                break;

            case STARTINGDISCARD:
                StartingDiscardLeaderRequest startingDiscardLeaderRequest = (StartingDiscardLeaderRequest) message;
                List<LeaderCard> selectedLeaders = new ArrayList<>();
                if (validator.isLeaderStartingDiscardInputSafe(startingDiscardLeaderRequest.getLeaders())){
                    selectedLeaders.add(matchController.getCurrentPlayer().getBoard().getLeaderCards().get(startingDiscardLeaderRequest.getLeaders().get(0)));
                    selectedLeaders.add(matchController.getCurrentPlayer().getBoard().getLeaderCards().get(startingDiscardLeaderRequest.getLeaders().get(1)));
                    matchController.getCurrentPlayer().getBoard().getLeaderCards().remove(selectedLeaders.get(0));
                    matchController.getCurrentPlayer().getBoard().getLeaderCards().remove(selectedLeaders.get(1));
                    matchController.useOrDiscardLeader();
                }else {
                    virtualView.updateDialog("Can't discard selected leaders, retry");
                    virtualView.askStartingDiscardLeaders(matchController.getCurrentPlayer().getBoard().getActiveLeadersFancyCli(),matchController.getCurrentPlayer().getBoard().getInactiveLeadersFancyCli());
                }
                break;

            case ACTIVELEADERS:
                ActivateLeadersRequest activateLeadersRequest = (ActivateLeadersRequest) message;
                if (validator.isLeaderActiveDiscardInputSafe(activateLeadersRequest.getLeaders(), matchController.getCurrentPlayer())) {
                    matchController.activeLeadersAction(activateLeadersRequest.getLeaders());
                } else {
                    virtualView.updateDialog("Can't active selected leaders, retry");
                    virtualView.askToDiscardLeaders(matchController.getCurrentPlayer().getBoard().getActiveLeadersFancyCli(),matchController.getCurrentPlayer().getBoard().getInactiveLeadersFancyCli());
                }
                break;

            case DISCARDLEADERS:
                DiscardLeadersRequest discardLeadersRequest = (DiscardLeadersRequest) message;
                if (validator.isLeaderActiveDiscardInputSafe(discardLeadersRequest.getLeaders(), matchController.getCurrentPlayer())) {
                    matchController.discardLeaderAction(discardLeadersRequest.getLeaders());
                } else {
                    virtualView.updateDialog("Can't discard selected leaders, retry");
                    virtualView.askToDiscardLeaders(matchController.getCurrentPlayer().getBoard().getActiveLeadersFancyCli(),matchController.getCurrentPlayer().getBoard().getInactiveLeadersFancyCli());
                }
                break;

            case TURNACTION:
                TurnActionRequest turnActionRequest = (TurnActionRequest) message;
                matchController.performTurnAction(turnActionRequest.getAction());
                break;

            case MARKETPOSITION:
                MarketPositionRequest marketPositionRequest = (MarketPositionRequest) message;
                if (validator.isPositionSafeForMarketTray(marketPositionRequest.getPosition())) {
                    matchController.takeResourceLeaders(marketPositionRequest.getPosition());
                } else {
                    virtualView.updateDialog("Invalid position! Retry with a new input");
                    virtualView.askPositionForMarket(game.getMarketTray().getRows(), game.getMarketTray().getSlideMarble());
                }
                break;

            case MARKETLEADERS:
                MarketLeadersRequest marketLeadersRequest = (MarketLeadersRequest) message;
                int numberToConvert = marketLeadersRequest.getNumberToConvert().get(0) + marketLeadersRequest.getNumberToConvert().get(1);
                if (validator.isNumberOfWhitesToConvertSafe(numberToConvert, marketLeadersRequest.getColors())) {
                    matchController.takeResourceAction(marketLeadersRequest.getColors(), marketLeadersRequest.getNumberToConvert().get(0), marketLeadersRequest.getNumberToConvert().get(1));
                }else{
                    List<Color> leadersColor = new ArrayList<>() {{
                        add(matchController.getCurrentPlayer().getBoard().getLeaderCards().get(0).getSpecialAbilityColor());
                        add(matchController.getCurrentPlayer().getBoard().getLeaderCards().get(1).getSpecialAbilityColor());
                    }};
                    virtualView.updateDialog("Invalid input! Retry");
                    virtualView.askNumberOfWhiteToConvert(leadersColor, marketLeadersRequest.getColors());
                }
                break;

            case MARKETDEPOTS:
                MarketDepotsRequest marketDepotsRequest = (MarketDepotsRequest) message;
                if (validator.isDiscardSafe(marketDepotsRequest.getTotal(), marketDepotsRequest.getDiscard())) {
                    Resources totalResources = new Resources(marketDepotsRequest.getTotal().get(0), marketDepotsRequest.getTotal().get(1), marketDepotsRequest.getTotal().get(2), marketDepotsRequest.getTotal().get(3));
                    Resources discardResources = new Resources(marketDepotsRequest.getDiscard().get(0), marketDepotsRequest.getDiscard().get(1), marketDepotsRequest.getDiscard().get(2), marketDepotsRequest.getDiscard().get(3));
                    totalResources.subtract(discardResources);
                    if (validator.canBeStoredInDepots(matchController.getCurrentPlayer().getBoard().getWarehouseDepots(), totalResources)) {
                        matchController.getCurrentPlayer().getBoard().getWarehouseDepots().storeResources(totalResources);
                        increaseAllOthersPlayersFaith(marketDepotsRequest.getSender(), discardResources.getAmount());
                        matchController.useOrDiscardLeader();
                    } else {
                        virtualView.updateDialog("Invalid discard retry!");
                        virtualView.askResourcesToDiscard(marketDepotsRequest.getTotal());
                    }

                } else {
                    virtualView.updateDialog("Invalid discard Input retry!");
                    virtualView.askResourcesToDiscard(marketDepotsRequest.getTotal());
                }
                break;

            case BUYDEVTRAYPOSITION:
                BuyDevInTrayPositionRequest buyDevInTrayPositionRequest = (BuyDevInTrayPositionRequest) message;
                if (validator.isPositionSafeForDevCardsTray(buyDevInTrayPositionRequest.getPosition())) {
                    matchController.buyOneDevCardSlotCall(buyDevInTrayPositionRequest.getPosition());
                }
                 else {
                    virtualView.updateDialog("Invalid position number, retry!");
                    virtualView.askDevelopmentCardInTray(game.getDevCardsTray().getFreeDevCardsInfo());
                }
                break;

            case BUYDEVSLOTPOSITION:
                BuyDevSlotPositionRequest buyDevSlotPositionRequest = (BuyDevSlotPositionRequest) message;
                if (validator.isSlotInputSafeForStoreDevCard(buyDevSlotPositionRequest.getSlotPosition()) && validator.isSlotSafe(matchController.getCurrentPlayer().getBoard().getDevelopmentCards().get(buyDevSlotPositionRequest.getSlotPosition()-1).size(),game.getDevCardsTray().select(matchController.getSelectedDevCardTrayPosition()).getLevel())) {
                        matchController.buyOneDevCardAction(buyDevSlotPositionRequest.getSlotPosition());

                } else {
                    virtualView.updateDialog("Invalid slot number, retry!");
                    virtualView.askDevelopmentCardSlot();
                }
                break;

            case ACTIVATEPRODUCTION:
                ActivateProductionRequest activateProductionRequest = (ActivateProductionRequest) message;
                if (validator.isSelectedProductionValid(activateProductionRequest.getProductionPosition(), activateProductionRequest.getPossiblePosition())) {
                    matchController.getSelectedProduction().addAll(activateProductionRequest.getProductionPosition());
                    if (activateProductionRequest.getProductionPosition().contains(0)) {
                        if (matchController.getCurrentPlayer().getBoard().getTotalResources().getAmount() < 2) {
                            virtualView.updateDialog("Can't take default resource, insufficient resources!");
                            virtualView.askProductionToActivate(activateProductionRequest.getPossiblePosition(), matchController.getCurrentPlayer().getBoard().getActiveLeadersFancyCli(), matchController.getCurrentPlayer().getBoard().getTopLevelDevCards());
                        } else virtualView.askDefaultProductionInput();
                    } else if (activateProductionRequest.getProductionPosition().contains(4) || activateProductionRequest.getProductionPosition().contains(5)) {
                        setOutputInLeadersProduction(activateProductionRequest.getProductionPosition());
                    } else matchController.activateProductionAction();
                } else virtualView.askProductionToActivate(activateProductionRequest.getPossiblePosition(), matchController.getCurrentPlayer().getBoard().getActiveLeadersFancyCli(), matchController.getCurrentPlayer().getBoard().getTopLevelDevCards());
                break;

            case LEADERPRODUCTION:
                LeaderProductionRequest leaderProductionRequest = (LeaderProductionRequest) message;
                if (validator.isLeadersProductionValid(leaderProductionRequest.getSelectedLeaders(), leaderProductionRequest.getColors())) {
                    if (leaderProductionRequest.getSelectedLeaders().contains(4)) {
                        Server.LOG.info(matchController.getCurrentPlayer().getNickname());
                        Server.LOG.info(matchController.getCurrentPlayer().getBoard().getLeaderCards().get(0).toString());
                        matchController.getCurrentPlayer().getBoard().getLeaderCards().get(0).setSpecialAbilityProductionPower(leaderProductionRequest.getColors().get(0));
                        if (leaderProductionRequest.getSelectedLeaders().contains(5))
                            matchController.getCurrentPlayer().getBoard().getLeaderCards().get(1).setSpecialAbilityProductionPower(leaderProductionRequest.getColors().get(1));
                    } else matchController.getCurrentPlayer().getBoard().getLeaderCards().get(1).setSpecialAbilityProductionPower(leaderProductionRequest.getColors().get(0));
                    matchController.activateProductionAction();
                } else
                    virtualView.askLeaderProductionOutput(leaderProductionRequest.getSelectedLeaders());
                break;

            case DEFAULTPRODUCTIONINPUT:
                DefaultProductionInputRequest defaultProductionInputRequest = (DefaultProductionInputRequest) message;
                if (validator.isSelectedInputValid(defaultProductionInputRequest.getColors())) {
                    if(matchController.getSelectedProduction().contains(0)){
                        matchController.getCurrentPlayer().getBoard().setBasicProductionPowerInput(defaultProductionInputRequest.getColors());
                    }
                    virtualView.askDefaultProductionOutput();
                } else virtualView.askDefaultProductionInput();
                break;

            case DEFAULTPRODUCTIONOUTPUT:
                DefaultProductionOutputRequest defaultProductionOutputRequest = (DefaultProductionOutputRequest) message;
                if (validator.isSelectedOutputValid(defaultProductionOutputRequest.getColors())) {

                    if(matchController.getSelectedProduction().contains(0)){
                        matchController.getCurrentPlayer().getBoard().setBasicProductionPowerOutput(defaultProductionOutputRequest.getColors());
                    }
                    if (matchController.getSelectedProduction().contains(4) || matchController.getSelectedProduction().contains(5)) {
                        setOutputInLeadersProduction(matchController.getSelectedProduction());
                    }
                    else matchController.activateProductionAction();
                } else virtualView.askDefaultProductionOutput();
                break;

            case SHOWFAITHTRACK:
                List<Boolean> popeFavors = new ArrayList<>();
                for (boolean x:getCurrentSender(message.getSender()).getBoard().getPopeFavorTiles()) {
                    if (x) popeFavors.add(Boolean.TRUE);
                    else popeFavors.add(Boolean.FALSE);
                }
                virtualView.showFaithTrack(getCurrentSender(message.getSender()).getBoard().getFaithMarker(),popeFavors);
                break;


            case SHOWLEADERS:
                virtualView.showLeaders(getCurrentSender(message.getSender()).getBoard().getActiveLeadersFancyCli(),getCurrentSender(message.getSender()).getBoard().getInactiveLeadersFancyCli());
                break;

            case SHOWOWNEDRESOURCES:
                virtualView.showOwnedResources(getCurrentSender(message.getSender()).getBoard().getWarehouseDepots().depotsToLists(), getCurrentSender(message.getSender()).getBoard().getStrongBox().resourceToInteger());
                break;

            case SHOWMARKETTRAY:
                virtualView.showMarketTray(game.getMarketTray().getRows(),game.getMarketTray().getSlideMarble());
                break;

            case SHOWDEVCARDTRAY:
                virtualView.showDevCardTray(game.getDevCardsTray().getFreeDevCardsInfo());
                break;

            case SHOWDEVCARDBOARDSLOTS:
                virtualView.showDevCardBoardSlots(getCurrentSender(message.getSender()).getBoard().getTopLevelDevCards(),getCurrentSender(message.getSender()).getBoard().getSecondaryLevelDevCards(1),getCurrentSender(message.getSender()).getBoard().getSecondaryLevelDevCards(2));
                break;

            case SHOWLEADERSPRODUCTION:
                virtualView.showLeadersProduction(getCurrentSender(message.getSender()).getBoard().getActiveLeadersFancyCli());
                break;

            case SHOWOTHERPLAYER:
                List <String> nicknames = new ArrayList<>();
                for (Player player:getGame().getPlayers()) {
                    nicknames.add(player.getNickname());
                }
                nicknames.remove(message.getSender());
                virtualView.showOtherPlayer(nicknames);
                break;

            case SHOWOTHERSLEADERS:
                ShowOthersLeaderRequest showOthersLeaderRequest = (ShowOthersLeaderRequest) message;
                virtualView.showOtherPlayerLeaders(getCurrentSender(showOthersLeaderRequest.getWantedPlayer()).getBoard().getActiveLeadersFancyCli(),getCurrentSender(showOthersLeaderRequest.getWantedPlayer()).getBoard().getInactiveLeadersFancyCli());
                break;

            case SHOWOTHERDEVCARDBOARDSLOTS:
                ShowOtherDevCardBoardSlotsRequest showOtherDevCardBoardSlotsRequest = (ShowOtherDevCardBoardSlotsRequest) message;
                virtualView.showOtherDevCardBoardSlots(getCurrentSender(showOtherDevCardBoardSlotsRequest.getWantedPlayer()).getBoard().getTopLevelDevCards(),getCurrentSender(showOtherDevCardBoardSlotsRequest.getWantedPlayer()).getBoard().getSecondaryLevelDevCards(1),getCurrentSender(showOtherDevCardBoardSlotsRequest.getWantedPlayer()).getBoard().getSecondaryLevelDevCards(2));
                break;

            case SHOWOTHERFAITHTRACK:
                ShowOtherFaithTrackRequest showOtherFaithTrackRequest = (ShowOtherFaithTrackRequest) message;
                List<Boolean> otherPopeFavors = new ArrayList<>();
                for (boolean x:getCurrentSender(showOtherFaithTrackRequest.getWantedPlayer()).getBoard().getPopeFavorTiles()) {
                    if (x) otherPopeFavors.add(Boolean.TRUE);
                    else otherPopeFavors.add(Boolean.FALSE);
                }
                virtualView.showOtherFaithTrack(getCurrentSender(showOtherFaithTrackRequest.getWantedPlayer()).getBoard().getFaithMarker(),otherPopeFavors);
                break;

            case SHOWOTHEROWNEDRESOURCES:
                ShowOtherOwnedResourcesRequest showOtherOwnedResourcesRequest = (ShowOtherOwnedResourcesRequest) message;
                virtualView.showOtherOwnedResources(getCurrentSender(showOtherOwnedResourcesRequest.getWantedPlayer()).getBoard().getWarehouseDepots().depotsToLists(), getCurrentSender(showOtherOwnedResourcesRequest.getWantedPlayer()).getBoard().getStrongBox().resourceToInteger());
                break;
        }
    }
}
