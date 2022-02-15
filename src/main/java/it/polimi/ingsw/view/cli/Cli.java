package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.request.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.sender.Sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Cli implements View {

    Sender sender;
    BufferedReader reader;
    Graphics graphics = new Graphics();
    private String nickname;

    public Cli() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        print(graphics.getTitle());
        print(graphics.getWelcomeMessage());
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public void setDirectlySender(Sender sender) {
        this.sender = sender;
    }

    public void print(String string) {
        System.out.println(string);
    }

    /**
     * Print {@param string} with style for auxiliary information to understand what to write in cli
     */
    public void printSupport(String string) {
        print(Graphics.ITALIC + Graphics.ANSI_CYAN + string + Graphics.ANSI_RESET);
    }

    /**
     * Print {@param string} with style to report an input error or a regulation violation
     */
    public void printError(String string) {
        print(Graphics.ANSI_RED + string + Graphics.ANSI_RESET);
    }

    /**
     * Print {@param string} with style for Fancy Title
     */
    public void printTitle(String string) {
        print("\n" + Graphics.BOLD + Graphics.ANSI_BG_WHITE_BRIGHT + Graphics.ANSI_BLACK + "                                        ☞" + string + "☜                                                  " + Graphics.ANSI_RESET + "\n");
    }

    /**
     * Print {@param string} with style to update the player on the progress of other players
     */
    public void printUpdatePlayer(String string) {
        print(Graphics.ANSI_YELLOW_UNDERLINED + string + Graphics.ANSI_RESET);
    }

    /**
     * Print {@param string} with style to remember the resource color conversion
     */
    public void printLegend() {
        printSupport(graphics.drawLegend());
    }

    @Override
    public void updateDialog(String text) {
        print(text);
    }

    @Override
    public void updateAlert(String text) {
        printError(text);
    }

    public String read() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public void askNickname() {
        print("Enter a unique nickname");
        printSupport("Nickname cannot exceed 50 characters, spaces will be removed");
        String nickname = read();
        nickname = nickname.replaceAll("\\s+", "");
        if (nickname.length() > 0 && nickname.length() < 50) {
            this.sender.send(new LoginRequest(nickname));
            this.nickname = nickname;
        } else {
            printError("The nickname isn't valid, retry");
            askNickname();
        }
    }

    @Override
    public void askMaxPlayers() {
        int maxPlayerNumber;
        print("Enter a max player number");
        printSupport("The number must be between 1 and 4");
        String maxPlayer = read();
        switch (maxPlayer) {
            case "1":
            case "2":
            case "3":
            case "4":
                maxPlayerNumber = Integer.parseInt(maxPlayer);
                break;
            default:
                maxPlayerNumber = 0;
                break;
        }
        sender.send(new InitializeGameRequest(maxPlayerNumber, nickname));
    }

    @Override
    public void askWaitingRoom() {
        print("Type 1 if you want to create a new Match, type 2 to join an already existing match");
        String chosenAction = read();
        switch (chosenAction) {
            case "1":
                sender.send(new WaitingRoomRequest(nickname, 1000));//1000 represent a new Match
                break;
            case "2":
                askMatchId();
                break;
            default:
                sender.send(new WaitingRoomRequest(nickname, 1001));//1001 is default wrong value
                break;
        }
    }

    private void askMatchId() {
        print("Type the ID of the match you want to join");
        printSupport("The Id must be a number between 0 and 999");
        String chosenId = read();
        try {
            int id = Integer.parseInt(chosenId);
            if (id > 999 || id < 0) id = 1001;
            sender.send(new WaitingRoomRequest(nickname, id));
        } catch (NumberFormatException e) {
            sender.send(new WaitingRoomRequest(nickname, 1001));
        }
    }

    @Override
    public void askWaitForOtherPlayers() {
        printUpdatePlayer("Wait for other Players to start the Game!");
    }

    @Override
    public void askWaitForYourTurn() {
        printUpdatePlayer("Wait for Your turn!");
    }

    @Override
    public void askIsYourTurn() {
        printUpdatePlayer("Is Your turn!");
    }

    @Override
    public void askEndGame() {
        print("The game ended. You are going to be disconnected from the server in a moment");
        sender.send(new EndGameRequest(nickname, 2));
    }

    /**
     * Convert read integer input in a color and add it to {@param colors}
     */
    private void typeResources(List<Color> colors) {
        printSupport("Type 1 for coin, 2 for stone, 3 for servant or 4 for shield");
        String input = read();
        switch (input) {
            case "1":
                colors.add(Color.YELLOW);
                break;

            case "2":
                colors.add(Color.GREY);
                break;

            case "3":
                colors.add(Color.PURPLE);
                break;

            case "4":
                colors.add(Color.BLUE);
                break;

            default:
                colors.add(Color.WHITE);
                break;
        }
    }

    @Override
    public void askStartingResources(int resourceAmount) {
        List<Color> chosenResources = new ArrayList<>();
        if (resourceAmount > 0) {
            print("Choose one resource as your starting Resource");
            typeResources(chosenResources);
        }
        if (resourceAmount == 2) {
            print("Choose another resource as your starting Resource");
            typeResources(chosenResources);
        }
        if (chosenResources.contains(Color.WHITE) || chosenResources.contains(Color.RED) || chosenResources.contains(Color.GREEN)) {
            printError("Your choice was invalid, please retry");
            askStartingResources(resourceAmount);
        }
        sender.send(new StartingResourcesRequest(nickname, chosenResources));
    }

    /**
     * Convert string {@param in} in safe Integer list
     */
    private List<Integer> selectedLeadersToList(String in) {
        List<Integer> out = new ArrayList<>();
        switch (in) {
            case "":
                out.add(0);
                break;
            case "1":
                out.add(1);
                break;
            case "2":
                out.add(2);
                break;
            case "1,2":
            case "2,1":
                out.add(2);
                out.add(1);
                break;
            default:
                break;
        }
        return out;
    }

    @Override
    public void askStartingDiscardLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        sender.showRequest(new ShowLeadersRequest(nickname));
        List<Integer> choose = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            print("Select leader to discard ");
            printSupport("Type 1 for the first card, 2 for the second, 3 for the third, 4 for the fourth");
            switch (read()) {
                case "1":
                    choose.add(0);
                    break;
                case "2":
                    choose.add(1);
                    break;
                case "3":
                    choose.add(2);
                    break;
                case "4":
                    choose.add(3);
                    break;
                default:
                    printError("Invalid Input! Retry");
                    i--;
                    break;
            }
        }
        sender.send(new StartingDiscardLeaderRequest(nickname, choose));
    }

    @Override
    public void askToActiveLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        print("Type the leaders you want to active.");
        sender.showRequest(new ShowLeadersRequest(nickname));
        printSupport("Type 1 if you want to activate first LeaderCard,\ntype 2 if you want to activate the second one,\n" +
                "type 1,2 or 2,1 if you want to activate both leaderCards.\nIf you want to skip active press enter.");
        String input = read();
        sender.send(new ActivateLeadersRequest(nickname, selectedLeadersToList(input)));
    }

    @Override
    public void askToDiscardLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        print("Type the leaders you want to discard.");
        sender.showRequest(new ShowLeadersRequest(nickname));
        printSupport("Type 1 if you want to discard first LeaderCard,\ntype 2 if you want to discard the second one,\n" +
                "type 1,2 or 2,1 if you want to discard both leaderCards\nIf you want to skip discard press enter.");
        String input = read();
        sender.send(new DiscardLeadersRequest(nickname, selectedLeadersToList(input)));
    }

    @Override
    public void askTurnAction() {
        printTitle("     BOARD      ");
        sender.showRequest(new ShowFaithTrackRequest(nickname));
        sender.showRequest(new ShowOwnedResourcesRequest(nickname));
        sender.showRequest(new ShowDevCardBoardSlotsRequest(nickname));
        print("Type 1 to Take Resources from the Market, 2 to Buy one Development Card and 3 to Activate the Production.");
        String input = read();
        int position = 0;
        try {
            position = Integer.parseInt(input);
            sender.send(new TurnActionRequest(nickname, position));
        } catch (NumberFormatException e) {
            sender.send(new TurnActionRequest(nickname, position));
        }
    }

    @Override
    public void showMarketTray(List<List<Color>> rows, Color slideMarble) {
        printLegend();
        print(graphics.drawMarketTray(rows, slideMarble));
    }

    @Override
    public void askPositionForMarket(List<List<Color>> rows, Color slideMarble) {
        sender.showRequest(new ShowMarketTrayRequest(nickname));
        int positionNumber;
        print("Select the position. Type 'undo' to return in Select Action");
        String position = read();
        switch (position) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
                positionNumber = Integer.parseInt(position);
                break;
            case "undo":
                printError("No position was chosen, going back to selecting turn action.");
                askTurnAction();
                return;
            default:
                positionNumber = 8; //default wrong value
                break;
        }
        sender.send(new MarketPositionRequest(nickname, positionNumber));
    }

    @Override
    public void askNumberOfWhiteToConvert(List<Color> leadersColor, List<Color> takenColor) {
        List<Integer> outputList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            print("Select number of white to convert in " + leadersColor.get(i));
            printSupport("Selected number has to be between 0 and 4");
            String read = read();
            switch (read) {
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                    outputList.add(Integer.parseInt(read));
                    break;
                default:
                    outputList.add(5); //default wrong value
                    break;
            }
        }
        sender.send(new MarketLeadersRequest(nickname, outputList, takenColor));
    }

    @Override
    public void askDevelopmentCardInTray(List<List<String>> tray) {
        sender.showRequest(new ShowDevCardTrayRequest(nickname));
        int positionInt;
        print("Choose one DevCard in tray by position. Type 'undo' to return in Select Action");
        String positionStr = read();
        switch (positionStr) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "10":
            case "11":
                positionInt = Integer.parseInt(positionStr);
                break;
            case "undo":
                printError("No position was chosen, going back to selecting turn action.");
                askTurnAction();
                return;
            default:
                positionInt = 12; //default wrong value
                break;
        }
        sender.send(new BuyDevInTrayPositionRequest(nickname, positionInt));
    }

    @Override
    public void askDevelopmentCardSlot() {
        int positionInt;
        sender.showRequest(new ShowDevCardBoardSlotsRequest(nickname));
        print("Choose one slot for store DevCard");
        printSupport("Selected number needs to be between 1 and 3");
        String positionStr = read();
        switch (positionStr) {
            case "1":
            case "2":
            case "3":
                positionInt = Integer.parseInt(positionStr);
                break;
            default:
                positionInt = 0; //default wrong value
                break;
        }
        sender.send(new BuyDevSlotPositionRequest(nickname, positionInt));
    }

    @Override
    public void askProductionToActivate(List<Integer> productionPosition, List<List<String>> activeLeaders, List<List<String>> devCards) {
        printTitle(" Productions ");
        print("Default Production");
        print(graphics.drawDefaultProduction());
        sender.showRequest(new ShowDevCardBoardSlotsRequest(nickname));
        sender.showRequest(new ShowLeadersProductionRequest(nickname));
        print("Choose the productions you want to activate");
        printSupport("Selected number needs to be between 0 and 5\nProduction marked with 0 correspond to default production\n" +
                "while productions 4 and 5 are leaderCards special ability production\nType 'alt' to stop selection. Type 'undo' to return in Select Action");
        printSupport("List of possible Productions: " + productionPosition.toString());
        List<Integer> chosen = new ArrayList<>();
        for (int i = 0; i < productionPosition.size(); i++) {
            String inputLine = read();
            switch (inputLine) {
                case "0":
                    chosen.add(0);
                    break;
                case "1":
                    chosen.add(1);
                    break;
                case "2":
                    chosen.add(2);
                    break;
                case "3":
                    chosen.add(3);
                    break;
                case "4":
                    chosen.add(4);
                    break;
                case "5":
                    chosen.add(5);
                    break;
                case "undo":
                    printError("No production was chosen, going back to selecting turn action.");
                    askTurnAction();
                    return;
                case "alt":
                    i = 10; //interrupts selection
                    break;
                default:
                    chosen.add(6); //default wrong value
                    break;
            }
        }
        sender.send(new ActivateProductionRequest(nickname, chosen, productionPosition));
    }

    @Override
    public void askDefaultProductionInput() {
        List<Color> colors = new ArrayList<>();
        print("Choose the first input resource for default production");
        typeResources(colors);
        print("Choose the second input resource for default production");
        typeResources(colors);
        sender.send(new DefaultProductionInputRequest(nickname, colors));
    }

    @Override
    public void askDefaultProductionOutput() {
        List<Color> colors = new ArrayList<>();
        print("Choose the output resource for default production");
        typeResources(colors);
        sender.send(new DefaultProductionOutputRequest(nickname, colors));
    }

    @Override
    public void askLeaderProductionOutput(List<Integer> selectedLeaders) {
        List<Color> colors = new ArrayList<>();
        for (Integer ignored : selectedLeaders) {
            print("Choose the output resource for Leader production");
            typeResources(colors);
        }
        sender.send(new LeaderProductionRequest(nickname, colors, selectedLeaders));
    }

    @Override
    public void askResourcesToDiscard(List<Integer> resources) {
        List<Integer> discard = new ArrayList<>() {{
            add(0);
            add(0);
            add(0);
            add(0);
        }};
        List<String> nameIndex = new ArrayList<>() {{
            add("Coins");
            add("Stones");
            add("Servants");
            add("Shields");
        }};

        for (int i = 0; i < resources.size(); i++) {
            if (resources.get(i) > 0) {
                int j;
                print("How much " + nameIndex.get(i) + " do you want to discard?");
                try {
                    j = Integer.parseInt(read());
                } catch (NumberFormatException e) {
                    j = 12;
                }
                discard.set(i, j);
            }
        }
        sender.send(new MarketDepotsRequest(nickname, resources, discard));
    }

    @Override
    public void askVaticanReportActive(int vaticanReportNumber) {
        vaticanReportNumber++;
        printSupport("The Vatican Report " + vaticanReportNumber + " occurred");
    }


    @Override
    public void showDevCardTray(List<List<String>> tray) {
        printTitle("      Tray      ");
        printSupport("Choose a number between 0 and 11, slot number is placed above every card");
        printLegend();
        print(graphics.drawDevCardTray(tray));
    }

    @Override
    public void showLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {

        if (activeLeaders.size() > 0) {
            printTitle(" Active Leaders ");
            printLegend();
            print(graphics.drawLeadersRow(activeLeaders));
        }

        if (inactiveLeaders.size() > 0) {
            printTitle("Inactive Leaders");
            printLegend();
            print(graphics.drawLeadersRow(inactiveLeaders));
        }
    }

    @Override
    public void showFaithTrack(int faithMarkerPosition, List<Boolean> popeFavors) {
        printTitle("   FaithTrack   ");
        print(graphics.drawFaithTrack(faithMarkerPosition, popeFavors));
    }

    @Override
    public void showLorenzoFaithTrack(int blackCrossPosition, String actionToken) {
        printTitle("    Lorenzo     ");
        print("Faith Track:");
        print(graphics.drawLorenzoFaithTrack(blackCrossPosition));
        print(actionToken);
    }

    @Override
    public void showDevCardBoardSlots(List<List<String>> topCards, List<String> middleCards, List<String> bottomCards) {
        printTitle("  DevCard Slots ");
        print(graphics.drawDevCardBoardSlots(topCards, middleCards, bottomCards));
    }

    @Override
    public void showOwnedResources(List<List<Integer>> depots, List<Integer> strongBox) {
        printTitle("Owned Resources");
        printLegend();
        print(graphics.drawOwnedResources(depots, strongBox));
    }

    @Override
    public void showLeadersProduction(List<List<String>> activeLeaders) {
        print(graphics.drawLeadersProduction(activeLeaders));
    }

    @Override
    public void showOtherPlayer(List<String> players) {
        Server.LOG.warning("Achieved unreachable state");
    }

    @Override
    public void showOtherPlayerLeaders(List<List<String>> activeLeaders, List<List<String>> inactiveLeaders) {
        Server.LOG.warning("Achieved unreachable state");
    }

    @Override
    public void showOtherFaithTrack(int faithMarkerPosition, List<Boolean> popeFavors) {
        Server.LOG.warning("Achieved unreachable state");
    }

    @Override
    public void showOtherDevCardBoardSlots(List<List<String>> topCards, List<String> middleCards, List<String> bottomCards) {
        Server.LOG.warning("Achieved unreachable state");
    }

    @Override
    public void showOtherOwnedResources(List<List<Integer>> depots, List<Integer> strongBox) {
        Server.LOG.warning("Achieved unreachable state");
    }


}
