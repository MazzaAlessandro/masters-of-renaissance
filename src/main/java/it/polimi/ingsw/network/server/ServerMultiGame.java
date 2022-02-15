package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.VirtualView;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Handles the client-server communications through multithreading and sockets
 * It implements the Server interface, overriding most of its methods
 */
public class ServerMultiGame implements Server {
    private ServerSocket serverSocket;
    private final VirtualView virtualView;
    private static final List<ClientHandler> clients = new ArrayList<>();
    private static final List<Match> matches = new ArrayList<>();

    public List<Match> getMatches() {
        return matches;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    public ServerMultiGame() {
        this.virtualView = new VirtualView(this);
    }

    public List<ClientHandler> getClients() {
        return clients;
    }

    @Override
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            LOG.warning("Can't start the Server");
        }
        LOG.info("Server Start at port " + port);
        while (true) {
            Socket client;
            try {
                client = serverSocket.accept();
                if (client != null) {
                    ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                    out.flush();//This will write any buffered output bytes and flush through to the underlying stream
                    ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                    ClientHandler clientSock = new ClientHandlerMultiGame(this, client, out, in);
                    clients.add(clientSock);
                    new Thread(clientSock).start();// run ClientHandlerMultiThread
                }
            } catch (IOException e) {
                LOG.warning("Can't create new ClientHandler");
            }
        }
    }

    @Override
    public boolean isNicknameTaken(String nickname) {

        for (ClientHandler client : clients) {
            if (Objects.equals(client.getNickname(), nickname)) return true;
        }
        return false;
    }

    @Override
    public ClientHandler nicknameToClientHandler(String nickname) {
        for (ClientHandler client : clients) {
            if (Objects.equals(client.getNickname(), nickname)) return client;
        }
        return null;
    }

    @Override
    public void broadcast(Message message, int matchId) {

        for (Match match : matches) {
            if (match.getMatchId() == matchId) {
                List<Player> players = match.getGameController().getGame().getPlayers();
                for (Player player : players) {
                    nicknameToClientHandler(player.getNickname()).send(message);
                }
            }
        }
    }

    /**
     * Removes the selected match from the server
     * @param matchId is the match that needs to be removed
     */
    public static void removeMatch(int matchId){
        matches.removeIf(match -> match.getMatchId() == matchId);
    }

    /**
     * Removes the ClientHandler from the server
     * @param client is the nickname corresponding to the ClientHandles to remove
     */
    public static void removeClient(String client){
        clients.removeIf(clientHandler -> clientHandler.getNickname().equals(client));
    }
}
