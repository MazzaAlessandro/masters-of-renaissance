package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.network.message.request.InitializeGameRequest;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;

/**
 * Handles the selection of the number of players allowed in a game.
 * Selecting '1' will immediately start a game against Lorenzo, while the other choices will have you waiting for the other players to join.
 */
public class MaxPlayerController extends GuiController {

    @FXML
    public AnchorPane maxPlayerPane;
    @FXML
    public Button onePlayerBtn;
    @FXML
    public Button twoPlayerBtn;
    @FXML
    public Button threePlayerBtn;
    @FXML
    public Button fourPlayerBtn;
    @FXML
    public void initialize(){
        GuiController.draggableStage(maxPlayerPane, mainStage,null);
    }
    @FXML
    public void maxPlayerUpdate(ActionEvent event) {
        onePlayerBtn.setDisable(true);
        twoPlayerBtn.setDisable(true);
        threePlayerBtn.setDisable(true);
        fourPlayerBtn.setDisable(true);
        String clickedBtn = ((Control) event.getSource()).getId();
        switch (clickedBtn){
            case "onePlayerBtn":
                sender.send(new InitializeGameRequest(1, nickname));
                break;
            case "twoPlayerBtn":
                sender.send(new InitializeGameRequest(2, nickname));
                break;
            case "threePlayerBtn":
                sender.send(new InitializeGameRequest(3, nickname));
                break;
            case "fourPlayerBtn":
                sender.send(new InitializeGameRequest(4, nickname));
                break;
            default:
                break;
        }
    }
}
