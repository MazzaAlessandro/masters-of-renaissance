package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.view.gui.GuiController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Handles the show Leader cards scene
 */
public class LeadersController extends GuiController {
    private final List<String> inputLeadersPath = new ArrayList<>();
    @FXML
    public AnchorPane leadersPane;
    @FXML
    public ImageView leader1;
    @FXML
    public ImageView leader2;
    @FXML
    public Label titleLabel;
    @FXML
    public Label leaderGoneLabel;
    @FXML
    public Button backBtn;


    public void setLeadersFace(String path){
        inputLeadersPath.add(path);
    }


    @FXML
    public void initialize(){
        GuiController.draggableStage(leadersPane, mainStage,null);
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::backUpdate);

        List<ImageView> leadersArray = new ArrayList<>(){{
            add(leader1);
            add(leader2);
        }};

        for (int i = 0; i < inputLeadersPath.size() && i < 2; i++) {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(inputLeadersPath.get(i))));
            leadersArray.get(i).setImage(img);
        }
        if (inputLeadersPath.size()==0){
          titleLabel.setVisible(false);
          leaderGoneLabel.setVisible(true);
        }
    }

    public void backUpdate(Event event) {
        backBtn.setDisable(true);
        Platform.runLater(GuiController::fastBackInBoard);
    }
}
