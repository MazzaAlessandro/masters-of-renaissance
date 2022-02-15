package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.view.gui.GuiController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * Handles the connection to the server. The client can either select "Connect to the server" to start the connection or "Quit" to close the application
 */
public class MainMenuController extends GuiController{

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
        GuiController.layoutSwapper(mainMenuPane.getScene(),"/fxml/connectionScene.fxml");
    }

    @FXML
    public void quitGame() {
        startBtn.setDisable(true);
        quitBtn.setDisable(true);
        System.exit(0);
    }
}
