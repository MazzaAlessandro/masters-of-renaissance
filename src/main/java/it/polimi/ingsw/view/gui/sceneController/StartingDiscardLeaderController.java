package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.network.message.request.StartingDiscardLeaderRequest;
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
 * Handles the starting scene in which the player has to choose two out of four Leader cards to discard
 */
public class StartingDiscardLeaderController extends GuiController {
    private final List<String> inputLeadersPath = new ArrayList<>();
    private final List<Integer> selectedLeaders = new ArrayList<>();
    @FXML
    public AnchorPane startingDiscardPane;

    @FXML
    public ImageView leader1;

    @FXML
    public ImageView leader2;

    @FXML
    public ImageView leader3;

    @FXML
    public ImageView leader4;

    @FXML
    public Button discardBtn;

    public void setLeadersFace(String path){
        inputLeadersPath.add(path);
    }

    @FXML
    public void initialize(){
        GuiController.draggableStage(startingDiscardPane, mainStage,null);
        leader1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        leader2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        leader3.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        leader4.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        discardBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::discardUpdate);

        List<ImageView> leadersArray = new ArrayList<>(){{
            add(leader1);
            add(leader2);
            add(leader3);
            add(leader4);
        }};

        for (int i = 0; i < 4; i++) {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(inputLeadersPath.get(i))));
            leadersArray.get(i).setImage(img);
        }
    }
    @FXML
    public void onClick(MouseEvent event) {
        String clickedLeader = ((ImageView) event.getSource()).getId();
        int clickedLeaderValue = 4;
        switch (clickedLeader){
            case "leader1":
                clickedLeaderValue = 0;
                break;
            case "leader2":
                clickedLeaderValue = 1;
                break;
            case "leader3":
                clickedLeaderValue = 2;
                break;
            case "leader4":
                clickedLeaderValue = 3;
                break;
            default:
                break;
        }
        if (clickedLeaderValue != 4){
            if(selectedLeaders.contains(clickedLeaderValue)) {
                selectedLeaders.remove(Integer.valueOf(clickedLeaderValue));
                mainStage.getScene().lookup("#"+clickedLeader).setOpacity(0.6);
            }
            else {
                selectedLeaders.add(clickedLeaderValue);
                mainStage.getScene().lookup("#"+clickedLeader).setOpacity(1);
            }
        }
        discardBtn.setDisable(selectedLeaders.size() != 2);
    }

    @FXML
    public void discardUpdate(Event event) {
        discardBtn.setDisable(true);
        sender.send(new StartingDiscardLeaderRequest(nickname, selectedLeaders));
        //GuiController.changeStageSize(1080, 613);
        //Platform.runLater( () -> GuiController.layoutSwapper (mainStage.getScene(),"/fxml/boardScene.fxml") );
        Platform.runLater(GuiController::updateBoard);
    }

}
