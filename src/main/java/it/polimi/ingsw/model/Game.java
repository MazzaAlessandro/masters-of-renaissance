package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.DevCardsTray;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.solo.SoloOpponent;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players = new ArrayList<>();
    private final List<Player> offlinePlayers = new ArrayList<>();
    private int maxPlayers;
    private GameState gameState;
    private final MarketTray marketTray;
    private SoloOpponent lorenzo;
    private final DevCardsTray devCardsTray;
    private final Deck deck;
    private StartStopStrategy gameStrategy;
    private final boolean[] vaticanReportTiles;

    public Game() {

        this.marketTray = new MarketTray();
        this.devCardsTray = new DevCardsTray();
        this.deck = new Deck();
        setGameState(GameState.LOGIN);
        vaticanReportTiles = new boolean[]{false, false, false};
    }

    public void init (int value){
        setMaxPlayers(value);
        setGameStrategy(value);
        createSoloOpponent(value);
    }

    public List<Player> getOfflinePlayers() {
        return offlinePlayers;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public boolean[] getVaticanReportTiles(){ return vaticanReportTiles; }

    public MarketTray getMarketTray() {
        return marketTray;
    }

    public SoloOpponent getLorenzo() {
        return lorenzo;
    }

    public DevCardsTray getDevCardsTray() {
        return devCardsTray;
    }

    public Deck getDeck() {
        return deck;
    }

    public Player getPlayer(int position) {
        return players.get(position);
    }

    public void addPlayers(Player player){
        this.players.add(player);
    }

    public StartStopStrategy getGameStrategy() {
        return gameStrategy;
    }

    public void setGameStrategy(StartStopStrategy gameStrategy){ this.gameStrategy = gameStrategy; }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public String getPlayerNickname(int position) {
        return players.get(position).getNickname();
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @param nickname to look for among those already taken
     * @return true if nickname already taken, else false
     */
    public boolean isNicknameTaken(String nickname) {
        for (int i = 0; i <= maxPlayers; i++) {
            if (getPlayerNickname(i).equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets strategy based on maxPlayers value
     */
    private void setGameStrategy(int value) {
        this.gameStrategy = (value > 1)
                ? new MoreThanOneStrategy()
                : new SoloStrategy();
    }

    /**
     * Sets to true the vaticanReportTile specified by @param vaticanReportNumber
     */
    public void setVaticanReportTileTrue(int vaticanReportNumber){
        this.vaticanReportTiles[vaticanReportNumber] = true;
    }

    /**
     * Create SoloOpponent if necessary
     */
    private void createSoloOpponent(int value) {
        this.lorenzo = (value > 1)
                ? null
                : new SoloOpponent(this.devCardsTray);
    }

    private void reorderPlayerList(){

    }

    public void startMatch(){
        gameStrategy.startMatch(this);
    }

    public void endGame(){
        gameStrategy.endGame(this);
    }

}
