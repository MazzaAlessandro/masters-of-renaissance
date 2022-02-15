package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.network.client.ClientMultiThread;
import it.polimi.ingsw.network.client.ReplyBroker;
import it.polimi.ingsw.view.ClientViewInitializer;
import it.polimi.ingsw.view.sender.SenderSocketClient;

import java.io.IOException;

public class ClientSocketCliInitializer implements ClientViewInitializer {

    private final Cli cli;

    /**
     * Initialize Cli for Server Connection
     */
    public ClientSocketCliInitializer (Cli cli) {
        this.cli = cli;
    }

    @Override
    public void initialize() {
        String ip = askIp();
        int port = askPort();
        ClientMultiThread client;
        try {
            client = new ClientMultiThread(ip, port);
        } catch (IOException e) {
            cli.printError("Unable connect to the server");
            return;
        }

        ReplyBroker replyBroker = new ReplyBroker(cli);
        client.addObserver(replyBroker);
        client.startServerListener();
        client.setPooling(true);
        cli.setSender(new SenderSocketClient(client));
    }

    /**
     * @return port number read in cli
     */
    private int askPort(){
        String portString;
        int port;
        cli.print("Enter server's port");
        portString = cli.read();
        if (portString.equals("")) portString = "1235";
        try{
            port = Integer.parseInt(portString);
        }catch (NumberFormatException e){
            cli.printError("Invalid port, please try again");
            port = askPort();
        }
        return port;
    }

    /**
     * @return ip address read in cli
     */
    private String askIp(){
        String ip;
        cli.print("Enter server's ip");
        ip = cli.read();
        if (ip.equals("")) ip = "127.0.0.1";
        return ip;
    }
}
