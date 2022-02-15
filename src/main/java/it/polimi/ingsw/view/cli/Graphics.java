package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.enumerations.Color;

import java.util.ArrayList;
import java.util.List;

public class Graphics {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREY = "\u001B[37m";

    public static final String ANSI_YELLOW_UNDERLINED = "\033[4;33m";
    public static final String ANSI_BG_WHITE_BRIGHT = "\033[0;107m";

    public static final String	ITALIC = "\u001B[3m";
    public static final String BOLD = "\u001b[1m";

    private final String gap = "  ";
    private final String emptyCard = ANSI_GREY+"░░░░░░░░░░░░░░░░░░░░░░░░░"+ANSI_RESET;



    public String getTitle(){

        return  "                 "+ANSI_YELLOW+"███"+ANSI_BLUE+"╗"+ANSI_RESET+"░░░"+ANSI_YELLOW+"███"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"█████"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"███████"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"██████"+ANSI_BLUE+"╗"+ANSI_YELLOW+"████████"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██████"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_RESET+"        "+ANSI_YELLOW+"██████"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"███████"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██" +ANSI_BLUE+ "╗"+ ANSI_RESET+"░░░░░\n" +
                "                 "+ANSI_YELLOW+"████"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"████"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔════╝"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔════╝╚══"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══╝"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"        "+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔════╝"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░░░░\n" +
                "                 "+ANSI_YELLOW+"██"+ANSI_BLUE+"╔"+ANSI_YELLOW+"████"+ANSI_BLUE+"╔"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"███████"+ANSI_BLUE+"║"+ANSI_YELLOW+"█████"+ANSI_BLUE+"╗"+ANSI_RESET+"░░"+ANSI_BLUE+"╚"+ANSI_YELLOW+"█████"+ANSI_BLUE+"╗"+ANSI_RESET+"░░░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░░"+ANSI_YELLOW+"██████"+ANSI_BLUE+"╔╝"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"        ██"+ANSI_BLUE+"║"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"█████"+ANSI_BLUE+"╗"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░░░░\n" +
                "                 "+ANSI_YELLOW+"██"+ANSI_BLUE+"║╚"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔╝"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══╝"+ANSI_RESET+"░░░"+ANSI_BLUE+"╚═══"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_RESET+"░░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██"+ANSI_BLUE+"║        "+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══╝"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░░░░\n" +
                "                 "+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░"+ANSI_BLUE+"╚═╝"+ANSI_RESET+"░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"███████"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██████"+ANSI_BLUE+"╔╝"+ANSI_RESET+"░░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"║         "+ANSI_YELLOW+"█████"+ANSI_BLUE+"╔╝"+ANSI_YELLOW+"███████"+ANSI_BLUE+"╗"+ANSI_YELLOW+"███████"+ANSI_BLUE+"╗\n" +
                "                 ╚═╝"+ANSI_RESET+"░░░░░"+ANSI_BLUE+"╚═╝╚═╝"+ANSI_RESET+"░░"+ANSI_BLUE+"╚═╝╚══════╝╚═════╝"+ANSI_RESET+"░░░░"+ANSI_BLUE+"╚═╝"+ANSI_RESET+"░░░"+ANSI_BLUE+"╚═╝"+ANSI_RESET+"░░"+ANSI_BLUE+"╚═╝╚═╝        ╚═════╝"+ANSI_RESET+"░"+ANSI_BLUE+"╚══════╝╚══════╝\n" +
                "\n" +
                "              "+ANSI_YELLOW+"██████"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"███"+ANSI_BLUE+"╗"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"█████"+ANSI_BLUE+"╗"+ANSI_RESET+"░░"+ANSI_YELLOW+"██████"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"█████"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"███"+ANSI_BLUE+"╗"+ANSI_RESET+"░░░"+ANSI_YELLOW+"███"+ANSI_BLUE+"╗"+ANSI_YELLOW+"███████"+ANSI_BLUE+"╗"+ANSI_YELLOW+"███"+ANSI_BLUE+"╗"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"████████"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"█████"+ANSI_BLUE+"╗"+ANSI_RESET+"░\n" +
                "              "+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"████"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔════╝"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"████"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"████"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔════╝"+ANSI_YELLOW+"████"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║╚══"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══╝"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗\n" +
                "              "+ANSI_YELLOW+"██████"+ANSI_BLUE+"╔╝"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"███████"+ANSI_BLUE+"║╚"+ANSI_YELLOW+"█████"+ANSI_BLUE+"╗"+ANSI_RESET+"░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░"+ANSI_BLUE+"╚═╝"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔"+ANSI_YELLOW+"████"+ANSI_BLUE+"╔"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"█████"+ANSI_BLUE+"╗"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║\n" +
                "              "+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"║╚"+ANSI_YELLOW+"████"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░"+ANSI_BLUE+"╚═══"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"║╚"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔╝"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"╔══╝"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║╚"+ANSI_YELLOW+"████"+ANSI_BLUE+"║"+ANSI_RESET+"░░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║\n" +
                "              "+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░"+ANSI_BLUE+"╚"+ANSI_YELLOW+"███"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██████"+ANSI_BLUE+"╔╝╚"+ANSI_YELLOW+"█████"+ANSI_BLUE+"╔╝"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░"+ANSI_BLUE+"╚═╝"+ANSI_RESET+"░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_YELLOW+"███████"+ANSI_BLUE+"╗"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░"+ANSI_BLUE+"╚"+ANSI_YELLOW+"███"+ANSI_BLUE+"║"+ANSI_RESET+"░░░"+ANSI_YELLOW+"██"+ANSI_BLUE+"║"+ANSI_RESET+"░░░"+ANSI_BLUE+"╚"+ANSI_YELLOW+"█████"+ANSI_BLUE+"╔╝\n" +
                "              ╚═╝"+ANSI_RESET+"░░"+ANSI_BLUE+"╚═╝╚═╝╚═╝"+ANSI_RESET+"░░"+ANSI_BLUE+"╚══╝╚═╝"+ANSI_RESET+"░░"+ANSI_BLUE+"╚═╝╚═════╝"+ANSI_RESET+"░░"+ANSI_BLUE+"╚════╝"+ANSI_RESET+"░"+ANSI_BLUE+"╚═╝╚═╝"+ANSI_RESET+"░░░░░"+ANSI_BLUE+"╚═╝╚══════╝╚═╝"+ANSI_RESET+"░░"+ANSI_BLUE+"╚══╝"+ANSI_RESET+"░░░"+ANSI_BLUE+"╚═╝"+ANSI_RESET+"░░░░"+ANSI_BLUE+"╚════╝"+ ANSI_RESET+"░";
    }


