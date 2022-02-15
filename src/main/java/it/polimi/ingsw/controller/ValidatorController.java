package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.player.Depots;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.solo.SoloOpponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ValidatorController {

    /**
     * @param number represents number of Players
     * @return {@code true} if {@param number} is coherent with game rules, {@code false} otherwise.
     */
    public boolean isNumberOfPlayerSafe(int number) {
        return number <= 4 && number >= 1;
    }


    /**
     * @param input represents LeaderCards chosen by the Player
     * @return {@code true} if {@param input} is actually 2 {@code false} otherwise.
     */
    public boolean isLeaderStartingDiscardInputSafe(List<Integer> input){
        List<Integer> tmp = input.stream().distinct().collect(Collectors.toList());
        for (Integer i:tmp) {
            if(i<0 || i>3)
                return false;
        }
        return tmp.size()==2;
    }

    /**
     * @param card represents a LeaderCard chosen by the Player
     * @return {@code true} if {@param card} is already active, {@code false} otherwise.
     */
    public boolean isLeaderCardDiscardable(LeaderCard card) {
        return !card.isActive();
    }

    /**
     * @param player is the player who wants to active the {@param card}
     * @param card   is the selected LeaderCard
     * @return {@code true} if {@param player} can active the {@param card} {@code false} otherwise.
     */
    public boolean isLeaderCardActivable(Player player, LeaderCard card) {
        if (card.isActive()) return false;
        switch (card.getRequiresType()) {
            case RESOURCES:
                RequiresResources requiresResources = (RequiresResources) card.getRequirements();
                return player.getBoard().getTotalResources().biggerThan(requiresResources.getRequired());

            case DEVELOPMENTCARDS:
                RequiresColors requiresColors = (RequiresColors) card.getRequirements();
                int greenRequiresOccurrences = Collections.frequency(requiresColors.getRequires(), Color.GREEN);
                int yellowRequiresOccurrences = Collections.frequency(requiresColors.getRequires(), Color.YELLOW);
                int purpleRequiresOccurrences = Collections.frequency(requiresColors.getRequires(), Color.PURPLE);
                int blueRequiresOccurrences = Collections.frequency(requiresColors.getRequires(), Color.BLUE);
                ArrayList<Color> ownedDevCards = new ArrayList<>();
                for (int i = 1; i < 4; i++) {
                    ownedDevCards.addAll(player.getBoard().getColorsByLevel(i));
                }
                int greenOwnedOccurrences = Collections.frequency(ownedDevCards, Color.GREEN);
                int yellowOwnedOccurrences = Collections.frequency(ownedDevCards, Color.YELLOW);
                int purpleOwnedOccurrences = Collections.frequency(ownedDevCards, Color.PURPLE);
                int blueOwnedOccurrences = Collections.frequency(ownedDevCards, Color.BLUE);
                if (greenRequiresOccurrences > greenOwnedOccurrences) return false;
                else if (yellowRequiresOccurrences > yellowOwnedOccurrences) return false;
                else if (purpleRequiresOccurrences > purpleOwnedOccurrences) return false;
                else return blueRequiresOccurrences <= blueOwnedOccurrences;

            case DEVELOPMENTCARD:
                RequiresLevel requiresLevel = (RequiresLevel) card.getRequirements();
                List<Color> tmp = player.getBoard().getColorsByLevel(requiresLevel.getRequiredLevel());
                return tmp.contains(requiresLevel.getRequiredColor());

            default:
                return false;
        }
    }

    /**
     * @param input represents the list of LeaderCards chosen by the Player
     * @param currentPlayer represents currentPlayer
     * @return {@code true} if {@param currentPlayer} can active or discard cards in {@param input} {@code false} otherwise.
     */
    public boolean isLeaderActiveDiscardInputSafe (List<Integer> input, Player currentPlayer) {
        if (input.contains(2) && currentPlayer.getBoard().getLeaderCards().size()==1) return false;
        return input.size() > 0;
    }

    /**
     * @param selectedLeaders is the leaders selected by player
     * @param colors represent Production Power Input
     * @return {@code true} if Production Power Input selected is possible for the game rules
     */
    public boolean isLeadersProductionValid(List<Integer> selectedLeaders,List<Color> colors){
        if(selectedLeaders.size() != colors.size()){
            return false;
        }
        if(colors.contains(Color.WHITE)) return false;
        if (colors.size() == 2){
            List <Color> firstColor= new ArrayList<>(){{
                add(colors.get(0));
            }};
            List <Color> secondColor= new ArrayList<>(){{
                add(colors.get(1));
            }};
            return isSelectedOutputValid(firstColor) && isSelectedOutputValid(secondColor);
        }
        else if(colors.size() == 1){
            return isSelectedOutputValid(colors);
        }
        else return false;
    }


    /**
     * @param number is the value of all white marbles to be converted
     * @return {@code true} if {@param number} is safe {@code false} otherwise.
     */
    public boolean isNumberOfWhitesToConvertSafe(int number, List<Color> colors) {
        if (number < 0 || number > 4) return false;
        int numberOfWhite = Collections.frequency(colors,Color.WHITE);
        return number <= numberOfWhite;
    }

    /**
     * @param colors represents Resources by their color
     * @return {@code true} if {@param colors} is coherent with colors of Resources
     */
    public boolean isSelectedInputValid(List<Color> colors){
        if (colors.size() != 2) return false;
        return !colors.contains(Color.WHITE) && !colors.contains(Color.GREEN) && !colors.contains(Color.RED);
    }

    /**
     * @param colors represent Production Power Output
     * @return {@code true} if Production Power Input selected is possible for the game rules
     */
    public boolean isSelectedOutputValid(List<Color> colors){
        if (colors.size() != 1) return false;
        return !colors.contains(Color.WHITE) && !colors.contains(Color.GREEN) && !colors.contains(Color.RED);
    }

    /**
     * @param depots    where to put the resources
     * @param resources to be placed in the {@param depots}
     * @return {@code true} if the {@param resources} are storable {@code false} otherwise
     */
    public boolean canBeStoredInDepots(Depots depots, Resources resources) {
        return depots.canBeStored(resources);
    }

    /**
     * @param player is the player who wants to active the pre selected production
     * @param cost   of pre selected production
     * @return {@code true} if {@param player} can active the the pre selected production {@code false} otherwise.
     */
    public boolean isProductionActivatable(Player player, Resources cost) {
        return player.getBoard().getTotalResources().biggerThan(cost);
    }

    /**
     * @param position represents position where to put the slide marble
     * @return {@code true} if {@param position} is safe {@code false} otherwise.
     */
    public boolean isPositionSafeForMarketTray(int position) {
        return position >= 1 && position <= 7;
    }

    /**
     * @param resources is a list representing the resources taken from the market
     * @param discard is a list representing the resources a player decides to discard
     * @return {@code true} if {@param discard} is smaller than {@param resources}
     */
    public boolean isDiscardSafe(List<Integer> resources, List<Integer> discard){
        Resources totalResources = new Resources(resources.get(0),resources.get(1),resources.get(2),resources.get(3));
        Resources discardResources = new Resources(discard.get(0), discard.get(1), discard.get(2), discard.get(3));
        return totalResources.biggerThan(discardResources);
    }


    /**
     * @param buyer is the player who wants to buy the {@param card}
     * @param cardCost  is the selected DevelopmentCard's cost
     * @return {@code true} if {@param buyer} can buy the {@param card} {@code false} otherwise.
     */
    public boolean isDevelopmentCardBuyable(Player buyer, Resources cardCost) {
        return buyer.getBoard().getTotalResources().biggerThan(cardCost);
    }

    /**
     * @param player is the player who wants to store the {@param card}
     * @param card   is the selected DevelopmentCard
     * @return {@code true} if {@param player} have space for store the {@param card} {@code false} otherwise.
     */
    public boolean isDevelopmentCardStorable(Player player, DevelopmentCard card) {
        List<List<DevelopmentCard>> cardsSlots = player.getBoard().getDevelopmentCards();
        for (List<DevelopmentCard> cardsSlot : cardsSlots) {
            if (cardsSlot.size() == card.getLevel() - 1) return true;
        }
        return false;
    }

    /**
     * @param position represents position of a deck of DevCards
     * @return {@code true} if {@param position} is coherent with max and min position of DevCardTray, {@code false} otherwise.
     */
    public boolean isPositionSafeForDevCardsTray(int position) {
        return (position >= 0 && position < 12);
    }

    /**
     * @param position represents position of DevCard slots of the Board
     * @return {@code true} if {@param position} is coherent with max and min position of the DevCards slots of the Board, {@code false} otherwise.
     */
    public boolean isSlotInputSafeForStoreDevCard(int position) {
        return (position >= 1 && position <= 3);
    }

    /**
     * @param slotSize represents how many DevCards are in a slot
     * @param cardLevel represent level of the bought by the Player
     * @return {@code true} if {@param slotSize} correspond to {@param cardLevel}, {@code false} otherwise.
     */
    public boolean isSlotSafe(int slotSize, int cardLevel){
        return cardLevel == slotSize+1;
    }

    /**
     * @param slot one of the three free slots on board
     * @param card is the selected DevelopmentCard
     * @return {@code true} if {@param player} slot is free {@code false} otherwise.
     */
    public boolean isMiniSlotFree(List<DevelopmentCard> slot, DevelopmentCard card) {
        return slot.size() == (card.getLevel() - 1);
    }

    /**
     * @param selectedProductions represents production selected by a player
     * @param availableProductions represents productions the player can actually do
     * @return {@code true} if {@param selectedProductions} are allowed
     */
    public boolean isSelectedProductionValid(List<Integer> selectedProductions, List<Integer> availableProductions){
        List<Integer> tempSelectedProduction;
        if (selectedProductions.size() > availableProductions.size()) return false;
        for (Integer i : selectedProductions){
            if(!availableProductions.contains(i)) return false;
        }
        tempSelectedProduction = selectedProductions.stream().distinct().collect(Collectors.toList());
        return selectedProductions.size() == tempSelectedProduction.size();
    }

    /**
     * @param color represents color of DevCard
     * @return {@code true} if {@param color} is finished {@code false} otherwise.
     */
    public boolean isDevelopmentCardColorFinished(Color color, DevCardsTray devCardsTray) {
        List<DevelopmentCard> list = devCardsTray.getColor(color);
        for(DevelopmentCard developmentCard : list){
            if (!developmentCard.getIsTaken() && !developmentCard.isAllTaken()) return false;
        }
        return true;
    }

    /**
     * Used in the MatchControllerSolo as part of the check for Lorenzo's actions
     * @return {@code true} if {@param devCardsTray} has no cards left of any color
     */
    public boolean isOneDevelopmentCardColorFinished(DevCardsTray devCardsTray){
        return (isDevelopmentCardColorFinished(Color.YELLOW, devCardsTray))||(isDevelopmentCardColorFinished(Color.PURPLE, devCardsTray))||(isDevelopmentCardColorFinished(Color.GREEN, devCardsTray))||(isDevelopmentCardColorFinished(Color.BLUE, devCardsTray));
    }


    /**
     * @param allPlayers is the list of all the players
     * @return {@code true} if a player triggers a Vatican Report
     */
    public boolean isVaticanReportTriggered(List<Player> allPlayers, boolean[] vaticanReportTiles){
        for (Player player : allPlayers) {
            if ((!vaticanReportTiles[0]) && (player.getBoard().getFaithMarker() >= 8)) return true;
            if ((!vaticanReportTiles[1]) && (player.getBoard().getFaithMarker() >= 16)) return true;
            if ((!vaticanReportTiles[2]) && (player.getBoard().getFaithMarker() >= 24)) return true;
        }
        return false;
    }

    /**
     * @param vaticanReportTiles represent vaticanReport tiles on the faithTack
     * @return {@code true} if {@param player} or {@param lorenzo} are on vaticanReport tile on the faithTrack, {@code false} otherwise.
     */
    public boolean isVaticanReportTriggeredSolo(Player player, SoloOpponent lorenzo, boolean[] vaticanReportTiles){
        if ((!vaticanReportTiles[0]) && ((player.getBoard().getFaithMarker() >= 8)||(lorenzo.getBlackCross() >= 8))) return true;
        if ((!vaticanReportTiles[1]) && ((player.getBoard().getFaithMarker() >= 16)||(lorenzo.getBlackCross() >= 16))) return true;
        return (!vaticanReportTiles[2]) && ((player.getBoard().getFaithMarker() >= 24) || (lorenzo.getBlackCross() >= 24));
    }

    /**
     * @return {@code true} if the Black Cross token reaches the final space of your Faith Track {@code false} otherwise
     */
    public boolean isOpponentEndingFaithTrack(SoloOpponent soloOpponent) {
        return soloOpponent.getBlackCross() >= 24;
    }

    /**
     * @param player who may have finished the match
     * @return {@code true} if {@param player} have finished the match {@code false} otherwise.
     */
    public boolean isMatchEnded(Player player) {
        int numberofDevCards = 0;
        for (List<DevelopmentCard> cardsSlot : player.getBoard().getDevelopmentCards()) {
            numberofDevCards += cardsSlot.size();
        }
        if (numberofDevCards >= 7) return true;
        else return player.getBoard().getFaithMarker() == 24;
    }

    /**
     * @param players is the list of all the players
     * @return {@code true} if someone in the {@param players} list have finished the match {@code false} otherwise
     */
    public boolean isMatchEnded(List<Player> players){
        for (Player player: players){
            if (isMatchEnded(player)) return true;
        }
        return false;
    }

}
