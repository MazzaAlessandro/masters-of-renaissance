package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.MatchController;
import it.polimi.ingsw.controller.MatchControllerMulti;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.MoreThanOneStrategy;
import it.polimi.ingsw.model.enumerations.GameState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;
import it.polimi.ingsw.network.message.reply.*;
import it.polimi.ingsw.network.message.request.EndGameRequest;
import it.polimi.ingsw.network.message.request.WaitingRoomRequest;
import it.polimi.ingsw.util.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;

/**
 * Handles the communication between server and clients
 * Send reply to client and forwards requests to GameController through the Observer pattern
 */
public class ClientHandlerMultiGame extends Observable implements ClientHandler {
    private final ServerMultiGame server;
    private final Socket clientSocket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final Object mutexIn; //sync request
    private final Object mutexOut;//sync reply
    private String nickname;
    private int matchId;

    public ClientHandlerMultiGame(ServerMultiGame server, Socket socket, ObjectOutputStream out, ObjectInputStream in) {
        this.server = server;
        this.clientSocket = socket;
        this.out = out;
        this.in = in;
        this.mutexIn = new Object();
        this.mutexOut = new Object();
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void send(Message message) {
        synchronized (mutexOut) {
            try {
                Server.LOG.info(message.toString());
                out.writeObject(message);
                out.reset();
            } catch (IOException e) {
                Server.LOG.warning("Can't send message");
            }
        }
    }

    @Override
    public void disconnect() {
        try {
            server.broadcast(new DialogReply(nickname + " disconnected from the server",null),matchId);
            for (Match match: server.getMatches()) {
                if (matchId == match.getMatchId()){
                    for (Player player: match.getGameController().getGame().getPlayers()) {
                        if (player.getNickname().equals(nickname)){
                            player.setConnected(false);
                            server.getClients().remove(server.nicknameToClientHandler(player.getNickname()));
                            match.getGameController().getGame().getOfflinePlayers().add(player);
                            match.getGameController().getGame().getPlayers().remove(player);
                            if (match.getGameController().getGame().getGameState().equals(GameState.IN_GAME) && player == match.getGameController().getMatchController().getCurrentPlayer()) match.getGameController().getMatchController().nextTurn();
                            break;
                        }
                    }
                }
            }
            this.clientSocket.close();
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            Server.LOG.warning("Can't disconnect the it.polimi.ingsw.Client");
        }
    }

    @Override
    public void run() {
        Server.LOG.info("New it.polimi.ingsw.Client Access to Server");
        send(new LoginReply(false));
        while (!Thread.currentThread().isInterrupted()) { //stop loop if thread was killed
            synchronized (mutexIn) {
                try {
                    Message message = (Message) in.readObject();
                    if (message != null && message.getType() != Type.PING) { //filter ping
                        Server.LOG.info(message.toString());
                        switch (message.getType()) {
                            case LOGIN:
                                loginBroker(message);
                                break;
                            case WAITINGROOM:
                                waitingRoomBroker(message);
                                break;
                            case ENDGAME:
                                endGameBroker(message);
                                break;
                            default:
                                updateAllObserver(message);
                                break;
                        }

                    }
                } catch (IOException | ClassNotFoundException | ClassCastException e) {
                    Server.LOG.info("it.polimi.ingsw.Client" + clientSocket.getInetAddress() + " close connection");
                    disconnect();
                }
            }
        }
    }

    /**
     * Sets the player's nickname. If the nickname is taken, a new LoginReply is sent
     * @param message is the message received
     */
    private void loginBroker(Message message) {
        if (!server.isNicknameTaken(message.getSender()) && message.getSender() != null) {
            this.nickname = message.getSender();
            Server.LOG.info("Add " + this.nickname + " in players list");
            send(new WaitingRoomReply());
        } else {
            send(new AlertReply("Nickname already taken!"));
            send(new LoginReply(false));
        }

    }

    /**
     * Either creates a new match or lets the player join an already existing match.
     * It handles reconnections and message limitations (trying to join an non-existing match, a full match or an ended match)
     * @param message is a WaitingRoomRequest
     */
    private void waitingRoomBroker(Message message) {
        WaitingRoomRequest waitingRoomRequest = (WaitingRoomRequest) message;
        switch (waitingRoomRequest.getChosenAction()) {
            case 1001://1001 is given as a token "wrong" message
                send(new AlertReply("Invalid input, please try again!"));
                send(new WaitingRoomReply());
                break;
            case 1000://creates a new match
                Match match = new Match();
                matchId = match.getMatchId();
                match.getGameController().setMatchId(match.getMatchId());
                match.getGameController().setVirtualView(server.getVirtualView());
                match.getGameController().getGame().addPlayers(new Player(nickname, 0));
                this.addObserver(match.getGameController());
                server.getMatches().add(match);
                send(new DialogReply("You created a new match! Your matchId is " + match.getMatchId(), null));
                send(new InitializeGameReply());
                break;
            default://tries to join a match with the given Id
                boolean matchFound = false;
                boolean canJoin = false;
                boolean wasOffline = false;
                for (Match matchIndex : server.getMatches()) {
                    Game matchGame = matchIndex.getGameController().getGame();

                    if (matchIndex.getMatchId() == waitingRoomRequest.getChosenAction()) {
                        for (Player offlinePlayers : matchGame.getOfflinePlayers()){
                            if (offlinePlayers.getNickname().equals(nickname)) {
                                wasOffline = true;
                                break;
                            }
                        }
                        matchFound = true;
                        if (!wasOffline && matchGame.getPlayers().size() + matchGame.getOfflinePlayers().size() < matchIndex.getGameController().getMaxPlayers()) {
                            matchGame.getPlayers().add(new Player(nickname, 0));
                            this.addObserver(matchIndex.getGameController());
                            if (matchGame.getPlayers().size() == matchIndex.getGameController().getMaxPlayers()) {
                                startMatch(matchIndex, matchGame);
                            }
                            else{
                                send(new WaitForOtherPlayersReply());
                            }
                        }
                        else if (matchGame.getOfflinePlayers().size() > 0){
                            for(Player offlinePlayer : matchGame.getOfflinePlayers()){
                                if(offlinePlayer.getNickname().equals(nickname)){
                                    canJoin = true;
                                    if (matchGame.getGameState().equals(GameState.END)){
                                        send(new AlertReply("The match already ended"));
                                        send(new WaitingRoomReply());
                                        break;
                                    }
                                    matchGame.getPlayers().add(offlinePlayer);
                                    Collections.sort(matchGame.getPlayers());
                                    //matchGame.getPlayer(matchGame.getOfflinePlayers().indexOf(offlinePlayer)).setConnected(true);
                                    this.addObserver(matchIndex.getGameController());
                                    matchGame.getOfflinePlayers().remove(offlinePlayer);
                                    if(matchGame.getGameState().equals(GameState.WAIT)){
                                        //send(new DialogReply("Waiting for all players to rejoin the match...", null));
                                        send(new WaitForOtherPlayersReply());
                                        if(matchGame.getOfflinePlayers().size() == 0) {
                                            server.getVirtualView().broadcast(new DialogReply("All players are back!", null), matchIndex.getMatchId());
                                            matchGame.setGameState(GameState.IN_GAME);
                                            matchIndex.getGameController().getMatchController().setCurrentPlayer(matchGame.getPlayers().get(0));
                                            for(Player player: matchGame.getPlayers()){
                                                if(!player.equals(matchIndex.getGameController().getMatchController().getCurrentPlayer())){
                                                    matchIndex.getGameController().getVirtualView().setReceiver(player.getNickname());
                                                    matchIndex.getGameController().getVirtualView().askWaitForYourTurn();
                                                }
                                            }
                                            matchIndex.getGameController().getMatchController().wakeUp();
                                        }
                                    }
                                    else if (matchGame.getGameState().equals(GameState.LOGIN)) {
                                        if(matchGame.getPlayers().size() == matchIndex.getGameController().getMaxPlayers()) {
                                            startMatch(matchIndex, matchGame);
                                        }
                                        else send(new WaitForOtherPlayersReply());
                                    }
                                    else send(new WaitForYourTurnReply());
                                    break;
                                }
                            }
                            if(!canJoin){
                                send(new AlertReply("Sorry, you can't join this game!"));
                                send(new WaitingRoomReply());
                            }
                        }
                        else {
                            send(new AlertReply("The match is already full, please try again!"));
                            send(new WaitingRoomReply());
                        }
                        break;
                    }
                }
                if (!matchFound) {
                    send(new AlertReply("Id not found, please try again!"));
                    send(new WaitingRoomReply());
                }
                break;
        }
    }

    /**
     * Sets up everything needed to start a new match as soon as all players are online
     * @param matchIndex is the match that needs to be started
     * @param matchGame is the Game linked to the match
     */
    private void startMatch(Match matchIndex, Game matchGame){
        matchIndex.getGameController().setMatchController(new MatchControllerMulti(matchIndex.getGameController().getValidator(), server.getVirtualView(), matchGame));
        MatchController matchController = matchIndex.getGameController().getMatchController();
        matchGame.setGameStrategy(new MoreThanOneStrategy());
        matchGame.startMatch();
        matchId = matchIndex.getMatchId();
        matchController.setMatchId(matchIndex.getMatchId());
        server.getVirtualView().broadcast(new DialogReply("All players joined!", null), matchIndex.getMatchId());
        matchController.setCurrentPlayer(matchGame.getPlayers().get(0));
        matchGame.setGameState(GameState.IN_GAME);
        for(Player player: matchGame.getPlayers()){
            if(!player.equals(matchController.getCurrentPlayer())){
                matchIndex.getGameController().getVirtualView().setReceiver(player.getNickname());
                matchIndex.getGameController().getVirtualView().askWaitForYourTurn();
            }
        }
        matchController.wakeUp();
    }

    /**
     * Removes all players from the ended match and either puts a player into the WaitingRoom or disconnects him, based on what he chose.
     * When the last one is removed, the match is destroyed
     * @param message is an EndGameRequest
     */
    private void endGameBroker(Message message){
        boolean isGameEmpty = false;
        EndGameRequest endGameRequest = (EndGameRequest) message;
        for (Match match: server.getMatches()){
            if (matchId == match.getMatchId()){
                this.removeObserver(match.getGameController());
                for (Player player : match.getGameController().getGame().getPlayers()) {
                    if (player.getNickname().equals(nickname)) match.getGameController().getGame().getPlayers().remove(player);
                    break;
                }
                if(match.getGameController().getGame().getPlayers().size()==0) isGameEmpty = true;
            }
        }
        if (isGameEmpty) ServerMultiGame.removeMatch(matchId);
        switch(endGameRequest.getChosenAction()){
            case 1:
                send(new WaitingRoomReply());
                break;
            case 2:
                send(new DialogReply("Thank you for playing the game!", null));
                disconnect();
                ServerMultiGame.removeClient(nickname);
                break;
            default:
                send(new AlertReply("Invalid choice, please try again!"));
                send(new EndGameReply());
                break;
        }
    }
}
