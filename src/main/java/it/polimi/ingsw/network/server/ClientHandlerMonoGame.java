package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.reply.*;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;
import it.polimi.ingsw.util.Observable;

import java.io.*;
import java.net.Socket;


public class ClientHandlerMonoGame extends Observable implements ClientHandler {

    private final Server server;
    private final Socket clientSocket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final Object mutexIn; //sync request
    private final Object mutexOut;//sync reply

    private final boolean isFirstConnected;
    private String nickname;

    public ClientHandlerMonoGame(Server server, Socket socket, ObjectOutputStream out, ObjectInputStream in, boolean isFirst) {
        this.server = server;
        this.clientSocket = socket;
        this.out = out;
        this.in = in;
        this.isFirstConnected = isFirst;
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
                        if (message.getType().equals(Type.LOGIN)) {
                            loginBroker(message);
                        } else updateAllObserver(message); //update game controller
                    }
                } catch (IOException | ClassNotFoundException e) {
                    Server.LOG.info("it.polimi.ingsw.Client" + clientSocket.getInetAddress() + " close connection");
                    disconnect();
                }
            }
        }
    }

    private void loginBroker(Message message) {
        if (isFirstConnected) {
            this.nickname = message.getSender();
            Server.LOG.info("Add " + message.getSender() + " in players list");
            updateAllObserver(message);
            send(new InitializeGameReply());
        }
        else {
            if(server.isNicknameTaken(message.getSender())){
                send(new AlertReply("Nickname already taken!"));
                send(new LoginReply(false));
            }
            else {
                this.nickname = message.getSender();
                Server.LOG.info("Add " + message.getSender() + " in players list");
                send(new WaitForOtherPlayersReply());
                updateAllObserver(message);
            }
        }
    }

}
