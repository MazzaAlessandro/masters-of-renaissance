package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.ProductionPower;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.SpecialAbility;
import it.polimi.ingsw.model.enumerations.StorableResources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represent a player's board, containing the faith track, the owned resources both in the Depots and the Strongbox, the owned Development Cards and Leader Cards
 */
public class Board {

    private int faithMarker;
    private final boolean[] popeFavorTiles;
    private final Depots warehouseDepots;
    private final Resources strongBox;
    private BuyDevCardStrategy buyDevCardStrategy;
    private final List<List<DevelopmentCard>> developmentCards;
    private final List<LeaderCard> leaderCards;
    private final ProductionPower basicProductionPower;


    public boolean[] getPopeFavorTiles() {
        return popeFavorTiles;
    }

    public ProductionPower getBasicProductionPower() {
        return basicProductionPower;
    }


    public void setBasicProductionPowerInput(Resources input){
        this.basicProductionPower.setInputResources(input);
    }

    public void setBasicProductionPowerOutput(Resources output){
        this.basicProductionPower.setInputResources(output);
    }

    public void setFaithMarker(int faithMarker) {
        this.faithMarker = faithMarker;
    }

    public List<List<DevelopmentCard>> getDevelopmentCards() {
        return developmentCards;
    }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public Depots getWarehouseDepots() {
        return warehouseDepots;
    }

    public Resources getStrongBox() {
        return strongBox;
    }

    public int getFaithMarker() {
        return faithMarker;
    }

    /**
     * Board creator, setting faithMarker to 0, popeFavorTiles to all false and creates the respective Depots
     */
    public Board(){
        faithMarker = 0;
        popeFavorTiles = new boolean[]{false, false, false};
        warehouseDepots = new Depots();
        strongBox = new Resources();
        buyDevCardStrategy = new BuyDevCardStandard();
        developmentCards = new ArrayList<>();
        developmentCards.add(0, new ArrayList<>());
        developmentCards.add(1, new ArrayList<>());
        developmentCards.add(2, new ArrayList<>());
        leaderCards = new ArrayList<>();
        basicProductionPower = new ProductionPower(null, null);
    }

    /**
     * @return a sum of resources stored into the warehouseDepots and strongBox
     */
    public Resources getTotalResources(){
        Resources totalResources = new Resources();
        totalResources.add(strongBox);
        totalResources.add(warehouseDepots.getValue());
        return totalResources;
    }

    public void setBasicProductionPowerInput(List<Color> inputColors){
        Resources inputProdResources = new Resources();
        for(Color color : inputColors){
            convertColor(color, inputProdResources);
        }
        this.basicProductionPower.setInputResources(inputProdResources);
    }

    public void setBasicProductionPowerOutput(List<Color> outputColors){
        Resources outputProdResources = new Resources();
        for(Color color : outputColors){
            convertColor(color, outputProdResources);
        }
        this.basicProductionPower.setOutputResources(outputProdResources);
    }


    public void ResetProductionPower(){
        this.basicProductionPower.setInputResources(new Resources());
        this.basicProductionPower.setOutputResources(new Resources());
    }

    /**
     * Increases the faithMarker of this Board by a certain amount
     * @param amount is how much the faithMarker increases
     */
    public void increaseFaith(int amount){
        faithMarker= faithMarker + amount;
        if (faithMarker > 24) faithMarker = 24;
    }

    /**
     * If the player's faithMarker is high enough, it sets true the respective vatican report card
     * @param vaticanReportNumber indicates which vatican report triggered
     */
    public void setPopeFavorTiles(int vaticanReportNumber){
        int validZone = 7*(vaticanReportNumber+1) - 2; //based on the given FaithTrack
        if (this.faithMarker >= validZone) popeFavorTiles[vaticanReportNumber] = true;
    }

