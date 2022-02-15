package it.polimi.ingsw.view;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.reply.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.sender.Sender;
import it.polimi.ingsw.view.sender.SenderSocketServer;

import java.util.List;

public class VirtualView implements View {

    Server server;
    ClientHandler clientHandler;
    Sender sender;

    public VirtualView(Server server) {
        this.server = server;
        this.sender = new SenderSocketServer(server);
    }

    public VirtualView(Sender sender) {
        this.sender = sender;
    }

    public synchronized void setReceiver(String nickname) {
        if (this.server != null) {
            this.clientHandler = server.nicknameToClientHandler(nickname);
            SenderSocketServer senderSocketServer = (SenderSocketServer) sender;
            senderSocketServer.setClientHandler(clientHandler);
        }
    }

    public void broadcast(Message message, int matchId) {
        sender.broadcast(message, matchId);
    }

    @Override
    public void askNickname() {
        this.sender.send(new LoginReply(true));
    }

    @Override
    public void askMaxPlayers() {
        this.sender.send(new InitializeGameReply());
    }

    @Override
    public void askWaitingRoom() {
        Server.LOG.warning("Error, impossible statement");
    }

    @Override
    public void askWaitForOtherPlayers() {
        this.sender.send(new WaitForOtherPlayersReply());
    }

    @Override
    public void askWaitForYourTurn() {
        this.sender.send(new WaitForYourTurnReply());
    }

    @Override
    public void askIsYourTurn() {
        this.sender.send(new IsYourTurnReply());
    }

    @Override
    public void askEndGame() {
        this.sender.send(new EndGameReply());
    }

    @Override
    public void askToActiveLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        this.sender.send(new ActivateLeadersReply(activeLeaders,inactiveLeaders));
    }

    @Override
    public void askToDiscardLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        this.sender.send(new DiscardLeadersReply(activeLeaders,inactiveLeaders));
    }


    @Override
    public void askTurnAction() {
        this.sender.send(new TurnActionReply());
    }

    @Override
    public void askStartingResources(int resourceAmount) {
        this.sender.send(new StartingResourcesReply(resourceAmount));
    }


    @Override
    public void askPositionForMarket(List<List<Color>> rows, Color slideMarble) {
        this.sender.send(new MarketPositionReply(rows, slideMarble));
    }

    @Override
    public void askNumberOfWhiteToConvert(List<Color> leadersColor, List<Color> takenColor) {
        this.sender.send(new MarketLeadersReply(leadersColor, takenColor));
    }

    @Override
    public void askResourcesToDiscard(List<Integer> resources) {
        this.sender.send(new MarketDepotsReply(resources));
    }

    @Override
    public void askDevelopmentCardInTray(List<List<String>> tray) {
        this.sender.send(new BuyDevInTrayPositionReply(tray));
    }

    @Override
    public void askDevelopmentCardSlot() {
        this.sender.send(new BuyDevSlotPositionReply());
    }

    @Override
    public void askProductionToActivate(List<Integer> productionPosition, List<List<String>> activeLeaders, List<List<String>> devCards) {
        this.sender.send(new ActivateProductionReply(productionPosition, activeLeaders, devCards));
    }

    @Override
    public void askDefaultProductionInput() {
        this.sender.send(new DefaultProductionInputReply());
    }

    @Override
    public void askDefaultProductionOutput() {
        this.sender.send(new DefaultProductionOutputReply());
    }

    @Override
    public void askLeaderProductionOutput(List<Integer> selectedLeaders) {
        this.sender.send(new LeaderProductionReply(selectedLeaders));
    }

    @Override
    public void updateDialog(String text) {
        this.sender.send(new DialogReply(text, "Server"));
    }

    @Override
    public void updateAlert(String text) {
        this.sender.send(new AlertReply(text));
    }

    @Override
    public void showMarketTray(List<List<Color>> rows, Color slideMarble) {
        this.sender.send(new ShowMarketTrayReply(rows, slideMarble));
    }

    @Override
    public void showDevCardTray(List<List<String>> tray) {
        this.sender.send(new ShowDevCardTrayReply(tray));
    }

    @Override
    public void showLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        this.sender.send(new ShowLeadersReply(activeLeaders, inactiveLeaders));
    }

    @Override
    public void showFaithTrack(int faithMarkerPosition, List<Boolean> popeFavors) {
        this.sender.send(new ShowFaithTrackReply(faithMarkerPosition, popeFavors));
    }

    @Override
    public void showLorenzoFaithTrack(int blackCrossPosition, String actionToken) {
        this.sender.send(new ShowLorenzoFaithTrackReply(blackCrossPosition, actionToken));
    }

    @Override
    public void showDevCardBoardSlots(List<List<String>> topCards, List<String> middleCards, List<String> bottomCards) {
        this.sender.send(new ShowDevCardBoardSlotsReply(topCards, middleCards, bottomCards));
    }

    @Override
    public void showOwnedResources(List<List<Integer>> depots, List<Integer> strongBox) {
        this.sender.send(new ShowOwnedResourcesReply(depots, strongBox));
    }

    @Override
    public void showLeadersProduction(List<List<String>> activeLeaders) {
        this.sender.send(new ShowLeadersProductionReply(activeLeaders));
    }

    @Override
    public void showOtherPlayer(List<String> players) {
        this.sender.send(new ShowOtherPlayerReply(players));
    }

    @Override
    public void showOtherPlayerLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        this.sender.send(new ShowOtherLeadersReply(activeLeaders, inactiveLeaders));
    }

    @Override
    public void showOtherFaithTrack(int faithMarkerPosition, List<Boolean> popeFavors) {
        this.sender.send(new ShowOtherFaithTrackReply(faithMarkerPosition, popeFavors));
    }

    @Override
    public void showOtherDevCardBoardSlots(List<List<String>> topCards, List<String> middleCards, List<String> bottomCards) {
        this.sender.send(new ShowOtherDevCardBoardSlotsReply(topCards, middleCards, bottomCards));
    }

    @Override
    public void showOtherOwnedResources(List<List<Integer>> depots, List<Integer> strongBox) {
        this.sender.send(new ShowOtherOwnedResourcesReply(depots, strongBox));
    }

    @Override
    public void askStartingDiscardLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders){
        this.sender.send(new StartingDiscardLeaderReply(activeLeaders, inactiveLeaders));
    }

    @Override
    public void askVaticanReportActive(int vaticanReportNumber) {
        this.sender.send(new VaticanReportReply(vaticanReportNumber));
    }
}
