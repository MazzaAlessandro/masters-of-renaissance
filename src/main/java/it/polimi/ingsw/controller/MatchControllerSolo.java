package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.enumerations.PlayerState;
import it.polimi.ingsw.model.solo.SoloOpponent;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.VirtualView;

/**
 * A subclass of MatchController, used if the {@param game} has only one player.
 * It handles Lorenzo's move at the end of each turn
 */
public class MatchControllerSolo extends MatchController {
    public MatchControllerSolo(ValidatorController validator,VirtualView virtualView, Game game) {
        super(validator, virtualView, game);
    }

    /**
     * Starts a new turn for the player
     */
    @Override
    public void wakeUp() {
        super.getVirtualView().setReceiver(super.getCurrentPlayer().getNickname());
        super.getCurrentPlayer().setState(PlayerState.ACTIVE);
        if (super.getCurrentPlayer().isFirstTurn()){
            super.getCurrentPlayer().setFirstTurn(false);
            super.getVirtualView().askStartingDiscardLeaders(getCurrentPlayer().getBoard().getActiveLeadersFancyCli(), getCurrentPlayer().getBoard().getInactiveLeadersFancyCli());
        }
        else super.useOrDiscardLeader();
    }

    /**
     * At the end of the player's turn, it handles Lorenzo's moves, checking if the player either lost or won
     */
    @Override
    public void nextTurn(){
        if (super.getGame().getPlayers().size()==0){
            super.getGame().setGameState(GameState.WAIT);
        }
        else {
            super.getCurrentPlayer().setState(PlayerState.WAIT);
            Server.LOG.info(super.getGame().getLorenzo().toString());
            String action = pickActionTokens(super.getGame().getLorenzo());
            Server.LOG.info(super.getGame().getLorenzo().toString());
            //super.getVirtualView().updateDialog(action);
            super.getVirtualView().showLorenzoFaithTrack(super.getGame().getLorenzo().getBlackCross(), action);
            if (super.getValidator().isVaticanReportTriggeredSolo(super.getGame().getPlayers().get(0), super.getGame().getLorenzo(), super.getGame().getVaticanReportTiles()))
                vaticanReport();
            if (super.getValidator().isOpponentEndingFaithTrack(super.getGame().getLorenzo()) || super.getValidator().isOneDevelopmentCardColorFinished(super.getGame().getDevCardsTray()))
                endMatch("lose");
            else if (super.getValidator().isMatchEnded(super.getCurrentPlayer())) endMatch("win");
            else wakeUp();
        }
    }

    /**
     * Activates {@param lorenzo}'s actions at the end of the turn
     */
    public String pickActionTokens(SoloOpponent lorenzo){
        return lorenzo.pickActionTokenFancyCli();
    }

    /**
     * Estimates the player's points and ends the match
     * @param result is either "win" or "lose", based on the match's results
     */
    public void endMatch(String result){
        super.getVirtualView().updateDialog("You " + result + "! You scored " + super.getCurrentPlayer().getBoard().getVictoryPoints() + " points!");
        super.getGame().setGameState(GameState.END);
        endGame();
    }

}
