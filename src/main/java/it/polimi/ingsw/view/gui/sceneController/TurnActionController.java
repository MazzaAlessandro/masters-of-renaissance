package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.network.message.request.TurnActionRequest;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.BorderPane;

/**
 * Handles the selection of the main turn action, showing a button for each one of them
 */
public class TurnActionController extends GuiController{
    @FXML
    public BorderPane turnActionPane;
    @FXML
    public Button takeResourcesBtn;
    @FXML
    public Button buyDevCardBtn;
    @FXML
    public Button activeProductionBtn;

    @FXML
    public void initialize(){
        GuiController.draggableStage(turnActionPane, actionStage,null);
    }

    @FXML
    public void chooseUpdate(ActionEvent event) {
        String clickedBtn = ((Control) event.getSource()).getId();
        takeResourcesBtn.setDisable(true);
        buyDevCardBtn.setDisable(true);
        activeProductionBtn.setDisable(true);
        switch (clickedBtn){
            case "takeResourcesBtn":
                sender.send(new TurnActionRequest(nickname, 1));
                break;
            case"buyDevCardBtn":
                sender.send(new TurnActionRequest(nickname, 2));
                break;
            case"activeProductionBtn":
                sender.send(new TurnActionRequest(nickname, 3));
                break;
            default:
                takeResourcesBtn.setDisable(false);
                buyDevCardBtn.setDisable(false);
                activeProductionBtn.setDisable(false);
                break;
        }

    }
}
