package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.reply.DialogReply;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.VirtualView;

/**
 * A subclass of MatchController, used if the {@param game} has more than one player.
 * It handles the player turns cycle
 */
public class MatchControllerMulti extends MatchController{
    private int currentPlayerPosition;
    private Player currentPlayer;
    private Player winner;
    private final Game game;
    private final VirtualView virtualView;

    public MatchControllerMulti(ValidatorController validator,VirtualView virtualView, Game game) {
        super(validator, virtualView,game);
        this.game = game;
        this.virtualView = virtualView;
        this.currentPlayerPosition = 0;
    }

    /**
     * Sets up everything to start the new turn.
     * If this is the first turn of the {@param currentPlayer}, the setStartingResources() method is called
     */
    @Override
    public void wakeUp(){
        currentPlayer = super.getCurrentPlayer();
        currentPlayerPosition = game.getPlayers().indexOf(currentPlayer);
        currentPlayer.setState(PlayerState.ACTIVE);
        virtualView.setReceiver(currentPlayer.getNickname());
        Server.LOG.info("Turn of "+ currentPlayer.getNickname());
        virtualView.askIsYourTurn();
        if (currentPlayer.isFirstTurn()) setStartingResources();
        else super.useOrDiscardLeader();
    }

    /**
     * Gives the extra Resources or Faith points to {@param currentPlayer} if this is the first turn
     */
    public void setStartingResources(){
        //getCurrentPlayer().getBoard().getStrongBox().add(new Resources(100,100,100,100));
        switch (currentPlayer.getPlayerOrder()){
            case 0:
                virtualView.updateDialog("You are first!");
                virtualView.askStartingDiscardLeaders(getCurrentPlayer().getBoard().getActiveLeadersFancyCli(), getCurrentPlayer().getBoard().getInactiveLeadersFancyCli());
                break;
            case 1:
                virtualView.updateDialog("You are second!");
                virtualView.askStartingResources(1);
                break;
            case 2:
                currentPlayer.getBoard().increaseFaith(1);
                virtualView.updateDialog("You are third!");
                virtualView.askStartingResources(1);
                break;
            case 3:
                currentPlayer.getBoard().increaseFaith(1);
                virtualView.updateDialog("You are fourth!");
                virtualView.askStartingResources(2);
                break;
        }
    }

    /**
     * After the {@param currentPlayer} ended his turn, checks if the match has ended.
     * If nobody has won yet, it sets up a new {@param currentPlayer} and calls wakeUp() to start his turn
     */
    @Override
    public void  nextTurn(){
        if (game.getPlayers().size()==0){
            game.setGameState(GameState.WAIT);
        }
        else {
            if (super.getValidator().isVaticanReportTriggered(game.getPlayers(), game.getVaticanReportTiles()))
                vaticanReport();
            virtualView.updateDialog("Your turn just ended");
            currentPlayer.setState(PlayerState.WAIT);
            if (getValidator().isMatchEnded(game.getPlayers()) && currentPlayer.equals(game.getPlayer(game.getPlayers().size() - 1)))
                endMatch();
            else {
                virtualView.askWaitForYourTurn();
                if (game.getOfflinePlayers().contains(currentPlayer)){
                    if (currentPlayer.getPlayerOrder() >= game.getPlayers().size()) super.setCurrentPlayer(game.getPlayer(0));
                    else super.setCurrentPlayer(game.getPlayer(currentPlayerPosition));
                }
                if (game.getPlayers().indexOf(currentPlayer) == game.getPlayers().size() - 1) super.setCurrentPlayer(game.getPlayer(0));
                else super.setCurrentPlayer(game.getPlayer(game.getPlayers().indexOf(currentPlayer) + 1));
                wakeUp();
            }
        }
    }

    /**
     * At the end of the match, it calculates each player's point and declares the winner
     */
    public void endMatch(){
        int highestScore = 0;
        virtualView.broadcast(new DialogReply("The match has ended.", null), super.getMatchId());
        for(Player player: game.getPlayers()){
            if (player.getBoard().getVictoryPoints() > highestScore){
                winner = player;
                highestScore = player.getBoard().getVictoryPoints();
            }
            virtualView.broadcast(new DialogReply(player.getNickname() + " scored " + player.getBoard().getVictoryPoints() + " points!", null), super.getMatchId());
        }
        virtualView.broadcast(new DialogReply(winner.getNickname() + " wins!", null), super.getMatchId());
        game.setGameState(GameState.END);
        endGame();
    }

}
