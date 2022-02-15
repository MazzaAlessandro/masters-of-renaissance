package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.enumerations.PlayerState;

/**
 * Holds all the information linked to a player
 */
public class Player implements Comparable<Player>{

    private boolean isFirstTurn = true;
    private boolean isConnected;
    private String nickname;
    private Board board;
    private PlayerState state;
    private int playerOrder;

    /**
     * Player constructor that sets the nickname and the order of the player. It also creates the board and set the state to WAIT
     * @param nickname is the given nickname
     * @param playerOrder is the player's position in the list
     */
    public Player(String nickname, int playerOrder) {
        this.nickname = nickname;
        this.board = new Board();
        this.playerOrder = playerOrder;
        this.isConnected = true;
        setState(PlayerState.WAIT);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isFirstTurn() {
        return isFirstTurn;
    }

    public void setFirstTurn(boolean firstTurn) {
        isFirstTurn = firstTurn;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayerOrder(int playerOrder) {
        this.playerOrder = playerOrder;
    }

    public PlayerState getState() {
        return state;
    }

    public int getPlayerOrder() {
        return playerOrder;
    }

    public Board getBoard() {
        return board;
    }


    @Override
    public String toString() {
        return "{\"Player\":{"
                + "\"isFirstTurn\":\"" + isFirstTurn + "\""
                + ",\"nickname\":\"" + nickname + "\""
                + ",\"state\":\"" + state + "\""
                + ",\"playerOrder\":\"" + playerOrder + "\""
                + "}}";
    }

    @Override
    public int compareTo(Player o) {
        Integer order = this.getPlayerOrder();
        return order.compareTo(o.playerOrder);
    }
}
