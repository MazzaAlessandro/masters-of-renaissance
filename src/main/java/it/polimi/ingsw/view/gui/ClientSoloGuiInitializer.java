package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.ReplyBroker;
import it.polimi.ingsw.view.ClientViewInitializer;
import it.polimi.ingsw.view.sender.SenderDirectly;

/**
 * Initialize Gui for Solo Game
 */
public class ClientSoloGuiInitializer implements ClientViewInitializer {

    private final Gui gui;
    private final SenderDirectly senderView;
    private final SenderDirectly senderController;


    public ClientSoloGuiInitializer(Gui gui, SenderDirectly senderView, SenderDirectly senderController) {
        this.gui = gui;
        this.senderView = senderView;
        this.senderController = senderController;
    }


    @Override
    public void initialize() {

        ReplyBroker replyBroker = new ReplyBroker(gui);
        senderView.addObserver(replyBroker);
        gui.setSender(senderController);
        GuiController.setSender(gui.getSender());
    }

}
