package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Type;
import it.polimi.ingsw.network.message.reply.AlertReply;
import it.polimi.ingsw.network.message.request.Ping;
import it.polimi.ingsw.util.Observable;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class that implements multithreading on the client.
 * Extends observables to update the GameController
 */
public class ClientMultiThread extends Observable implements Client {

    private final Socket clientSocket;

    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    private final ScheduledExecutorService poolingExecutor;
    private final ExecutorService readExecutor;


    /**
     * Establish socket connection with server {@param ip} at {@param port}
     *
     * @throws IOException if cant create Stream
     */
    public ClientMultiThread(String ip, int port) throws IOException {
        this.clientSocket = new Socket(ip, port);
        this.readExecutor = Executors.newSingleThreadExecutor();
        this.poolingExecutor = Executors.newSingleThreadScheduledExecutor();
        this.in = new ObjectInputStream(clientSocket.getInputStream());
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.flush();
    }

    @Override
    public void startServerListener() {
        // start new thread with submit and not execute because returns a Future object.
        // This Future object can be used to check if the Runnable has finished executing.
        readExecutor.submit(() -> {
                    while (!readExecutor.isShutdown()) {
                        Message receive = receivedMessage();
                        updateAllObserver(receive);
                    }
                }
        );
    }

    @Override
    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.reset(); //Reset the Output stream so that objects previously written will not be referred to as already being in the stream
        } catch (IOException e) {
            LOG.warning("Server unreachable, stop connection");
            stopConnection();
        }
        if (message.getType() != Type.PING) LOG.info("Send " + message); //Log all no PING message
    }

    @Override
    public void showRequest(Message message) {
        sendMessage(message);
        Message receive = receivedMessage();
        updateAllObserver(receive);
    }

    @Override
    public Message receivedMessage() {
        try {
            Message message = (Message) in.readObject();
            LOG.info("Received " + message.toString());
            return message;
        } catch (IOException | ClassNotFoundException e) {
            LOG.warning("Cant read message");
            return null;
        }
    }

    @Override
    public void stopConnection() {
        try {
            AlertReply message = new AlertReply("You are disconnected from the server, please relaunch the Application");
            updateAllObserver(message);
            readExecutor.shutdown();
            setPooling(false);
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            LOG.warning("Cant stop connection");
        }
    }

    @Override
    public void setPooling(boolean status) {
        if (status) {
            poolingExecutor.scheduleAtFixedRate(() -> sendMessage(new Ping(null)), 0, 2, TimeUnit.SECONDS);
            //Create and execute tasks that run periodically until cancelled
        } else {
            poolingExecutor.shutdownNow();
            // use shutdownNow and not shutdown because shutdownNow try to cancel the already submitted tasks by interrupting the relevant threads,
            //instead on shutdown already submitted tasks continue to run!
        }

    }
}
