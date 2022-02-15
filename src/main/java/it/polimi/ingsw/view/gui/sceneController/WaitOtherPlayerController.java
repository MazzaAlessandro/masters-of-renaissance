package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.view.gui.GuiController;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * Displays a loading screen while the player is waiting for the others to join
 */
public class WaitOtherPlayerController extends GuiController {
    @FXML
    public AnchorPane waitingRoomPane;

    @FXML
    public void initialize(){
        GuiController.draggableStage(waitingRoomPane, mainStage,null);
    }

}
