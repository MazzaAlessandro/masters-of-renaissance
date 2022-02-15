package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.request.LoginRequest;
import it.polimi.ingsw.view.gui.sceneController.BoardController;
import it.polimi.ingsw.view.gui.sceneController.OtherPlayerBoardController;
import it.polimi.ingsw.view.gui.sceneController.ShowTokenController;
import it.polimi.ingsw.view.sender.Sender;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Controller for implement JavaFx basic function like swap stage and drag stage
 */
public class GuiController {

    protected static Gui gui;
    protected static Stage mainStage;
    protected static Stage actionStage;
    protected static Stage lorenzoStage;
    protected static BoardController boardController;
    protected static OtherPlayerBoardController otherBoardController;
    protected static Sender sender;
    protected static String nickname;

    public static BoardController getBoardController() {
        return boardController;
    }

    public static OtherPlayerBoardController getOtherBoardController() {
        return otherBoardController;
    }

    public static Stage getActionStage() {
        return actionStage;
    }

    public static void setActionStage(Stage actionStage) {
        GuiController.actionStage = actionStage;
    }

    public static void closeActionStage() {
        if (actionStage != null) {
            GuiController.actionStage.close();
            GuiController.actionStage = null;
        }
    }

    public static void closeLorenzoStage() {
        if (lorenzoStage != null) {
            GuiController.lorenzoStage.close();
            GuiController.lorenzoStage = null;
        }
    }

    public static void setGui(Gui gui) {
        GuiController.gui = gui;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void setMainStage(Stage mainStage) {
        GuiController.mainStage = mainStage;
    }

    public static void setSender(Sender sender) {
        GuiController.sender = sender;
    }

    public static void setNickname(String nickname) {

        GuiController.nickname = nickname;

    }

    /**
     * Swap content of {@param scene} with new scene built with {@param controller } and fxml file taken in {@param layoutPath}
     * Set the stage width to {@param width} and the height {@param height}
     * Implement Greedy algorithm for no kill Popup message
     */
    public static void layoutSwapperGreedy(GuiController controller, Scene scene, String layoutPath, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(GuiController.class.getResource(layoutPath));
            loader.setController(controller);
            changeStageSize((Stage) scene.getWindow(), width, height);
            Pane pane = loader.load();
            scene.setRoot(pane);
        } catch (IOException e) {
            Client.LOG.severe(e.getMessage());
        }
    }

    /**
     * Swap content of {@param scene} with new scene built with {@param controller } and fxml file taken in {@param layoutPath}
     * Implement Greedy algorithm for no kill Popup message
     */
    public static void layoutSwapperGreedy(GuiController controller, Scene scene, String layoutPath) {
        int width = (int) scene.getWindow().getWidth();
        int height = (int) scene.getWindow().getHeight();
        layoutSwapperGreedy(controller, scene, layoutPath, width, height);
    }

