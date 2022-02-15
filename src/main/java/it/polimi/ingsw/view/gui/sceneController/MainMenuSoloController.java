package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.network.message.request.InitializeGameRequest;
import it.polimi.ingsw.network.message.request.LoginRequest;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * Handles the creation of a Solo match without the server.
 * Since no login is needed, selecting "Start New Game" will immediately start the match against Lorenzo.
 */
public class MainMenuSoloController extends GuiController{

    @FXML
    private AnchorPane mainMenuPane;
    @FXML
    public Button startBtn;
    @FXML
    public Button quitBtn;


    @FXML
    public void initialize(){
        GuiController.draggableStage(mainMenuPane, mainStage,null);
    }

    @FXML
    public void openConnectPopup() {
        startBtn.setDisable(true);
        quitBtn.setDisable(true);
        sender.send(new LoginRequest("SOLO"));
        sender.send(new InitializeGameRequest(1, "SOLO"));
    }

    @FXML
    public void quitGame() {
        startBtn.setDisable(true);
        quitBtn.setDisable(true);
        System.exit(0);
    }
}