    public String getWelcomeMessage() {
        return ANSI_YELLOW_UNDERLINED + "It is a great honour to have you in our game!\n" +
                "All of us here are very excited to play with you.\n" +
                "Welcome aboard!" + ANSI_RESET;
    }

    /**
     * @return a message explaining symbols representing Resources
     */
    public String drawLegend(){
        return "coins: "+ ANSI_YELLOW + "●" + ANSI_CYAN +" stones: "+ ANSI_GREY + "●" + ANSI_CYAN
                +" servants: " + ANSI_PURPLE + "●" + ANSI_CYAN +" shields: "+ ANSI_BLUE + "●" + ANSI_RESET;
    }

    /**
     * @param rows represents the structure of Marbles in the MarketTray
     * @param slideMarble represents the Marble which the Player has to move
     * @return a graphic representing MarketTray
     */
    public String drawMarketTray(List<List<Color>> rows, Color slideMarble) {
        StringBuilder builder = new StringBuilder();
        int counter = 5;
        builder.append("┏━━━━━━━━━━━━").append(marbleToAnsi(slideMarble)).append(" Slide Marble\n");
        for (List<Color> row : rows) {
            builder.append("┣");
            for (Color marble : row) {
                builder.append(marbleToAnsi(marble));
            }
            builder.append("◌ ← ").append(counter).append("\n");
            counter++;
        }
        builder.append("  ◌  ◌  ◌  ◌\n").append("  ↑  ↑  ↑  ↑\n").append("  1  2  3  4\n");
        return builder.toString();
    }