    /**
     * Swap content of {@param scene} with new scene built with fxml file taken in {@param layoutPath} and controller is embedded on fxml.
     * Set the stage width to {@param width} and the height {@param height}
     * Implement Greedy algorithm for no kill Popup message
     */
    public static void layoutSwapperGreedy(Scene scene, String layoutPath, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(GuiController.class.getResource(layoutPath));
            changeStageSize((Stage) scene.getWindow(), width, height);
            Pane pane = loader.load();
            scene.setRoot(pane);
        } catch (IOException e) {
            Client.LOG.severe(e.getMessage());
        }
    }

    /**
     * Swap content of {@param scene} with new scene built with fxml file taken in {@param layoutPath} and controller is embedded on fxml.
     * Implement Greedy algorithm for no kill Popup message
     */
    public static void layoutSwapperGreedy(Scene scene, String layoutPath) {
        int width = (int) scene.getWindow().getWidth();
        int height = (int) scene.getWindow().getHeight();
        layoutSwapperGreedy(scene, layoutPath, width, height);
    }

    /**
     * Swap content of {@param scene} with new scene built with {@param controller } and fxml file taken in {@param layoutPath}
     * Set the stage width to {@param width} and the height {@param height}
     */
    public static void layoutSwapper(GuiController controller, Scene scene, String layoutPath, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(GuiController.class.getResource(layoutPath));
            loader.setController(controller);
            scene.getWindow().hide();
            changeStageSize((Stage) scene.getWindow(), width, height);
            Pane pane = loader.load();
            scene.setRoot(pane);
            ((Stage) scene.getWindow()).show();
        } catch (IOException e) {
            Client.LOG.severe(e.getMessage());
        }
    }

    /**
     * Swap content of {@param scene} with new scene built with {@param controller } and fxml file taken in {@param layoutPath}
     */
    public static void layoutSwapper(GuiController controller, Scene scene, String layoutPath) {
        int width = (int) scene.getWindow().getWidth();
        int height = (int) scene.getWindow().getHeight();
        layoutSwapper(controller, scene, layoutPath, width, height);
    }

    /**
     * Swap content of {@param scene} with new scene built with {@param controller } and fxml file taken in {@param layoutPath}
     * Set the stage width to {@param width} and the height {@param height}
     */
    public static void layoutSwapper(Scene scene, String layoutPath, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(GuiController.class.getResource(layoutPath));
            scene.getWindow().hide();
            changeStageSize((Stage) scene.getWindow(), width, height);
            Pane pane = loader.load();
            scene.setRoot(pane);
            ((Stage) scene.getWindow()).show();
        } catch (IOException e) {
            Client.LOG.severe(e.getMessage());
        }
    }

    /**
     * Swap content of {@param scene} with new scene built with fxml file taken in {@param layoutPath} and controller is embedded on fxml.
     */
    public static void layoutSwapper(Scene scene, String layoutPath) {
        int width = (int) scene.getWindow().getWidth();
        int height = (int) scene.getWindow().getHeight();
        layoutSwapper(scene, layoutPath, width, height);
    }

    /**
     * Set the stage width to {@param width} and the height {@param height}
     */
    public static void changeStageSize(Stage stage, int width, int height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }


    /**
     * Make {@param stage} in {@param mainNode} Draggable if {@param stage} not null
     * Make {@param alert} in {@param mainNode} Draggable if {@param stage} not null
     */
    public static void draggableStage(Node mainNode, Stage stage, Alert alert) {
        double[] xOffset = {0}, yOffset = {0};
        mainNode.setOnMousePressed(event -> {
            if (stage != null && alert == null) {
                xOffset[0] = stage.getX() - event.getScreenX();
                yOffset[0] = stage.getY() - event.getScreenY();
            } else if (stage == null && alert != null) {
                xOffset[0] = alert.getX() - event.getScreenX();
                yOffset[0] = alert.getY() - event.getScreenY();
            }
        });

        mainNode.setOnMouseDragged(event -> {
            if (stage != null && alert == null) {
                stage.setX(event.getScreenX() + xOffset[0]);
                stage.setY(event.getScreenY() + yOffset[0]);
            } else if (stage == null && alert != null) {
                alert.setX(event.getScreenX() + xOffset[0]);
                alert.setY(event.getScreenY() + yOffset[0]);
            }
        });
    }

    /**
     * Crate a popup windows attached to {@param currentStage}  and set text with {@param message}
     */
    private static void buildPopup(Stage currentStage, String message, FXMLLoader loader) throws IOException {
        Scene dialog;
        dialog = new Scene(loader.load(), 350, 250);
        Stage stage = new Stage();
        stage.setScene(dialog);
        stage.initOwner(currentStage);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setAlwaysOnTop(true);
        dialog.lookup("#okBtn").addEventHandler(MouseEvent.MOUSE_CLICKED, event -> stage.close());
        ((Label) dialog.lookup("#messageLabel")).setText(message);
        stage.showAndWait();
    }


    /**
     * Build Dialog Popup
     */
    public static void showDialog(Stage currentStage, String message) {
        FXMLLoader loader = new FXMLLoader(GuiController.class.getResource("/fxml/dialogScene.fxml"));

        try {
            buildPopup(currentStage, message, loader);
        } catch (IOException ignored) {
        }

    }

    /**
     * Build Alert Popup
     */
    public static void showAlert(Stage currentStage, String message) {
        FXMLLoader loader = new FXMLLoader(GuiController.class.getResource("/fxml/alertScene.fxml"));

        try {
            buildPopup(currentStage, message, loader);
        } catch (IOException ignored) {
        }
    }

    /**
     * Open modular and set content to {@param wanted} with new scene built with {@param controller } and fxml file taken in {@param fxmlPath}
     * Set the stage width to {@param width} and the height {@param height}
     */
    public static void showModularWindows(GuiController controller, Stage wanted, String fxmlPath, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(GuiController.class.getResource(fxmlPath));
            loader.setController(controller);
            Scene scene = new Scene(loader.load(), width, height);
            changeStageSize(wanted, width, height);
            wanted.setScene(scene);
            wanted.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Open modular and set content to {@param wanted} with new scene built with {@param controller } and fxml file taken in {@param fxmlPath}
     */
    public static void showModularWindows(Stage wanted, String fxmlPath, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(GuiController.class.getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), width, height);
            changeStageSize(wanted, width, height);
            wanted.setScene(scene);
            wanted.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a new if {@param stage } is null and set basic style
     */
    public static Stage buildStage(Stage stage) {
        if (stage == null) {
            stage = new Stage();
            stage.setResizable(false);
            stage.initModality(Modality.NONE);
            stage.initStyle(StageStyle.UNDECORATED);
        }
        stage.hide();
        return stage;
    }

    /**
     * Show your Board
     */
    public static void updateBoard() {
        try {
            BoardController controller = new BoardController();
            FXMLLoader loader = new FXMLLoader(GuiController.class.getResource("/fxml/boardScene.fxml"));
            loader.setController(controller);
            changeStageSize(mainStage, 1080, 613);
            Pane pane = loader.load();
            mainStage.getScene().setRoot(pane);
            boardController = controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show Board of other player
     */
    public static void updateOtherBoard() {
        try {
            OtherPlayerBoardController controller = new OtherPlayerBoardController();
            FXMLLoader loader = new FXMLLoader(GuiController.class.getResource("/fxml/otherPlayerBoardScene.fxml"));
            loader.setController(controller);
            changeStageSize(mainStage, 1080, 613);
            Pane pane = loader.load();
            mainStage.getScene().setRoot(pane);
            otherBoardController = controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create or Refresh Lorenzo action info
     *
     * @param controller passed by gui with server side parameter
     */
    public static void updateLorenzo(ShowTokenController controller) {

        if (lorenzoStage == null) {
            lorenzoStage = buildStage(null);
            showModularWindows(controller, lorenzoStage, "/fxml/showTokenScene.fxml", 700, 500);
        }
        else{
            layoutSwapper(controller, lorenzoStage.getScene(), "/fxml/showTokenScene.fxml");
        }

    }

    /**
     * Return in board without call the Server
     */
    public static void fastBackInBoard() {
        try {
            BoardController controller = boardController;
            FXMLLoader loader = new FXMLLoader(GuiController.class.getResource("/fxml/boardScene.fxml"));
            loader.setController(controller);
            mainStage.hide();
            changeStageSize(mainStage, 1080, 613);
            Pane pane = loader.load();
            mainStage.getScene().setRoot(pane);
            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return in board without call the Server
     */
    public static void fastBackInOtherPlayerBoard() {
        try {
            OtherPlayerBoardController controller = otherBoardController;
            FXMLLoader loader = new FXMLLoader(GuiController.class.getResource("/fxml/otherPlayerBoardScene.fxml"));
            loader.setController(controller);
            mainStage.hide();
            changeStageSize(mainStage, 1080, 613);
            Pane pane = loader.load();
            mainStage.getScene().setRoot(pane);
            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
