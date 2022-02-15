package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.ClientViewInitializer;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.gui.sceneController.*;
import it.polimi.ingsw.view.sender.Sender;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that implement Gui
 */
public class Gui implements View {
    private Sender sender;
    private ClientViewInitializer initializer;

    public ClientViewInitializer getInitializer() {
        return initializer;
    }

    public void setInitializer(ClientViewInitializer initializer) {
        this.initializer = initializer;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void askNickname() {
        Platform.runLater(() -> GuiController.layoutSwapperGreedy(GuiController.getMainStage().getScene(), "/fxml/loginScene.fxml"));
    }

    @Override
    public void askMaxPlayers() {
        Platform.runLater(() -> GuiController.layoutSwapperGreedy(GuiController.getMainStage().getScene(), "/fxml/maxPlayerScene.fxml"));
    }

    @Override
    public void askWaitingRoom() {
        Platform.runLater(() -> GuiController.layoutSwapperGreedy(GuiController.getMainStage().getScene(), "/fxml/waitingRoomScene.fxml"));
    }

    @Override
    public void askWaitForOtherPlayers() {
        Platform.runLater(() -> GuiController.layoutSwapperGreedy(GuiController.getMainStage().getScene(), "/fxml/waitOtherPlayerScene.fxml"));
    }

    @Override
    public void askWaitForYourTurn() {
        Platform.runLater(() -> {
            GuiController.updateBoard();
            if (GuiController.getActionStage() != null) {
                GuiController.getActionStage().close();
            }
        });
    }

    @Override
    public void askIsYourTurn() {
        Platform.runLater(() -> GuiController.showDialog(GuiController.getMainStage(), "Is Your Turn!"));
    }

    @Override
    public void askEndGame() {
        Platform.runLater(() -> {
            GuiController.closeLorenzoStage();
            GuiController.closeActionStage();
            GuiController.layoutSwapperGreedy(GuiController.getMainStage().getScene(), "/fxml/endGameScene.fxml",600, 600);
        });
    }

    @Override
    public void askToActiveLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        ActivateLeadersController controller = new ActivateLeadersController();
        if (inactiveLeaders.size() > 0) {
            for (List<String> parameter : inactiveLeaders) {
                controller.setLeadersFace(parameter.get(7));
            }
            Platform.runLater(() -> {
                GuiController.setActionStage(GuiController.buildStage(GuiController.getActionStage()));
                GuiController.showModularWindows(controller, GuiController.getActionStage(), "/fxml/activateLeadersScene.fxml", 600, 500);
            });
        }
    }