    /**
     * @return colored marble
     */
    private String marbleToAnsi(Color marble) {
        switch (marble) {
            case RED:
                return ANSI_RED + " ● " + ANSI_RESET;
            case BLUE:
                return ANSI_BLUE + " ● " + ANSI_RESET;
            case GREY:
                return ANSI_GREY + " ● " + ANSI_RESET;
            case WHITE:
                return " ● ";
            case YELLOW:
                return ANSI_YELLOW + " ● " + ANSI_RESET;
            case PURPLE:
                return ANSI_PURPLE + " ● " + ANSI_RESET;
            default:
                return null;
        }
    }

    /**
     * @param tray fancy string representing top DevelopmentCards in DevelopmentCardTray
     */
    public String drawDevCardTray(List<List<String>> tray) {
        StringBuilder builder = new StringBuilder();
        List<List<List<String>>> master = new ArrayList<>();

        for (int j = 0; j < 3; j++) {
            List<String> cost = new ArrayList<>();
            List<String> type = new ArrayList<>();
            List<String> level = new ArrayList<>();
            List<String> productionPowerInput = new ArrayList<>();
            List<String> productionPowerOutput = new ArrayList<>();
            List<String> victoryPoints = new ArrayList<>();
            List<String> allTaken = new ArrayList<>();
            List<List<String>> temp = new ArrayList<>();

            for (int i = j; i < 12; i = i + 3) {
                cost.add(tray.get(0).get(i));
                type.add(tray.get(1).get(i));
                level.add(tray.get(2).get(i));
                productionPowerInput.add(tray.get(3).get(i));
                productionPowerOutput.add(tray.get(4).get(i));
                victoryPoints.add(tray.get(5).get(i));
                allTaken.add(tray.get(6).get(i));
            }

            temp.add(cost);
            temp.add(type);
            temp.add(level);
            temp.add(productionPowerInput);
            temp.add(productionPowerOutput);
            temp.add(victoryPoints);
            temp.add(allTaken);
            master.add(temp);
            builder.append(drawPositionOfDevCard(j));
            builder.append(drawDevCardTrayRow(master.get(j))).append("\n");
        }

        return builder.toString();
    }

    /**
     * @param row represent each row of the set up of DevelopmentCardTray
     */
    private String drawDevCardTrayRow(List<List<String>> row) {

        List<String> cost = new ArrayList<>(row.get(0));
        List<String> type = new ArrayList<>(row.get(1));
        List<String> level = new ArrayList<>(row.get(2));
        List<String> productionPowerInput = new ArrayList<>(row.get(3));
        List<String> productionPowerOutput = new ArrayList<>(row.get(4));
        List<String> victoryPoints = new ArrayList<>(row.get(5));
        List<String> allTaken = new ArrayList<>(row.get(6));
        return  drawDevCardRowStatic("╭───────────────────────╮", type, allTaken) +
                drawDevCardRowType(level, type, allTaken) +
                drawDevCardRowStatic("│                       │", type, allTaken) +
                drawDevCardRowStatic("│         Cost:         │", type, allTaken) +
                drawDevCardRowResource(cost, type, allTaken) +
                drawDevCardRowStatic("│                       │", type, allTaken) +
                drawDevCardRowStatic("│   Production Power:   │", type, allTaken) +
                drawDevCardRowResource(productionPowerInput, type, allTaken) +
                drawDevCardRowStatic("│           ↓           │", type, allTaken) +
                drawDevCardRowResource(productionPowerOutput, type, allTaken) +
                drawDevCardRowStatic("│                       │", type, allTaken) +
                drawDevCardRowVictoryPoints(victoryPoints, type, allTaken) +
                drawDevCardRowStatic("╰───────────────────────╯", type, allTaken);
    }


