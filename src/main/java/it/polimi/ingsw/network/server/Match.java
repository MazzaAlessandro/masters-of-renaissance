package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;

/**
 * Identifies the match with an Id and its gameController.
 * The matchId is unique to the Match since it's based on an increasing static counter
 */
public class Match {

    private static int matchIdCounter = 0;
    private final GameController gameController;
    private final int matchId;

    public GameController getGameController() {
        return gameController;
    }

    public int getMatchId() {
        return matchId;
    }

    public Match(){
        matchId = matchIdCounter;
        matchIdCounter++;
        this.gameController = new GameController();
    }

}
