package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.reply.*;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.util.Observer;
import it.polimi.ingsw.view.View;

/**
 * Correctly routes server responses to the view.
 * To do this use some controller logic
 */
public class ReplyBroker implements Observer {
    private final View view;

    public ReplyBroker(View wantedView) {
        this.view = wantedView;
    }

    @Override
    public void update(Message message) {
        switch (message.getType()) {
            case LOGIN:
                LoginReply loginReply = (LoginReply) message;
                if (loginReply.isAllowed()) view.updateDialog("Wait for start the Game!");
                else view.askNickname();
                break;

            case INITIALIZE:
                view.askMaxPlayers();
                break;

            case ISYOURTURN:
                view.askIsYourTurn();
                break;

            case STARTINGRESOURCES:
                StartingResourcesReply startingResourcesReply = (StartingResourcesReply) message;
                view.askStartingResources(startingResourcesReply.getAmountResources());
                break;

            case ACTIVELEADERS:
                ActivateLeadersReply activeLeadersReply = (ActivateLeadersReply) message;
                view.askToActiveLeaders(activeLeadersReply.getActiveLeaders(), activeLeadersReply.getInactiveLeaders());
                break;

            case DISCARDLEADERS:
                DiscardLeadersReply discardLeadersReply = (DiscardLeadersReply) message;
                view.askToDiscardLeaders(discardLeadersReply.getActiveLeaders(),discardLeadersReply.getInactiveLeaders());
                break;

            case TURNACTION:
                view.askTurnAction();
                break;

            case MARKETPOSITION:
                MarketPositionReply marketPositionReply = (MarketPositionReply) message;
                view.askPositionForMarket(marketPositionReply.getRows(), marketPositionReply.getSlideMarble());
                break;

            case MARKETLEADERS:
                MarketLeadersReply marketLeadersReply = (MarketLeadersReply) message;
                view.askNumberOfWhiteToConvert(marketLeadersReply.getLeadersColor(), marketLeadersReply.getTakenColors());
                break;

            case MARKETDEPOTS:
                MarketDepotsReply marketDepotsReply = (MarketDepotsReply) message;
                view.askResourcesToDiscard(marketDepotsReply.getInputResources());
                break;

            case BUYDEVTRAYPOSITION:
                BuyDevInTrayPositionReply buyDevInTrayPositionReply = (BuyDevInTrayPositionReply) message;
                view.askDevelopmentCardInTray(buyDevInTrayPositionReply.getTray());
                break;

            case BUYDEVSLOTPOSITION:
                view.askDevelopmentCardSlot();
                break;

            case ACTIVATEPRODUCTION:
                ActivateProductionReply activateProductionReply = (ActivateProductionReply) message;
                view.askProductionToActivate(activateProductionReply.getProductionPosition(), activateProductionReply.getActiveLeaders(), activateProductionReply.getDevCards());
                break;

            case DEFAULTPRODUCTIONINPUT:
                view.askDefaultProductionInput();
                break;

            case DEFAULTPRODUCTIONOUTPUT:
                view.askDefaultProductionOutput();
                break;

            case LEADERPRODUCTION:
                LeaderProductionReply leaderProductionReply = (LeaderProductionReply) message;
                view.askLeaderProductionOutput(leaderProductionReply.getSelectedLeaders());
                break;


            case SHOWMARKETTRAY:
                ShowMarketTrayReply showMarketTrayReply = (ShowMarketTrayReply) message;
                view.showMarketTray(showMarketTrayReply.getRows(), showMarketTrayReply.getSlideMarble());
                break;

            case SHOWDEVCARDTRAY:
                ShowDevCardTrayReply showDevCardTrayReply = (ShowDevCardTrayReply) message;
                view.showDevCardTray(showDevCardTrayReply.getTray());
                break;

            case SHOWLEADERS:
                ShowLeadersReply showLeadersReply = (ShowLeadersReply) message;
                view.showLeaders(showLeadersReply.getActiveLeaders(), showLeadersReply.getInactiveLeaders());
                break;

            case SHOWFAITHTRACK:
                ShowFaithTrackReply showFaithTrackReply = (ShowFaithTrackReply) message;
                view.showFaithTrack(showFaithTrackReply.getFaithMarkerPosition(), showFaithTrackReply.getPopeFavor());
                break;

            case SHOWLORENZOFAITHTRACK:
                ShowLorenzoFaithTrackReply showLorenzoFaithTrackReply = (ShowLorenzoFaithTrackReply) message;
                view.showLorenzoFaithTrack(showLorenzoFaithTrackReply.getBlackCrossPosition(), showLorenzoFaithTrackReply.getActionToken());
                break;

            case SHOWDEVCARDBOARDSLOTS:
                ShowDevCardBoardSlotsReply showDevCardBoardSlotsReply = (ShowDevCardBoardSlotsReply) message;
                view.showDevCardBoardSlots(showDevCardBoardSlotsReply.getTopCards(), showDevCardBoardSlotsReply.getMiddleCards(), showDevCardBoardSlotsReply.getBottomCards());
                break;

            case SHOWOWNEDRESOURCES:
                ShowOwnedResourcesReply showOwnedResourcesReply = (ShowOwnedResourcesReply) message;
                view.showOwnedResources(showOwnedResourcesReply.getDepots(), showOwnedResourcesReply.getStrongBox());
                break;

            case SHOWLEADERSPRODUCTION:
                ShowLeadersProductionReply showLeadersProductionReply = (ShowLeadersProductionReply) message;
                view.showLeadersProduction(showLeadersProductionReply.getActiveLeaders());
                break;


            case DIALOG:
                DialogReply dialogReply = (DialogReply) message;
                view.updateDialog(dialogReply.getText());
                break;

            case STARTINGDISCARD:
                StartingDiscardLeaderReply startingDiscardLeaderReply = (StartingDiscardLeaderReply) message;
                view.askStartingDiscardLeaders(startingDiscardLeaderReply.getActiveLeaders(), startingDiscardLeaderReply.getInactiveLeaders());
                break;

            case ALERT:
                AlertReply alertReply = (AlertReply) message;
                view.updateAlert(alertReply.getText());
                break;

            case WAITPLAYERS:
                view.askWaitForOtherPlayers();
                break;

            case WAITTURN:
                view.askWaitForYourTurn();
                break;

            case VATICANREPORT:
                VaticanReportReply vaticanReportReply = (VaticanReportReply) message;
                view.askVaticanReportActive(vaticanReportReply.getVaticanReportNumber());
                break;

            case WAITINGROOM:
                view.askWaitingRoom();
                break;

            case SHOWOTHERPLAYER:
                ShowOtherPlayerReply showOtherPlayerReply = (ShowOtherPlayerReply) message;
                view.showOtherPlayer(showOtherPlayerReply.getPlayers());
                break;

            case SHOWOTHERSLEADERS:
                ShowOtherLeadersReply showOtherLeadersReply = (ShowOtherLeadersReply) message;
                view.showOtherPlayerLeaders(showOtherLeadersReply.getActiveLeaders(), showOtherLeadersReply.getInactiveLeaders());
                break;

            case SHOWOTHERFAITHTRACK:
                ShowOtherFaithTrackReply showOtherFaithTrackReply = (ShowOtherFaithTrackReply) message;
                view.showOtherFaithTrack(showOtherFaithTrackReply.getFaithMarkerPosition(), showOtherFaithTrackReply.getPopeFavor());
                break;

            case SHOWOTHERDEVCARDBOARDSLOTS:
                ShowOtherDevCardBoardSlotsReply showOtherDevCardBoardSlotsReply = (ShowOtherDevCardBoardSlotsReply) message;
                view.showOtherDevCardBoardSlots(showOtherDevCardBoardSlotsReply.getTopCards(), showOtherDevCardBoardSlotsReply.getMiddleCards(), showOtherDevCardBoardSlotsReply.getBottomCards());
                break;

            case SHOWOTHEROWNEDRESOURCES:
                ShowOtherOwnedResourcesReply showOtherOwnedResourcesReply = (ShowOtherOwnedResourcesReply) message;
                view.showOtherOwnedResources(showOtherOwnedResourcesReply.getDepots(), showOtherOwnedResourcesReply.getStrongBox());
                break;

            case ENDGAME:
                view.askEndGame();
                break;
        }
    }
}
