package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.client.ClientMultiThread;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.VirtualView;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.ClientSoloCliInitializer;
import it.polimi.ingsw.view.gui.JavaFxLauncherSolo;
import it.polimi.ingsw.view.sender.SenderDirectly;

import java.util.logging.Level;

public class SoloApp {
    public static void main(String[] args) {
        ClientMultiThread.LOG.setLevel(Level.OFF);
        Server.LOG.setLevel(Level.OFF);
        boolean useCli = false; //default start with GUI
        GameController gameController = new GameController();
        SenderDirectly senderView = new SenderDirectly();
        SenderDirectly senderController = new SenderDirectly();
        VirtualView virtualView = new VirtualView(senderView);
        gameController.setVirtualView(virtualView);
        senderController.addObserver(gameController);

        for(String arg: args){
            if (arg.equals("-cli")) {
                useCli = true;
                break;
            }
        }

        if (useCli) {
            Cli cli = new Cli();
            ClientSoloCliInitializer initializer = new ClientSoloCliInitializer(cli,senderView,senderController);
            initializer.initialize();
        }
        else {
            JavaFxLauncherSolo.setSenderView(senderView);
            JavaFxLauncherSolo.setSenderController(senderController);
            JavaFxLauncherSolo.main(args);
        }
    }
}
