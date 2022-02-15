package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.network.message.request.WaitingRoomRequest;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Handles the selection of the matchId used to join an already existing match
 */
public class WaitingRoomMatchIdController extends GuiController {
    @FXML
    public AnchorPane waitingRoomMatchIdPane;
    @FXML
    public TextField matchIdField;
    @FXML
    public Button enterBtn;
    @FXML
    public void initialize(){
        GuiController.draggableStage(waitingRoomMatchIdPane, mainStage,null);
    }
    @FXML
    public void matchIdUpdate() {

        try {
            int id = Integer.parseInt(matchIdField.getText());
            enterBtn.setDisable(true);
            sender.send(new WaitingRoomRequest(nickname, id));
        }catch(NumberFormatException e){
            showAlert(mainStage,"The match ID isn't valid, please retry");
        }
    }
}