    @Override
    public void askToDiscardLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        DiscardLeadersController controller = new DiscardLeadersController();
        if (inactiveLeaders.size() > 0) {
            for (List<String> parameter : inactiveLeaders) {
                controller.setLeadersFace(parameter.get(7));
            }
            Platform.runLater(() -> {
                GuiController.setActionStage(GuiController.buildStage(GuiController.getActionStage()));
                GuiController.showModularWindows(controller, GuiController.getActionStage(), "/fxml/discardLeadersScene.fxml", 600, 500);
            });
        }

    }

    @Override
    public void askTurnAction() {
        Platform.runLater(GuiController::updateBoard);

        Platform.runLater(() -> {
            GuiController.setActionStage(GuiController.buildStage(GuiController.getActionStage()));//refresh the board
            GuiController.showModularWindows(GuiController.getActionStage(), "/fxml/turnActionScene.fxml", 600, 600);
        });
    }

    @Override
    public void askStartingResources(int resourceAmount) {
        StartingResourcesController controller = new StartingResourcesController();
        controller.setAmount(resourceAmount);
        Platform.runLater(() -> GuiController.layoutSwapper(controller, GuiController.getMainStage().getScene(), "/fxml/startingResourcesScene.fxml", 600, 600));
    }

    @Override
    public void askStartingDiscardLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        StartingDiscardLeaderController controller = new StartingDiscardLeaderController();
        if (activeLeaders.size() > 0) {
            for (List<String> parameter : activeLeaders) {
                controller.setLeadersFace(parameter.get(7));
            }
        }
        if (inactiveLeaders.size() > 0) {
            for (List<String> parameter : inactiveLeaders) {
                controller.setLeadersFace(parameter.get(7));
            }
        }
        Platform.runLater(() -> GuiController.layoutSwapper(controller, GuiController.getMainStage().getScene(), "/fxml/startingDiscardLeaderScene.fxml", 900, 500));

    }

    @Override
    public void askVaticanReportActive(int vaticanReportNumber) {
        int finalVaticanReportNumber = vaticanReportNumber + 1;
        Platform.runLater(() -> GuiController.showDialog(GuiController.getMainStage(), "Vatican Report number " + finalVaticanReportNumber + " has been activated!"));
    }

    @Override
    public void askPositionForMarket(List<List<Color>> rows, Color slideMarble) {
        TakeResourcesController controller = new TakeResourcesController();
        for (List<Color> colors : rows) {
            for (Color color : colors) {
                controller.setMarbleImage(color);
            }
        }
        controller.setMarbleImage(slideMarble);
        Platform.runLater(() -> GuiController.layoutSwapper(controller, GuiController.getActionStage().getScene(), "/fxml/takeResourcesScene.fxml", 528, 680));
    }

    @Override
    public void askNumberOfWhiteToConvert(List<Color> leadersColor, List<Color> takenColor) {
        NumberOfWhiteToConvertController controller = new NumberOfWhiteToConvertController();
        controller.setLeadersColor(leadersColor);
        controller.setTakenColor(takenColor);
        Platform.runLater(() -> GuiController.layoutSwapper(controller, GuiController.getActionStage().getScene(), "/fxml/numberOfWhiteToConvertScene.fxml", 600, 400));

    }

    @Override
    public void askResourcesToDiscard(List<Integer> resources) {
        ResourcesToDiscardController controller = new ResourcesToDiscardController();
        controller.setResourcesAmount(resources);
        Platform.runLater(() -> GuiController.layoutSwapper(controller, GuiController.getActionStage().getScene(), "/fxml/resourcesToDiscardScene.fxml", 700, 350));
    }

    @Override
    public void askDevelopmentCardInTray(List<List<String>> tray) {
        BuyDevCardController controller = new BuyDevCardController();
        for (String parameter : tray.get(7)) {
            controller.setDevCardFace(parameter);
        }
        Platform.runLater(() -> GuiController.layoutSwapper(controller, GuiController.getActionStage().getScene(), "/fxml/buyDevCardScene.fxml", 720, 780));
    }

    @Override
    public void askDevelopmentCardSlot() {
        Platform.runLater(() -> GuiController.layoutSwapper(GuiController.getActionStage().getScene(), "/fxml/devCardSlotScene.fxml", 600, 400));
    }

    @Override
    public void askProductionToActivate(List<Integer> productionPosition, List<List<String>> activeLeaders, List<List<String>> devCards) {
        ActivateProductionController controller = new ActivateProductionController();
        controller.setProductionPosition(productionPosition);
        if(productionPosition.contains(5) && !productionPosition.contains(4) && activeLeaders.size()==1) {
            List<String> emptyCard = new ArrayList<>(){{
                add("│                       │");
                add("│                       │");
                add("│                       │");
                add("│                       │");
                add("│                       │");
                add("│                       │");
                add("│                       │");
                add("│                       │");
            }};
            activeLeaders.add(0, emptyCard);
        }
        if (activeLeaders.size() > 0) {
            for (List<String> parameter : activeLeaders) {
                if (parameter.get(6).equals("true") && parameter.get(1).equals("│       MOREPOWER       │")) {
                    controller.setLeadersFace(parameter.get(7));
                }
                else {
                    controller.setLeadersFace("/images/cards/emptyCard.png");
                }
            }
        }

        for (String parameter : devCards.get(7)) {
            controller.setDevCardsFace(parameter);
        }
        Platform.runLater(() -> GuiController.layoutSwapper(controller, GuiController.getActionStage().getScene(), "/fxml/activateProductionScene.fxml", 900, 720));
    }

    @Override
    public void askDefaultProductionInput() {
        Platform.runLater(() -> GuiController.layoutSwapper(GuiController.getActionStage().getScene(), "/fxml/defaultProductionInputScene.fxml", 600, 500));
    }

    @Override
    public void askDefaultProductionOutput() {
        Platform.runLater(() -> GuiController.layoutSwapper(GuiController.getActionStage().getScene(), "/fxml/defaultProductionOutputScene.fxml", 600, 500));
    }

    @Override
    public void askLeaderProductionOutput(List<Integer> selectedLeaders) {
        LeaderProductionOutputController controller = new LeaderProductionOutputController();
        controller.setNumberOfLeaders(selectedLeaders);
        Platform.runLater(() -> GuiController.layoutSwapper(controller, GuiController.getActionStage().getScene(), "/fxml/leaderProductionOutputScene.fxml", 600, 500));
    }

    @Override
    public void updateDialog(String text) {
        Platform.runLater(() -> GuiController.showDialog(GuiController.getMainStage(), text));
    }

    @Override
    public void updateAlert(String text) {
        Platform.runLater(() -> GuiController.showAlert(GuiController.getMainStage(), text));
    }

    @Override
    public void showMarketTray(List<List<Color>> rows, Color slideMarble) {
        MarketTrayController controller = new MarketTrayController();
        for (List<Color> colors : rows) {
            for (Color color : colors) {
                controller.setMarbleImage(color);
            }
        }
        controller.setMarbleImage(slideMarble);
        Platform.runLater(() -> GuiController.layoutSwapper(controller, GuiController.getMainStage().getScene(), "/fxml/marketTrayScene.fxml", 528, 680));
    }

    @Override
    public void showDevCardTray(List<List<String>> tray) {
        DevCardTrayController controller = new DevCardTrayController();
        for (String parameter : tray.get(7)) {
            controller.setDevCardFace(parameter);
        }

        Platform.runLater(() -> GuiController.layoutSwapper(controller, GuiController.getMainStage().getScene(), "/fxml/devCardTrayScene.fxml", 720, 780));
    }

    @Override
    public void showLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {

        LeadersController controller = new LeadersController();
        if (activeLeaders.size() > 0) {
            for (List<String> parameter : activeLeaders) {
                controller.setLeadersFace(parameter.get(7));
            }
        }

        if (inactiveLeaders.size() > 0) {
            for (List<String> parameter : inactiveLeaders) {
                controller.setLeadersFace(parameter.get(7));
            }
        }
        Platform.runLater(() -> GuiController.layoutSwapper(controller, GuiController.getMainStage().getScene(), "/fxml/leadersScene.fxml", 600, 500));

    }

    @Override
    public void showFaithTrack(int faithMarkerPosition, List<Boolean> popeFavors) {

        Platform.runLater(() -> {
            GuiController.getBoardController().setFaith(faithMarkerPosition);
            GuiController.getBoardController().setVaticanReport(popeFavors);
        });
    }

    @Override
    public void showLorenzoFaithTrack(int blackCrossPosition, String actionToken) {
        ShowTokenController controller = new ShowTokenController();
        controller.setTokenImg(actionToken);
        controller.setFaithPosition(blackCrossPosition);

        Platform.runLater(() -> GuiController.updateLorenzo(controller));
    }

    @Override
    public void showDevCardBoardSlots(List<List<String>> topCards, List<String> middleCards, List<String> bottomCards) {
        Platform.runLater(() -> GuiController.getBoardController().setDevCardSlots(topCards, middleCards, bottomCards));
    }

    @Override
    public void showOwnedResources(List<List<Integer>> depots, List<Integer> strongBox) {
        Platform.runLater(() -> {
            GuiController.getBoardController().setDepotLayer1(depots.get(0));
            GuiController.getBoardController().setDepotLayer2(depots.get(1));
            GuiController.getBoardController().setDepotLayer3(depots.get(2));
            if (depots.size() == 4) {
                GuiController.getBoardController().setExtraDepotLabelVisibility(true);
                GuiController.getBoardController().setFirstExtraDepots(depots.get(3));
            } else if (depots.size() == 5) {
                GuiController.getBoardController().setExtraDepotLabelVisibility(true);
                GuiController.getBoardController().setFirstExtraDepots(depots.get(3));
                GuiController.getBoardController().setSecondExtraDepots(depots.get(4));
            }else{
                GuiController.getBoardController().setExtraDepotLabelVisibility(false);
            }
            GuiController.getBoardController().setStrongBox(strongBox);
        });

    }

    @Override
    public void showLeadersProduction(List<List<String>> activeLeaders) {
        Server.LOG.warning("Impossible State");
    }

    @Override
    public void showOtherPlayer(List<String> players) {
        OtherPlayersController controller = new OtherPlayersController();
        controller.setPlayers(players);
        Platform.runLater(() -> GuiController.layoutSwapper(controller, GuiController.getMainStage().getScene(), "/fxml/otherPlayersScene.fxml", 600, 400));
    }

    @Override
    public void showOtherPlayerLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        OtherLeadersController controller = new OtherLeadersController();
        if (activeLeaders.size() > 0) {
            for (List<String> parameter : activeLeaders) {
                controller.setLeadersFace(parameter.get(7));
            }
        }

        if (inactiveLeaders.size() > 0) {
            for (List<String> ignored : inactiveLeaders) {
                controller.setLeadersFace("/images/cards/leaders/leaderBack.png");
            }
        }
        Platform.runLater(() -> GuiController.layoutSwapper(controller, GuiController.getMainStage().getScene(), "/fxml/otherLeadersScene.fxml", 600, 500));

    }

    @Override
    public void showOtherFaithTrack(int faithMarkerPosition, List<Boolean> popeFavors) {
        Platform.runLater(() -> {
            GuiController.getOtherBoardController().setFaith(faithMarkerPosition);
            GuiController.getOtherBoardController().setVaticanReport(popeFavors);
        });
    }

    @Override
    public void showOtherDevCardBoardSlots(List<List<String>> topCards, List<String> middleCards, List<String> bottomCards) {
        Platform.runLater(() -> GuiController.getOtherBoardController().setDevCardSlots(topCards, middleCards, bottomCards));
    }

    @Override
    public void showOtherOwnedResources(List<List<Integer>> depots, List<Integer> strongBox) {
        Platform.runLater(() -> {
            GuiController.getOtherBoardController().setDepotLayer1(depots.get(0));
            GuiController.getOtherBoardController().setDepotLayer2(depots.get(1));
            GuiController.getOtherBoardController().setDepotLayer3(depots.get(2));
            if (depots.size() == 4) {
                GuiController.getOtherBoardController().setExtraDepotLabelVisibility(true);
                GuiController.getOtherBoardController().setFirstExtraDepots(depots.get(3));
            } else if (depots.size() == 5) {
                GuiController.getOtherBoardController().setExtraDepotLabelVisibility(true);
                GuiController.getOtherBoardController().setFirstExtraDepots(depots.get(3));
                GuiController.getOtherBoardController().setSecondExtraDepots(depots.get(4));
            }else{
                GuiController.getOtherBoardController().setExtraDepotLabelVisibility(false);
            }
            GuiController.getOtherBoardController().setStrongBox(strongBox);
        });
    }
}
