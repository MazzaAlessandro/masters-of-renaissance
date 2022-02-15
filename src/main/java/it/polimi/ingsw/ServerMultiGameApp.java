package it.polimi.ingsw;

import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.ServerMultiGame;

public class ServerMultiGameApp {
    public static void main(String[] args) {
        int port = 1235; //default port
        boolean useCustom = false;

        for (String arg:args) {
            if (arg.equals("-port") && args.length >= 2) {
                useCustom = true;
            }
            else if (useCustom){
                try {
                    port = Integer.parseInt(arg);
                }catch (NumberFormatException e){
                    Server.LOG.warning("Invalid Custom Port");
                }
                break;
            }
        }

        Server server = new ServerMultiGame();
        server.start(port);
    }
}
