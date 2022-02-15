package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.view.sender.SenderDirectly;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

/**
 * Launch First Scene in Solo Game
 */
public class JavaFxLauncherSolo extends Application {
    private static SenderDirectly senderView;
    private static SenderDirectly senderController;

    public static void setSenderView(SenderDirectly senderView) {
        JavaFxLauncherSolo.senderView = senderView;
    }

    public static void setSenderController(SenderDirectly senderController) {
        JavaFxLauncherSolo.senderController = senderController;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Gui gui = new Gui();
            ClientSoloGuiInitializer clientSoloGuiInitializer= new ClientSoloGuiInitializer(gui,senderView,senderController);
            GuiController.setGui(gui);
            GuiController.setMainStage(stage);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/mainMenuSoloScene.fxml")));
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.setResizable(false);
            clientSoloGuiInitializer.initialize();
            stage.show();
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
