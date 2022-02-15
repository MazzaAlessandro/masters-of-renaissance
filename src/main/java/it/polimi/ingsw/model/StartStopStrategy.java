package it.polimi.ingsw.model;

public interface StartStopStrategy {
    public void startMatch(Game currentGame);
    public void endGame(Game currentGame);
}
