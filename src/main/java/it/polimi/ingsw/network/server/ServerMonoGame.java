package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.VirtualView;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServerMonoGame implements Server {
    private final GameController gameController;
    private ServerSocket serverSocket;
    private final List<ClientHandlerMonoGame> clients = new ArrayList<>();
    private boolean isFirstPlayerConnected = false;

    /**
     * Constructor that associates the game controller and the virtual view to the server
     */
    public ServerMonoGame(GameController gameController) {
        this.gameController = gameController;
        this.gameController.setVirtualView(new VirtualView(this));
    }


    @Override
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            LOG.warning("Can't start the Server");
        }
        LOG.info("Server Start at port " + port);
        while (true) { //change true with correct thread
            Socket client;
            try {

                client = serverSocket.accept(); //accept the incoming request to the socket
                boolean isGameInitialize = gameController.isGameInitialized();

                if (client != null) {
                    if (! this.isFirstPlayerConnected){
                        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                        out.flush();//This will write any buffered output bytes and flush through to the underlying stream
                        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                        ClientHandlerMonoGame clientSock = new ClientHandlerMonoGame(this, client, out, in, true);
                        clientSock.addObserver(this.gameController);
                        clients.add(clientSock);
                        isFirstPlayerConnected = true;
                        new Thread(clientSock).start();// run ClientHandlerMultiThread
                    }
                    else if (isGameInitialize){
                        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                        out.flush();//This will write any buffered output bytes and flush through to the underlying stream
                        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                        ClientHandlerMonoGame clientSock = new ClientHandlerMonoGame(this, client, out, in, false);
                        clientSock.addObserver(this.gameController);
                        clients.add(clientSock);
                        new Thread(clientSock).start();// run ClientHandlerMultiThread
                    }
                    else{
                        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                        out.flush();//This will write any buffered output bytes and flush through to the underlying stream
                        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                        ClientHandlerMonoGame clientSock = new ClientHandlerMonoGame(this, client, out, in, false);
                        clientSock.addObserver(this.gameController);
                        clients.add(clientSock);
                        new Thread(clientSock).start();
                        clientSock.disconnect();
                    }
                }

            } catch (IOException e) {
                LOG.warning("Can't create new ClientHandler");
            }
        }
    }


    @Override
    public boolean isNicknameTaken(String nickname) {
        for (ClientHandlerMonoGame client : clients) {
            if (Objects.equals(client.getNickname(), nickname)) return true;
        }
        return false;
    }


    @Override
    public ClientHandler nicknameToClientHandler(String nickname) {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(nickname)) return client;
        }
        return null;
    }


    @Override
    public void broadcast(Message message, int matchId) {
        for (ClientHandlerMonoGame client : this.clients) {
            client.send(message);
        }
    }

}