    /**
     * @return how many victoryPoints the player has based on the owned Development/Leader cards, resources and the faithMarker
     */
    public int getVictoryPoints(){
        int totalVictoryPoints = 0;
        int totalOwnedResources;
        if (popeFavorTiles[0]) totalVictoryPoints = totalVictoryPoints + 2;
        if (popeFavorTiles[1]) totalVictoryPoints = totalVictoryPoints + 3;
        if (popeFavorTiles[2]) totalVictoryPoints = totalVictoryPoints+ 4;
        for (List<DevelopmentCard> developmentCardList : developmentCards){
            totalVictoryPoints = totalVictoryPoints + developmentCardList.stream().mapToInt(Card::getVictoryPoints).sum();
        }
        totalVictoryPoints = totalVictoryPoints + leaderCards.stream().filter(LeaderCard::isActive).mapToInt(Card::getVictoryPoints).sum();
        totalOwnedResources = strongBox.getAmount() + warehouseDepots.getValue().getAmount();
        totalVictoryPoints = totalVictoryPoints + (totalOwnedResources - (totalOwnedResources%5))/5;
        if (faithMarker>=3 && faithMarker < 6) totalVictoryPoints = totalVictoryPoints + 1;
        if (faithMarker>=6 && faithMarker < 9) totalVictoryPoints = totalVictoryPoints + 2;
        if (faithMarker>=9 && faithMarker < 12) totalVictoryPoints = totalVictoryPoints + 4;
        if (faithMarker>=12 && faithMarker < 15) totalVictoryPoints = totalVictoryPoints + 6;
        if (faithMarker>=15 && faithMarker < 18) totalVictoryPoints = totalVictoryPoints + 9;
        if (faithMarker>=18 && faithMarker < 21) totalVictoryPoints = totalVictoryPoints + 12;
        if (faithMarker>=21 && faithMarker < 24) totalVictoryPoints = totalVictoryPoints + 16;
        if (faithMarker == 24) totalVictoryPoints = totalVictoryPoints + 20;
        return totalVictoryPoints;
    }

    /**
     * Spends a certain amount of the owned resources, giving priority to the ones in the warehouseDepots since its space is limited
     * @param neededResources is the amount of resources to spend
     */
    public void spendResources(Resources neededResources){
        Resources takenFromDepots = new Resources();
        int coinAmount, stonesAmount, servantAmount, shieldAmount;
        coinAmount = Math.min(neededResources.getCoins(), warehouseDepots.getValue().getCoins());
        stonesAmount = Math.min(neededResources.getStones(), warehouseDepots.getValue().getStones());
        servantAmount = Math.min(neededResources.getServants(), warehouseDepots.getValue().getServants());
        shieldAmount = Math.min(neededResources.getShields(), warehouseDepots.getValue().getShields());
        takenFromDepots.setResources(coinAmount, stonesAmount, servantAmount, shieldAmount, 0);
        warehouseDepots.takeResources(takenFromDepots);
        strongBox.subtractAmount((neededResources.getCoins() - coinAmount), StorableResources.COINS);
        strongBox.subtractAmount((neededResources.getStones() - stonesAmount), StorableResources.STONES);
        strongBox.subtractAmount((neededResources.getServants() - servantAmount), StorableResources.SERVANTS);
        strongBox.subtractAmount((neededResources.getShields() - shieldAmount), StorableResources.SHIELDS);
    }

    /**
     * Converts the marbles bought from the market to a set of Resources, increasing faith if the red marble is present
     * @param boughtMarbles is a list of Color, representing the set of marbles bought
     * @return a set of Resources with the corresponding amounts
     */
    public Resources takeResources(List<Color> boughtMarbles){
        Resources boughtResources = new Resources();
        for (Color color : boughtMarbles){
            convertColor(color, boughtResources);
        }
        return boughtResources;
    }

    /**
     * Given a color, it adds the corresponding resource to a given Resources set or increases faith for the Red color
     * @param toConvert is the given color
     * @param boughtResources is the Resources set
     */
    private void convertColor(Color toConvert, Resources boughtResources){
        if (toConvert == Color.YELLOW) boughtResources.setCoins(boughtResources.getCoins() + 1);
        if (toConvert == Color.BLUE) boughtResources.setShields(boughtResources.getShields() + 1);
        if (toConvert == Color.GREY) boughtResources.setStones(boughtResources.getStones() + 1);
        if (toConvert == Color.PURPLE) boughtResources.setServants(boughtResources.getServants() + 1);
        if (toConvert == Color.RED) this.increaseFaith(1);
    }

    /**
     * Adds a set of Resources to the Strongbox after increasing the player's faith
     * @param toAdd is the Resource set that is added to the Strongbox
     */
    public void addToStrongbox(Resources toAdd){
        if(toAdd.getFaith()!=0) {
            increaseFaith(toAdd.getFaith());
            toAdd.setFaith(0);
        }
        strongBox.add(toAdd);
    }

    /**
     * Places a Development Card in the selected slot, spending resources needed to buy it
     * @param boughtCard is the Development Card that was bought
     * @param DevCardsSlot is the chosen slot to place the Development Card
     */
    public void buyDevCard(DevelopmentCard boughtCard, List<DevelopmentCard> DevCardsSlot){
        buyDevCardStrategy.buyDevCard(boughtCard, this);
        DevCardsSlot.add(boughtCard);
    }

