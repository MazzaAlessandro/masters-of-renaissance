package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.view.gui.GuiController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Handles show Development Card tray scene
 */
public class DevCardTrayController extends GuiController {
    List<String> inputDevCardPath = new ArrayList<>();
    @FXML
    public AnchorPane devCardTrayPane;
    @FXML
    public ImageView devCard0;
    @FXML
    public ImageView devCard3;
    @FXML
    public ImageView devCard6;
    @FXML
    public ImageView devCard9;
    @FXML
    public ImageView devCard1;
    @FXML
    public ImageView devCard4;
    @FXML
    public ImageView devCard7;
    @FXML
    public ImageView devCard10;
    @FXML
    public ImageView devCard2;
    @FXML
    public ImageView devCard5;
    @FXML
    public ImageView devCard8;
    @FXML
    public ImageView devCard11;
    @FXML
    public Button backBtn;


    public void setDevCardFace(String path){
        inputDevCardPath.add(path);
    }

    @FXML
    public void initialize(){
        GuiController.draggableStage(devCardTrayPane, mainStage,null);
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::backUpdate);
        List<ImageView> devCardArray = new ArrayList<>(){{
            add(devCard0);
            add(devCard1);
            add(devCard2);
            add(devCard3);
            add(devCard4);
            add(devCard5);
            add(devCard6);
            add(devCard7);
            add(devCard8);
            add(devCard9);
            add(devCard10);
            add(devCard11);
        }};

        System.out.println(inputDevCardPath);
        System.out.println(devCardArray);
        for (int i = 0; i < 12; i++) {
            System.out.println(getClass().getResourceAsStream(inputDevCardPath.get(i)));
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(inputDevCardPath.get(i))));
            devCardArray.get(i).setImage(img);
        }
    }

    public void backUpdate(Event event) {
        backBtn.setDisable(true);
        Platform.runLater(GuiController::fastBackInBoard);
    }
}
