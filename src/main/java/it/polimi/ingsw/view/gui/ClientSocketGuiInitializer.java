package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.ClientMultiThread;
import it.polimi.ingsw.network.client.ReplyBroker;
import it.polimi.ingsw.view.ClientViewInitializer;
import it.polimi.ingsw.view.sender.SenderSocketClient;

import java.io.IOException;


/**
 * Initialize Gui for Server Connection
 */
public class ClientSocketGuiInitializer implements ClientViewInitializer {

    private final Gui gui;
    private String ip;
    private int port;

    public ClientSocketGuiInitializer(Gui gui) {
        this.gui = gui;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void initialize() {
        try {
            ClientMultiThread client = new ClientMultiThread(ip, port);
            ReplyBroker replyBroker = new ReplyBroker(gui);
            client.addObserver(replyBroker);
            client.startServerListener();
            client.setPooling(true);
            gui.setSender(new SenderSocketClient(client));
            GuiController.setSender(gui.getSender());
        } catch (IOException e) {
            GuiController.layoutSwapper(GuiController.getMainStage().getScene(),"/fxml/connectionScene.fxml");
            GuiController.showAlert(GuiController.getMainStage(),"Can't connect to the Server");
        }

    }

}