    /**
     * @param toInsert is the Development Card that needs to be inserted into the Board
     * @return a list of the available slots for that card, based on the level compatibility
     */
    public List<List<DevelopmentCard>> getAvailableSlots(DevelopmentCard toInsert){
        List<List<DevelopmentCard>> availableSlots = new ArrayList<>();
        if (toInsert.getLevel() == 1){
            for (List<DevelopmentCard> devCardSlot : developmentCards){
                if (devCardSlot.isEmpty()) availableSlots.add(devCardSlot);
            }
        }
        else{
            for (List<DevelopmentCard> devCardSlot : developmentCards){
                if ((!devCardSlot.isEmpty()) && devCardSlot.get(devCardSlot.size()-1).getLevel() == toInsert.getLevel() - 1)
                {
                    availableSlots.add(devCardSlot);
                }
            }
        }
        return availableSlots;
    }

    /**
     * @param level is the level of the Development Cards
     * @return a list of colors based on the owned Development Cards of the selected level
     */
    public List<Color> getColorsByLevel(int level){
        return developmentCards.stream().
                flatMap(List::stream).filter(DevelopmentCard -> (DevelopmentCard.getLevel()==level)).
                map(DevelopmentCard::getType).collect(Collectors.toList());
    }

    /**
     * Depending on the Leader Card's special ability, does different things
     * With an EXTRADEPOTS Leader Card adds a new layer to the Depots with 2 spaces of the corresponding resource
     * With a DISCOUNT Leader Card alters the buyDevCardsStrategy, always applying the discount to the Card's cost if possible
     * @param toActivate is the Leader Card that gets activated
     */
    public void activateLeaderCard(LeaderCard toActivate){
        if (toActivate.getSpecialAbility() == SpecialAbility.EXTRADEPOT) warehouseDepots.addLeaderCardLayer(toActivate);
        if (toActivate.getSpecialAbility() == SpecialAbility.DISCOUNT) buyDevCardStrategy = new BuyDevCardDiscount();
    }

    public List<List<String>> getActiveLeadersFancyCli (){
        List<List<String>> leaders = new ArrayList<>();
        for (LeaderCard card: leaderCards) {
            if(card.isActive()) leaders.add(card.leadersToFancyString());
        }
        return leaders;
    }

    public List<List<String>> getInactiveLeadersFancyCli(){
       List<List<String>> leaders = new ArrayList<>();
        for (LeaderCard card: leaderCards) {
            if(!card.isActive()) leaders.add(card.leadersToFancyString());
        }
       return leaders;
    }

    public List<List<String>> getTopLevelDevCards(){
        List<DevelopmentCard> topDevCards = new ArrayList<>();
        for (List<DevelopmentCard> slot:developmentCards) {
           if (!slot.isEmpty()) topDevCards.add(slot.get(slot.size()-1));
           else topDevCards.add(DevelopmentCard.addEmptyCards());
        }
        return DevelopmentCard.getDevCardsPrintInfo(topDevCards);
    }

    public List<String> getSecondaryLevelDevCards(int position){
        List<String> victoryPoints = new ArrayList<>();
        List<String> imagePath = new ArrayList<>();
        for (List<DevelopmentCard> slot:developmentCards) {
            switch (slot.size()){
                case 3:
                    if (position == 1) {
                        victoryPoints.add(Integer.toString(slot.get(1).getVictoryPoints()));
                        imagePath.add(slot.get(1).getFace());
                    } else if (position == 2) {
                        victoryPoints.add(Integer.toString(slot.get(0).getVictoryPoints()));
                        imagePath.add(slot.get(0).getFace());
                    }
                    break;
                case 2:
                    if (position == 1) {
                        victoryPoints.add(Integer.toString(slot.get(0).getVictoryPoints()));
                        imagePath.add(slot.get(0).getFace());
                    }else if (position == 2) {
                    victoryPoints.add("");
                    imagePath.add("/images/cards/emptyCard.png");
                }
                    break;
                case 1:
                default:
                    victoryPoints.add("");
                    imagePath.add("/images/cards/emptyCard.png");
                    break;
            }
        }
        List<String> outList = new ArrayList<>();
        outList.addAll(victoryPoints);
        outList.addAll(imagePath);
        return outList;
    }

    @Override
    public String toString() {
        return "{\"Board\":{"
                + "\"faithMarker\":\"" + faithMarker + "\""
                + ",\"popeFavorTiles\":" + Arrays.toString(popeFavorTiles)
                + ",\"warehouseDepots\":" + warehouseDepots
                + ",\"strongBox\":" + strongBox
                + ",\"developmentCards\":" + developmentCards
                + ",\"leaderCards\":" + leaderCards
                + "}}";
    }
}
