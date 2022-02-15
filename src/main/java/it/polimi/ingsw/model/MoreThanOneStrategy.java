package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;

import java.util.Collections;

public class MoreThanOneStrategy implements StartStopStrategy{
    @Override
    public void startMatch(Game currentGame) {
        Collections.shuffle(currentGame.getPlayers());
        for (int i = 0; i < 4; i++) {
            currentGame.getPlayers().forEach((player) -> player.getBoard().getLeaderCards().add(currentGame.getDeck().pickCard()));
        }
        for (Player player: currentGame.getPlayers()){
            player.setPlayerOrder(currentGame.getPlayers().indexOf(player));
        }
    }

    @Override
    public void endGame(Game currentGame) {

    }
}
