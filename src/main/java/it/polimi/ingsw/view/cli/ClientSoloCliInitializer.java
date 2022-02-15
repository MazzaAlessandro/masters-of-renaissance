package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.network.client.ReplyBroker;
import it.polimi.ingsw.network.message.request.InitializeGameRequest;
import it.polimi.ingsw.network.message.request.LoginRequest;
import it.polimi.ingsw.view.ClientViewInitializer;
import it.polimi.ingsw.view.sender.SenderDirectly;

/**
 * Initialize Cli for Solo Game
 */
public class ClientSoloCliInitializer implements ClientViewInitializer {

    private final Cli cli;
    private final SenderDirectly senderView;
    private final SenderDirectly senderController;

    public ClientSoloCliInitializer(Cli cli, SenderDirectly senderView, SenderDirectly senderController) {
        this.cli = cli;
        this.senderView = senderView;
        this.senderController = senderController;
    }

    @Override
    public void initialize() {
        ReplyBroker replyBroker = new ReplyBroker(cli);
        senderView.addObserver(replyBroker);
        cli.setDirectlySender(senderController);
        senderController.send(new LoginRequest("SOLO"));
        senderController.send(new InitializeGameRequest(1, "SOLO"));
    }
}
