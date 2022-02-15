package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.util.Objects;

/**
 * Launch First Scene in Server Game
 */
public class JavaFxLauncherSocket extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Gui gui = new Gui();
            ClientSocketGuiInitializer initializer = new ClientSocketGuiInitializer(gui);
            gui.setInitializer(initializer);
            GuiController.setGui(gui);
            GuiController.setMainStage(stage);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/mainMenuScene.fxml")));
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image("/images/guiAsset/icon.png"));
            stage.show();
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
