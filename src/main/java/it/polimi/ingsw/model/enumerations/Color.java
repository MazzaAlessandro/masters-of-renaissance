package it.polimi.ingsw.model.enumerations;

import it.polimi.ingsw.view.cli.Graphics;

/**
 * Enumeration representing colors of Development Cards (BLUE, YELLOW, GREEN, PURPLE), Leader Cards (BLUE, YELLOW, GREEN, PURPLE),
 * Resources (GREY=stones, BLUE=shields, YELLOW=coins, PURPLE=servants) and Marbles (GREY, BLUE, YELLOW, PURPLE, RED, WHITE)
 */
public enum Color {
    GREY,
    BLUE,
    PURPLE,
    YELLOW,
    RED,
    WHITE,
    GREEN;

    /**
     * @param color represents Marbles used in MarketTray
     * @return a colored graphic for MarketTray in Cli
     */
    public static String colorToAnsiMarble(Color color) {
        switch (color) {
            case RED:
                return Graphics.ANSI_RED + "●" + Graphics.ANSI_RESET;
            case BLUE:
                return Graphics.ANSI_BLUE + "●" + Graphics.ANSI_RESET;
            case GREY:
                return Graphics.ANSI_GREY + "●" + Graphics.ANSI_RESET;
            case YELLOW:
                return Graphics.ANSI_YELLOW + "●" + Graphics.ANSI_RESET;
            case PURPLE:
                return Graphics.ANSI_PURPLE + "●" + Graphics.ANSI_RESET;
            case GREEN:
                return Graphics.ANSI_GREEN + "●" + Graphics.ANSI_RESET;
            default:
                return "●";
        }
    }
}

