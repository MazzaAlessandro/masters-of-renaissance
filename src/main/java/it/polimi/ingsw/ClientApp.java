package it.polimi.ingsw;

import it.polimi.ingsw.network.client.ClientMultiThread;
import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.cli.ClientSocketCliInitializer;
import it.polimi.ingsw.view.gui.JavaFxLauncherSocket;

import java.util.logging.Level;

public class ClientApp {
    public static void main(String[] args) {
        boolean useCli = false; //default start with GUI
        boolean useLogger = false; //default not use Logger

        for(String arg: args){
            if (arg.equals("-cli")) {
                useCli = true;
                break;
            }
            if (arg.equals("-debug")){
                useCli = true;
                useLogger = true;
                break;
            }
        }
        if (useLogger) ClientMultiThread.LOG.setLevel(Level.INFO);
        else ClientMultiThread.LOG.setLevel(Level.OFF);

        if (useCli) {
            Cli cli = new Cli();
            ClientSocketCliInitializer initializer = new ClientSocketCliInitializer(cli);
            initializer.initialize();
        }
        else {
            JavaFxLauncherSocket.main(args);
        }
    }
}
