package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.LeaderCardsRequirements;
import it.polimi.ingsw.model.enumerations.SpecialAbility;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents LeaderCards in the game
 */
public class LeaderCard extends Card {
    private Requirements requirements;
    private final LeaderCardsRequirements requiresType;
    private final Resources haveResources;
    private final List<Color> haveDevCards;
    private final Color haveDevCardOfColor;
    private final int haveDevCardOfLevel;
    private final SpecialAbility specialAbility;
    private final Color specialAbilityColor;
    private final ProductionPower specialAbilityProductionPower;
    private boolean isActive;

    public LeaderCard(LeaderCardsRequirements requiresType, Resources haveResources, List<Color> haveDevCards, Color haveDevCardOfColor, int haveDevCardOfLevel, SpecialAbility specialAbility, Color specialAbilityColor, ProductionPower specialAbilityProductionPower, boolean isActive, int victoryPoints, String face) {
        this.requiresType = requiresType;
        this.haveResources = haveResources;
        this.haveDevCards = haveDevCards;
        this.haveDevCardOfColor = haveDevCardOfColor;
        this.haveDevCardOfLevel = haveDevCardOfLevel;
        this.specialAbility = specialAbility;
        this.specialAbilityColor = specialAbilityColor;
        this.specialAbilityProductionPower = specialAbilityProductionPower;
        this.isActive = isActive;
        super.victoryPoints = victoryPoints;
        super.face = face;
    }

    public LeaderCard(LeaderCardsRequirements requiresType, Resources haveResources, List<Color> haveDevCards, Color haveDevCardOfColor, int haveDevCardOfLevel, SpecialAbility specialAbility, Color specialAbilityColor, ProductionPower specialAbilityProductionPower, int victoryPoints, String face) {
        this(requiresType, haveResources, haveDevCards, haveDevCardOfColor, haveDevCardOfLevel, specialAbility, specialAbilityColor, specialAbilityProductionPower, false, victoryPoints, face );
    }

    public LeaderCard(LeaderCardsRequirements requiresType, Resources haveResources, List<Color> haveDevCards, Color haveDevCardOfColor, int haveDevCardOfLevel, SpecialAbility specialAbility, Color specialAbilityColor, ProductionPower specialAbilityProductionPower, int victoryPoints) {
        this(requiresType, haveResources, haveDevCards, haveDevCardOfColor, haveDevCardOfLevel, specialAbility, specialAbilityColor, specialAbilityProductionPower, false, victoryPoints, null );
    }

    public Requirements getRequirements() {
        return requirements;
    }

    public LeaderCardsRequirements getRequiresType() {
        return requiresType;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }


    /**
     * Set the correct requirements based on requiresType of card
     */
    public void setRequirements() {
        switch (this.requiresType) {
            case RESOURCES:
                this.requirements = new RequiresResources(this.haveResources);
                break;
            case DEVELOPMENTCARD:
                this.requirements = new RequiresLevel(this.haveDevCardOfColor, this.haveDevCardOfLevel);
                break;
            case DEVELOPMENTCARDS:
                this.requirements = new RequiresColors(this.haveDevCards);
                break;
        }
    }

    @Override
    public String toString() {
        return "{\"LeaderCard\":{"
                + "\"face\":" + face
                + ",\"victoryPoints\":\"" + victoryPoints + "\""
                + ",\"requirements\":" + requirements
                + ",\"requiresType\":\"" + requiresType + "\""
                + ",\"haveResources\":" + haveResources
                + ",\"haveDevCards\":" + haveDevCards
                + ",\"haveDevCardOfColor\":\"" + haveDevCardOfColor + "\""
                + ",\"haveDevCardOfLevel\":\"" + haveDevCardOfLevel + "\""
                + ",\"specialAbility\":\"" + specialAbility + "\""
                + ",\"specialAbilityColor\":\"" + specialAbilityColor + "\""
                + ",\"specialAbilityProductionPower\":" + specialAbilityProductionPower
                + ",\"isActive\":\"" + isActive + "\""
                + "}}";
    }

    public Color getSpecialAbilityColor() {
        return specialAbilityColor;
    }

    public SpecialAbility getSpecialAbility() {
        return specialAbility;
    }

    public ProductionPower getSpecialAbilityProductionPower() {
        return specialAbilityProductionPower;
    }


    public void setSpecialAbilityProductionPower(Color outputColor) {
        if (outputColor.equals(Color.YELLOW))
            specialAbilityProductionPower.setOutputResources(new Resources(1, 0, 0, 0, 1));
        if (outputColor.equals(Color.GREY))
            specialAbilityProductionPower.setOutputResources(new Resources(0, 1, 0, 0, 1));
        if (outputColor.equals(Color.PURPLE))
            specialAbilityProductionPower.setOutputResources(new Resources(0, 0, 1, 0, 1));
        if (outputColor.equals(Color.BLUE))
            specialAbilityProductionPower.setOutputResources(new Resources(0, 0, 0, 1, 1));
    }

    public void resetSpecialAbilityProductionPower() {
        this.specialAbilityProductionPower.setOutputResources(new Resources(0, 0, 0, 0, 1));
    }

    public List<String> leadersToFancyString() {
        List<String> list = new ArrayList<>();

        switch (requiresType) {
            case RESOURCES:
                RequiresResources requiresResources = (RequiresResources) requirements;
                list.add("│     Resources " + requiresResources.getRequired().resourceToFancyString()+"      │");
                break;
            case DEVELOPMENTCARD:
                RequiresLevel requiresLevel = (RequiresLevel) requirements;
                list.add("│ " +  "One DevCard "+Color.colorToAnsiMarble(requiresLevel.getRequiredColor()) +" Level " + requiresLevel.getRequiredLevel() + " │");
                break;
            case DEVELOPMENTCARDS:
                RequiresColors requiresColors = (RequiresColors) requirements;
                StringBuilder builder = new StringBuilder();
                builder.append("│     DevCards ");
                for (Color color : requiresColors.getRequires()) {
                    builder.append(Color.colorToAnsiMarble(color)).append(" ");
                }
                if(requiresColors.getRequires().size() == 1)  builder.append("    ");
                else if(requiresColors.getRequires().size() == 2)  builder.append("  ");
                builder.append("   │");
                list.add(builder.toString());
                break;
        }

        switch (specialAbility) {
            case DISCOUNT:
                list.add("│       DISCOUNT        │");
                list.add("│           "+Color.colorToAnsiMarble(specialAbilityColor)+"           │");
                list.add("│                       │");
                list.add("│                       │");
                break;

            case MOREPOWER:
                list.add("│       MOREPOWER       │");
                list.add("│          "+specialAbilityProductionPower.getInputResources().resourceToFancyString()+"           │");
                list.add("│           ↓           │");
                list.add("│         "+specialAbilityProductionPower.getOutputResources().resourceToFancyString()+" 1?         │");
                break;

            case EXTRADEPOT:
                list.add("│       EXTRADEPOT      │");
                list.add("│           "+Color.colorToAnsiMarble(specialAbilityColor)+"           │");
                list.add("│                       │");
                list.add("│                       │");
                break;

            case WHITEMARBLE:
                list.add("│       WHITEMARBLE     │");
                list.add("│           "+Color.colorToAnsiMarble(specialAbilityColor)+"           │");
                list.add("│                       │");
                list.add("│                       │");
                break;

        }
        String points = Integer.toString(victoryPoints);
        if (points.length() == 2) list.add("│   "+points+" Victory Points   │");
        else list.add("│    "+points+" Victory Points   │");
        list.add(Boolean.toString(isActive));
        list.add(super.face);
        return list;
    }
}