    /**
     * @return a fancy string representing the parts of DevelopmentCards which are the same in every DevelopmentCard
     */
    private String drawDevCardRowStatic(String text, List<String> type, List<String> allTaken) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < type.size(); i++) {
            if (!Boolean.parseBoolean(allTaken.get(i))) builder.append(colorString(text, type.get(i))).append(gap);
            else builder.append(emptyCard).append(gap);
        }
        builder.append("\n");
        return builder.toString();
    }

    /**
     * @return a fancy string representing color and level in the same row of the card taking count of length of the words
     */
    private String drawDevCardRowType(List<String> level, List<String> type, List<String> allTaken) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < type.size(); i++) {
            if (!Boolean.parseBoolean(allTaken.get(i))) {
                builder.append(colorString("│  ", type.get(i))).append(colorString(type.get(i), type.get(i))).append(":  level ").append((level.get(i)));
                if (type.get(i).length() == 4) builder.append("       ");
                else if (type.get(i).length() == 5) builder.append("      ");
                else builder.append("     ");
                builder.append(colorString("│", type.get(i))).append(gap);
            }
            else builder.append(emptyCard).append(gap);
        }
        builder.append("\n");
        return builder.toString();

    }

    /**
     * @return a fancy string representing colored resources
     */
    private String drawDevCardRowResource(List<String> resource, List<String> type, List<String> allTaken) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < type.size(); i++) {
            if (!Boolean.parseBoolean(allTaken.get(i))) builder.append(colorString("│", type.get(i))).append(resource.get(i)).append(colorString("│", type.get(i))).append(gap);
            else builder.append(emptyCard).append(gap);
        }
        builder.append("\n");
        return builder.toString();
    }

    /**
     * @return a fancy string representing the part of the DevelopmentCard in which lay the value of Victory Points
     */
    private String drawDevCardRowVictoryPoints(List<String> victoryPoints, List<String> type, List<String> allTaken) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < type.size(); i++) {
            if (!Boolean.parseBoolean(allTaken.get(i))) {
                builder.append(colorString("│   ", type.get(i))).append(victoryPoints.get(i)).append(" Victory Points");
                if (victoryPoints.get(i).length() == 1) builder.append("    ");
                else builder.append("   ");
                builder.append(colorString("│", type.get(i))).append(gap);
            }
            else builder.append(emptyCard).append(gap);
        }
        builder.append("\n");
        return builder.toString();
    }

    private String colorString(String string, String color) {
        switch (color) {
            case "YELLOW":
                return ANSI_YELLOW + string + ANSI_RESET;
            case "GREEN":
                return ANSI_GREEN + string + ANSI_RESET;
            case "PURPLE":
                return ANSI_PURPLE + string + ANSI_RESET;
            case "GREY":
                return ANSI_GREY + string + ANSI_RESET;
            case "BLUE":
                return ANSI_BLUE + string + ANSI_RESET;
            case "RED":
                return ANSI_RED + string + ANSI_RESET;
            default:
                return string;
        }
    }

    private String drawPositionOfDevCard(int initial) {
        StringBuilder builder = new StringBuilder();
        for (int i = initial; i < 12; i = i + 3) {
            builder.append("     SLOT NUMBER ").append(i);
            if (i < 10) builder.append(" ");
            builder.append("      ").append(gap);

        }
        builder.append("\n");
        return builder.toString();
    }

    /**
     * @return a fancy string representing LeaderCards
     */
    public String drawLeadersRow(List<List<String>> leaders) {
        return  drawLeaderCardRowStatic("╭───────────────────────╮", leaders.size()) +
                drawLeaderCardRowStatic("│       Requires:       │", leaders.size()) +
                drawLeaderCardRowDynamic(leaders, 0, leaders.size()) +
                drawLeaderCardRowStatic("│                       │", leaders.size()) +
                drawLeaderCardRowStatic("│        Power:         │", leaders.size()) +
                drawLeaderCardRowDynamic(leaders, 1, leaders.size()) +
                drawLeaderCardRowDynamic(leaders, 2, leaders.size()) +
                drawLeaderCardRowDynamic(leaders, 3, leaders.size()) +
                drawLeaderCardRowDynamic(leaders, 4, leaders.size()) +
                drawLeaderCardRowStatic("│                       │", leaders.size()) +
                drawLeaderCardRowDynamic(leaders, 5, leaders.size()) +
                drawLeaderCardRowStatic("╰───────────────────────╯", leaders.size());
    }

    private String drawLeaderCardRowStatic(String text, int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(text).append(gap);
        }
        builder.append("\n");

        if(size>0){
            return builder.toString();
        }else return "";


    }
    private String drawLeaderCardRowDynamic(List<List<String>> leaders,int index,int size) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(leaders.get(i).get(index)).append(gap);
        }
        builder.append("\n");

        if(size > 0){
            return builder.toString();
        }else return "";
    }

    /**
     * @return a fancy string representing FaithTrack
     */
    public String drawFaithTrack(int faithMarkerPosition,List<Boolean> popeFavors){
        StringBuilder builder = new StringBuilder();
        builder.append("          ").append(colorString("│", "RED")).append("   ").append(drawFaithTrackPopeFavor(popeFavors.get(0))).append("   ").append(colorString("│", "RED")).append("     ").append(colorString("│", "RED")).append("    ").append(drawFaithTrackPopeFavor(popeFavors.get(1))).append("    ").append(colorString("│", "RED")).append("   ").append(colorString("│", "RED")).append("     ").append(drawFaithTrackPopeFavor(popeFavors.get(2))).append("     ").append(colorString("│", "RED")).append("\n");
        builder.append("          ").append(colorString("│", "RED")).append("Vatican").append(colorString("│", "RED")).append("     ").append(colorString("│", "RED")).append(" Vatican ").append(colorString("│", "RED")).append("   ").append(colorString("│", "RED")).append("  Vatican  ").append(colorString("│", "RED")).append("\n");
        builder.append("          ").append(colorString("│", "RED")).append("Report ").append(colorString("│", "RED")).append("     ").append(colorString("│", "RED")).append(" Report  ").append(colorString("│", "RED")).append("   ").append(colorString("│", "RED")).append("  Report   ").append(colorString("│", "RED")).append("\n");
        builder.append("          ").append(colorString("│", "RED")).append("  N°1  ").append(colorString("│", "RED")).append("     ").append(colorString("│", "RED")).append("   N°2   ").append(colorString("│", "RED")).append("   ").append(colorString("│", "RED")).append("    N°3    ").append(colorString("│", "RED")).append("\n");
        for (int i = 0; i < 25; i++) {
            if (i == faithMarkerPosition) builder.append("│").append(colorString("†","RED"));
            else builder.append("│_");
        }
        builder.append("│\n");
        builder.append(colorString("       1     2     4     6     9       12    16  20 :ExtraVictoryPoints\n","GREY"));
        builder.append(colorString("                 P               P               P  :Pope Spaces\n","GREY"));
        return builder.toString();
    }

    /**
     * @return a fancy string representing Lorenzo's FaithTrack
     */
    public String drawLorenzoFaithTrack(int blackCrossPosition){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            if (i == blackCrossPosition) builder.append("│").append(colorString("†","RED"));
            else builder.append("│_");
        }
        builder.append("│\n");
        return builder.toString();
    }

    private String drawFaithTrackPopeFavor(boolean isActive){
        if (isActive)return colorString("✓","GREEN");
        else return colorString("x","RED");
    }

    public String drawDevCardBoardSlots(List<List<String>> topCards, List<String> middleCards, List<String> bottomCards) {
        return "     SLOT NUMBER 1              SLOT NUMBER 2              SLOT NUMBER 3       \n" +
                drawDevCardTrayRow(topCards) +
                drawDevCardBoardSecondarySlots(middleCards) +
                drawDevCardBoardSecondarySlots(bottomCards);
    }

    public String drawLeadersProduction(List<List<String>> activeLeaders){
        List<List<String>> leaders = new ArrayList<>();
        for (List<String> activeLeader : activeLeaders) {
            if (activeLeader.get(6).equals("true") && activeLeader.get(1).equals("│       MOREPOWER       │")) leaders.add(activeLeader);
        }
        switch (leaders.size()){
            case 1:
                return "    LEADER PRODUCTION    \n"+drawLeadersRow(leaders);
            case 2:
                return "    LEADER PRODUCTION    "+gap+"    LEADER PRODUCTION    \n"+drawLeadersRow(leaders);
            default:
                return "";
        }
    }

    /**
     * @return a fancy string representing the margin of the DevelopmentCards underneath
     */
    private String drawDevCardBoardSecondarySlots(List<String> cards){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            if (cards.get(i).equals("")) builder.append("                         ").append(gap);
            else {
                builder.append("│   ").append(cards.get(i)).append(" Victory Points");
                if (cards.get(i).length() == 1) builder.append("    ");
                else builder.append("   ");
                builder.append("│").append(gap);
            }
        }
        builder.append("\n");
        for (int i = 0; i < 3; i++) {
            if (cards.get(i).equals("")) builder.append("                         ").append(gap);
            else builder.append("╰───────────────────────╯").append(gap);
        }
        builder.append("\n");
        return builder.toString();
    }

    /**
     * @param resources represent a list of resources read as Integers (0=Coins, 1=Stones, 2=Servants, 3=Shields)
     * @return a colored graphic corresponding to the color of the resource
     */
    private String integerToColor(List<Integer> resources){

        return (ANSI_YELLOW + "● " + ANSI_RESET).repeat(Math.max(0, resources.get(0))) +
                (ANSI_GREY + "● " + ANSI_RESET).repeat(Math.max(0, resources.get(1))) +
                (ANSI_PURPLE + "● " + ANSI_RESET).repeat(Math.max(0, resources.get(2))) +
                (ANSI_BLUE + "● " + ANSI_RESET).repeat(Math.max(0, resources.get(3)));
    }

    public String drawOwnedResources(List<List<Integer>> depots, List<Integer> strongbox){
        StringBuilder builder = new StringBuilder();

        builder.append("     Depots                     Strongbox");
        builder.append("\n");
        builder.append("   First Layer:     ").append(drawOwnedResourcesDepotsLayer(depots.get(0), 1)).append("              ").append(strongbox.get(0)).append(ANSI_YELLOW).append(" ● ").append(ANSI_RESET);
        builder.append("\n");
        builder.append("   Second Layer:   ").append(drawOwnedResourcesDepotsLayer(depots.get(1), 2)).append("             ").append(strongbox.get(1)).append(ANSI_GREY).append(" ● ").append(ANSI_RESET);
        builder.append("\n");
        builder.append("   Third Layer:   ").append(drawOwnedResourcesDepotsLayer(depots.get(2), 3)).append("            ").append(strongbox.get(2)).append(ANSI_PURPLE).append(" ● ").append(ANSI_RESET);
        builder.append("\n");
        builder.append("                                    ").append(strongbox.get(3)).append(ANSI_BLUE).append(" ● ").append(ANSI_RESET);
        builder.append("\n");
        if (depots.size() >= 4) {
            builder.append("   Extra Layer:    ").append(drawOwnedResourcesDepotsLayer(depots.get(3), 2));
            builder.append("\n");
        }
        if (depots.size() >= 5) {
            builder.append("   Extra Layer:    ").append(drawOwnedResourcesDepotsLayer(depots.get(4), 2));
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * @param resources represents a list of resources, position 0=Coins, 1=stones, 2=Servant and 3=Shields
     * @param deep represents the depot layer, if {@param deep}=1 the layer can contain 1 resource, if {@param deep}=2
     *             the layer will contain 2 resources and if {@param deep}=3 the layer can contain 3 resources
     * @return a graphic representing the Warehouse
     */
    private String drawOwnedResourcesDepotsLayer (List<Integer> resources, int deep){
        StringBuilder builder = new StringBuilder();
        int sum = resources.stream().mapToInt(Integer::intValue).sum();
        switch (deep){
            case 1:
                if (sum == 0) builder.append("- ");
                else builder.append(integerToColor(resources));
                break;
            case 2:
                if (sum == 0) builder.append("- - ");
                else if (sum == 1) builder.append(integerToColor(resources)).append("- ");
                else builder.append(integerToColor(resources));
                break;
            case 3:
                if (sum == 0) builder.append("- - - ");
                else if (sum == 1) builder.append(integerToColor(resources)).append("- - ");
                else if (sum == 2) builder.append(integerToColor(resources)).append("- ");
                else builder.append(integerToColor(resources));
                break;
            default:
                break;
        }
        return builder.toString();
    }

    /**
     * @return a drawing representing default production
     */
    public String drawDefaultProduction(){
        return "      * *\n" +
                "    *  ?  *\n" +
                "      * * \n" +
                "                           * *\n" +
                "                ⟹       *  ?  *\n" +
                "                           * *\n" +
                "      * *\n" +
                "    *  ?  *\n" +
                "      * *\n";
    }


}




