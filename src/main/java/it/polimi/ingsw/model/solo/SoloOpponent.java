package it.polimi.ingsw.model.solo;

import it.polimi.ingsw.model.cards.DevCardsTray;
import it.polimi.ingsw.model.enumerations.Color;

/**
 * Represents the only opponent in the single player
 */
public class SoloOpponent {
    private int blackCross;
    private final SoloActionTokens actionTokens = new SoloActionTokens();
    private final DevCardsTray devCardsTray;

    /**
     * SoloOpponent creator with blackCross reset and DevCardTray connected
     */
    public SoloOpponent(DevCardsTray devCardsTray) {
        this.blackCross = 0;
        this.devCardsTray = devCardsTray;
    }

    /**
     * Method that moves the BlackCross marker
     *
     * @param value is number of of steps forward
     */
    public void increaseBlackCross(int value) {
        this.blackCross = this.blackCross + value;
    }

    /**
     * Shuffle the ActionTokens stack
     */
    public void shuffleActionTokens() {
        actionTokens.shuffle();
    }

    /**
     * Make opponent step (pick and apply ActionToken effect)
     */
    public void pickActionToken() {
        SoloActionToken pick = actionTokens.pop();
        pick.getEffect().doAction(this);
    }
    
    public String pickActionTokenFancyCli(){
        SoloActionToken pick = actionTokens.pop();
        pick.getEffect().doAction(this);
        switch (pick.getType()){
            case MOVE2FORWARD:
                return "Lorenzo move the Black Cross token forward by 2 spaces.";
            case MOVEANDSHUFFLE:
                return "Lorenzo move the Black Cross token forward by 1 space.\n" +
                        "Then, he shuffle all the Solo Action tokens.";
            case DISCARDDEVCARDS:
                Color color = pick.getColor();
                return "Lorenzo Discard 2 " + color + " Development Cards\n" +
                        "from the bottom of the grid, from the lowest level to the highest";
            default:
                return "";
        }
    }

    public int getBlackCross() {
        return blackCross;
    }

    public DevCardsTray getDevCardsTray() {
        return devCardsTray;
    }

    @Override
    public String toString() {
        return "{\"SoloOpponent\":{"
                + "\"blackCross\":\"" + blackCross + "\""
                + ", \"actionTokens\":" + actionTokens
                + "}}";
    }
}
