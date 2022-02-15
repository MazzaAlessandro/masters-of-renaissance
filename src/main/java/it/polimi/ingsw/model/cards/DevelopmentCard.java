package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.view.cli.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents DevelopmentCards in the game
 */
public class DevelopmentCard extends Card {
    private Resources cost;
    private Color type;
    private int level;
    private ProductionPower productionPower;
    private boolean isTaken;
    private boolean allTaken;

    public DevelopmentCard(Resources cost, Color type, int level, ProductionPower productionPower, boolean isTaken, int victoryPoints) {
        this.cost = cost;
        this.type = type;
        this.level = level;
        this.productionPower = productionPower;
        this.isTaken = false;
        super.victoryPoints = victoryPoints;
    }


    public DevelopmentCard(boolean allTaken) {
        this.cost = new Resources(0,0,0,0);
        this.type = Color.GREEN;
        this.level = 0;
        this.productionPower = new ProductionPower(new Resources(0,0,0,0),new Resources(0,0,0,0));
        this.face = "/images/cards/emptyCard.png";
        this.allTaken = allTaken;
    }

    public Resources getCost() {
        return cost;
    }

    public Color getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public ProductionPower getProductionPower() {
        return productionPower;
    }

    public boolean getIsTaken() {
        return isTaken;
    }

    public boolean isAllTaken() {
        return allTaken;
    }

    public void setAllTaken(boolean allTaken) {
        this.allTaken = allTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    /**
     * @return the empty DevelopmentCard
     */
    public static DevelopmentCard addEmptyCards(){

        return new DevelopmentCard(true);
    }

    public static List<List<String>> getDevCardsPrintInfo(List<DevelopmentCard> freeDevCards) {
        List<List<String>> cards = new ArrayList<>();
        List<String> cost = new ArrayList<>();
        List<String> type = new ArrayList<>();
        List<String> level = new ArrayList<>();
        List<String> productionPowerInput = new ArrayList<>();
        List<String> productionPowerOutput = new ArrayList<>();
        List<String> victoryPoints = new ArrayList<>();
        List<String> allTaken = new ArrayList<>();
        List<String> face = new ArrayList<>();
        for (DevelopmentCard card : freeDevCards) {
            cost.add(resourceToFancyString(card.getCost()));
            type.add(card.getType().toString());
            level.add(Integer.toString(card.getLevel()));
            productionPowerInput.add(resourceToFancyString(card.getProductionPower().getInputResources()));
            productionPowerOutput.add(resourceToFancyString(card.getProductionPower().getOutputResources()));
            victoryPoints.add(Integer.toString(card.getVictoryPoints()));
            allTaken.add(Boolean.toString(card.isAllTaken()));
            face.add(card.face);

        }
        cards.add(cost);
        cards.add(type);
        cards.add(level);
        cards.add(productionPowerInput);
        cards.add(productionPowerOutput);
        cards.add(victoryPoints);
        cards.add(allTaken);
        cards.add(face);
        return cards;
    }

    private static String resourceToFancyString(Resources resource) {
        StringBuilder mainBuilder = new StringBuilder();
        StringBuilder builder = new StringBuilder();

        if (resource.getCoins() > 0) {
            builder.append(" ").append(resource.getCoins()).append(Graphics.ANSI_YELLOW).append("● ").append(Graphics.ANSI_RESET);
        }

        if (resource.getServants() > 0) {
            builder.append(" ").append(resource.getServants()).append(Graphics.ANSI_PURPLE).append("● ").append(Graphics.ANSI_RESET);
        }

        if (resource.getStones() > 0) {
            builder.append(" ").append(resource.getStones()).append(Graphics.ANSI_GREY).append("● ").append(Graphics.ANSI_RESET);
        }

        if (resource.getShields() > 0) {
            builder.append(" ").append(resource.getShields()).append(Graphics.ANSI_BLUE).append("● ").append(Graphics.ANSI_RESET);
        }

        if (resource.getFaith() > 0) {
            builder.append(" ").append(resource.getFaith()).append(Graphics.ANSI_RED).append("● ").append(Graphics.ANSI_RESET);
        }
        switch (resource.typeVariety()) {
            case (1):
                mainBuilder.append("         ").append(builder).append("          ");
                return mainBuilder.toString();
            case (2):
                mainBuilder.append("       ").append(builder).append("        ");
                return mainBuilder.toString();
            case (3):
                mainBuilder.append("     ").append(builder).append("      ");
                return mainBuilder.toString();
            case (4):
                mainBuilder.append("   ").append(builder).append("    ");
                return mainBuilder.toString();
            case (5):
                mainBuilder.append(" ").append(builder).append("  ");
                return mainBuilder.toString();
            default:
                return mainBuilder.toString();
        }

    }


    @Override
    public String toString() {
        return "{\"DevelopmentCard\":{"
                + "\"face\":" + face
                + ",\"victoryPoints\":\"" + victoryPoints + "\""
                + ",\"cost\":" + cost
                + ",\"type\":\"" + type + "\""
                + ",\"level\":\"" + level + "\""
                + ",\"productionPower\":" + productionPower
                + ",\"isTaken\":\"" + isTaken + "\""
                + ",\"allTaken\":\"" + allTaken + "\""
                + "}}";
    }


}
