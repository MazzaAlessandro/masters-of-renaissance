package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.view.gui.ClientSocketGuiInitializer;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Handles the connection to the Server, allowing to select the Server IP and the Socket Port.
 * If none are selected, it will automatically set the localhost IP and the 1235 port
 */
public class ConnectionController extends GuiController {

    @FXML
    private AnchorPane connectionPane;
    @FXML
    public Button connectBtn;
    @FXML
    public Button backBtn;
    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;

    @FXML
    public void initialize(){
        GuiController.draggableStage(connectionPane, mainStage,null);
    }

    @FXML
    private void backToMainMenu() {
        setAllElementDisable(true);
        GuiController.layoutSwapper(connectionPane.getScene(),"fxml/mainMenuScene.fxml");
    }

    @FXML
    private void connect() {
        try{
            setAllElementDisable(true);
            String ip = ipField.getText();
            String port = portField.getText();
            if(ip.equals("")) ip = "127.0.0.1";
            if(port.equals("")) port = "1235";

            ClientSocketGuiInitializer initializer = (ClientSocketGuiInitializer) gui.getInitializer();

            initializer.setIp(ip);
            initializer.setPort(Integer.parseInt(port));
            initializer.initialize();
        }catch(NumberFormatException e){
            GuiController.showAlert(mainStage,"Invalid port");
            setAllElementDisable(false);
        }
    }

    private void setAllElementDisable(Boolean state){
        connectBtn.setDisable(state);
        backBtn.setDisable(state);
        ipField.setDisable(state);
        portField.setDisable(state);
    }
}
