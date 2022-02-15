package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.view.gui.GuiController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class EndGameController extends GuiController {
    @FXML
    public AnchorPane endGamePane;
    @FXML
    public Button quitBtn;

    @FXML
    public void initialize() {
        GuiController.draggableStage(endGamePane, mainStage, null);
    }

    @FXML
    public void quit() {
        quitBtn.setDisable(true);
        System.exit(0);
    }
}
