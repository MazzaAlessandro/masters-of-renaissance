package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.network.message.request.WaitingRoomRequest;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * Handles the initial choice to either create a new match or join a match that already exist in the server
 */
public class WaitingRoomController extends GuiController {
    @FXML
    public AnchorPane waitingRoomPane;
    @FXML
    public Button newMatchBtn;
    @FXML
    public Button joinMatchBtn;
    @FXML
    public void initialize(){
        GuiController.draggableStage(waitingRoomPane, mainStage,null);
    }

    @FXML
    public void newMatchUpdate() {
        newMatchBtn.setDisable(true);
        joinMatchBtn.setDisable(true);
        sender.send(new WaitingRoomRequest(nickname, 1000));
    }

    @FXML
    public void joinUpdate() {
        newMatchBtn.setDisable(true);
        joinMatchBtn.setDisable(true);
        GuiController.layoutSwapper(mainStage.getScene(),"/fxml/waitingRoomSceneMatchId.fxml");
    }
}
