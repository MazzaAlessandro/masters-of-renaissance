package it.polimi.ingsw.view;

import it.polimi.ingsw.model.enumerations.Color;

import java.util.List;

public interface View {

    /**
     * The method has the functionality of subscribe a new nickname to the server
     */
    void askNickname();

    /**
     * Set the maximum number of participants in this match
     */
    void askMaxPlayers();

    /**
     * Manage a player's access to the WaitingRoom
     */
    void askWaitingRoom();

    /**
     * Puts all players on hold until all participants arrive
     */
    void askWaitForOtherPlayers();

    /**
     * Puts player on hold until it's your turn
     */
    void askWaitForYourTurn();

    /**
     * Wake up the player of the current turn
     */
    void askIsYourTurn();

    /**
     * Puts player on endGame status and invites them to quit the game
     */
    void askEndGame();

    /**
     * Manages the activation of leaders
     *
     * @param activeLeaders   list of active leaders
     * @param inactiveLeaders list of inactive leaders
     */
    void askToActiveLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders);

    /**
     * Manages the leaders discarding
     *
     * @param activeLeaders   list of active leaders
     * @param inactiveLeaders list of inactive leaders
     */
    void askToDiscardLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders);

    /**
     * Manage the choice of the player's action
     */
    void askTurnAction();

    /**
     * Manage initial distribution of resources
     *
     * @param resourceAmount amount of resources that can be stored
     */
    void askStartingResources(int resourceAmount);

    /**
     * @param activeLeaders   list of active leaders, if call is safe it should be empty
     * @param inactiveLeaders list of inactive leaders, if call is safe it size must be 4
     */
    void askStartingDiscardLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders);

    /**
     * Informs of the activation of the Vatican Report number {@param vaticanReportNumber}
     */
    void askVaticanReportActive(int vaticanReportNumber);

    /**
     * Manages the action: Take Resources
     *
     * @param rows        market marbles color sorted by rows
     * @param slideMarble slideMarble color
     */
    void askPositionForMarket(List<List<Color>> rows, Color slideMarble);

    /**
     * Called when there are two leader cards with the power to replace the white marble
     *
     * @param leadersColor colors of the two leader cards with the power to replace the white marble
     * @param takenColor   colors Taken in the market
     */
    void askNumberOfWhiteToConvert(List<Color> leadersColor, List<Color> takenColor);

    /**
     * Called when there is not enough space in the deposits, manages the discarding of resources
     *
     * @param resources resources taken in the market
     */
    void askResourcesToDiscard(List<Integer> resources);

    /**
     * * Manages the action: Buy One Development Card
     *
     * @param tray development cards Tray
     */
    void askDevelopmentCardInTray(List<List<String>> tray);

    /**
     * Places purchased development card in a slot
     */
    void askDevelopmentCardSlot();

    /**
     * Manages the action: Active the Production
     *
     * @param productionPosition list of selected production order by position
     * @param activeLeaders      list of activated leaders with special ability MORE POWER
     * @param devCards           list of development card at the top of each slot
     */
    void askProductionToActivate(List<Integer> productionPosition, List<List<String>> activeLeaders, List<List<String>> devCards);

    /**
     * Set the default production input value
     */
    void askDefaultProductionInput();

    /**
     * Set the default production output value
     */
    void askDefaultProductionOutput();

    /**
     * Set the leader production output value
     *
     * @param selectedLeaders list of selected leader
     */
    void askLeaderProductionOutput(List<Integer> selectedLeaders);

    /**
     * Notifies the player with a normal message, the message body is {@param text}
     */
    void updateDialog(String text);

    /**
     * Notifies the player with a Alert message, the message body is {@param text}
     */
    void updateAlert(String text);

    /**
     * Shows the market to the player
     * @param rows is a representation of the market as a group of colors
     * @param slideMarble is the color of the little marble outside the market
     */
    void showMarketTray(List<List<Color>> rows, Color slideMarble);

    /**
     * Shows the whole tray of Development Cards to the player
     * @param tray is a representation of the tray as a group oh strings holding all the information on the corresponding card
     */
    void showDevCardTray(List<List<String>> tray);

    /**
     * Shows to the player his leader cards, divided between {@param activeLeaders} and {@param inactiveLeaders}
     */
    void showLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders);

    /**
     * Shows to the player his Faith Track, displaying both his Faith Marker set at {@param faithMarkerPosition} and which {@param popeFavors} he activated
     */
    void showFaithTrack(int faithMarkerPosition, List<Boolean> popeFavors);

    /**
     * Shows to the player Lorenzo's actions at the end of a turn
     * @param blackCrossPosition is used to show his position on the Faith Track
     * @param actionToken is used to show which action Lorenzo did
     */
    void showLorenzoFaithTrack(int blackCrossPosition, String actionToken);

    /**
     * Shows to the player all the Development Cards he bought, hierarchical divided between {@param topCards}, {@param middleCards} and {@param bottomCards} to show them overlapped
     */
    void showDevCardBoardSlots(List<List<String>> topCards, List<String> middleCards, List<String> bottomCards);

    /**
     * Shows to the player all the Resources he stored either in the {@param depots } or the {@param strongBox}
     */
    void showOwnedResources(List<List<Integer>> depots, List<Integer> strongBox);

    /**
     * Shows to the player any Leader Card with the EXTRAPOWER ability included in {@param activeLeaders}
     */
    void showLeadersProduction(List<List<String>> activeLeaders);

    /**
     * Shows to the player another player's board
     * @param players is the list of all active players
     */
    void showOtherPlayer(List<String> players);

    /**
     * Shows to the player all the leader cards of another player, divided between {@param activeLeaders} and {@param inactiveLeaders}
     */
    void showOtherPlayerLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders);

    /**
     * Shows to the player another player's Faith Track, displaying both his Faith Marker set at {@param faithMarkerPosition} and which {@param popeFavors} he activated
     */
    void showOtherFaithTrack(int faithMarkerPosition, List<Boolean> popeFavors);

    /**
     * Shows to the player all the Development Cards owned by another player, hierarchical divided between {@param topCards}, {@param middleCards} and {@param bottomCards} to show them overlapped
     */
    void showOtherDevCardBoardSlots(List<List<String>> topCards, List<String> middleCards, List<String> bottomCards);

    /**
     * Shows to the player all the Resources another player stored either in the {@param depots } or the {@param strongBox}
     */
    void showOtherOwnedResources(List<List<Integer>> depots, List<Integer> strongBox);

}
