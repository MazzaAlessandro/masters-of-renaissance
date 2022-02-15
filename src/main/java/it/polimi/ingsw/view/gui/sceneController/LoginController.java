package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.network.message.request.LoginRequest;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Handles the login to the server, allowing the player to set a valid nickname
 */
public class LoginController extends GuiController {
    @FXML
    public AnchorPane loginPane;
    @FXML
    public TextField nicknameField;
    @FXML
    public Button okBtn;

    @FXML
    public void initialize() {
        GuiController.draggableStage(loginPane, mainStage, null);
    }

    @FXML
    public void nicknameUpdate() {
        String nickname = nicknameField.getText();
        nickname = nickname.replaceAll("\\s+", "");
        if (nickname.length() > 0 && nickname.length() < 50) {
            okBtn.setDisable(true);
            GuiController.setNickname(nickname);
            if (GuiController.nickname != null) {
                String finalNickname = nickname;
                Platform.runLater(() -> sender.send(new LoginRequest(finalNickname)));
            } else {
                showAlert(mainStage, "There was an issue while registering your nickname, please retry!");
                okBtn.setDisable(false);
            }

        } else {
            showAlert(mainStage, "The nickname isn't valid, retry");
        }


    }
}
