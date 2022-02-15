package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.network.message.request.ActivateLeadersRequest;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Handles scene to active Leader cards
 */
public class ActivateLeadersController extends GuiController {
    private final List<Integer> selectedLeaders = new ArrayList<>();
    private final List<String> inputLeadersPath = new ArrayList<>();

    @FXML
    public BorderPane activeLeadersPane;

    @FXML
    public ImageView leader1;

    @FXML
    public ImageView leader2;

    @FXML
    public Button activeBtn;

    @FXML
    public Button skipBtn;

    public void setLeadersFace(String path){
        inputLeadersPath.add(path);
    }

    @FXML
    public void initialize(){
        GuiController.draggableStage(activeLeadersPane, actionStage,null);

        activeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::activeUpdate);
        skipBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::skipUpdate);


        List<ImageView> leadersArray = new ArrayList<>(){{
            add(leader1);
            add(leader2);
        }};

        for (int i = 0; i < inputLeadersPath.size(); i++) {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(inputLeadersPath.get(i))));
            setClickEvent(i);
            leadersArray.get(i).setImage(img);
        }

    }

    public void setClickEvent(int index){
        switch (index){
            case 0:
                if(!inputLeadersPath.get(index).equals("/images/cards/emptyCard.png")){
                    leader1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
                }
                break;
            case 1:
                if(!inputLeadersPath.get(index).equals("/images/cards/emptyCard.png")){
                    leader2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
                }
                break;
            default:
                break;
        }
    }

    @FXML
    public void onClick(MouseEvent event) {

        String clickedLeader = ((ImageView) event.getSource()).getId();

        int clickedLeaderValue = 0;
        switch (clickedLeader) {
            case "leader1":
                clickedLeaderValue = 1;
                break;
            case "leader2":
                clickedLeaderValue = 2;
                break;
            default:
                break;
        }

        if (clickedLeaderValue != 0) {
            if (selectedLeaders.contains(clickedLeaderValue)) {
                selectedLeaders.remove(Integer.valueOf(clickedLeaderValue));
                actionStage.getScene().lookup("#" + clickedLeader).setOpacity(0.6);
            } else {
                selectedLeaders.add(clickedLeaderValue);
                actionStage.getScene().lookup("#" + clickedLeader).setOpacity(1);
            }
        }
        activeBtn.setDisable(!(selectedLeaders.size() > 0));
    }

    @FXML
    public void activeUpdate(Event event) {
        activeBtn.setDisable(true);
        skipBtn.setDisable(true);
        closeActionStage();
        sender.send(new ActivateLeadersRequest(nickname, selectedLeaders));
    }

    @FXML
    public void skipUpdate(Event event) {
        activeBtn.setDisable(true);
        skipBtn.setDisable(true);
        List<Integer> nullLeaders = new ArrayList<>(){{add(0);}};
        closeActionStage();
        sender.send(new ActivateLeadersRequest(nickname, nullLeaders));

    }
}
