package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.network.message.request.ActivateProductionRequest;
import it.polimi.ingsw.network.message.request.ShowFaithTrackRequest;
import it.polimi.ingsw.network.message.request.ShowOwnedResourcesRequest;
import it.polimi.ingsw.view.gui.GuiController;
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
 * Handles the different types of productions in a single scene (default, Development cards and Leader cards productions)
 */
public class ActivateProductionController extends GuiController {
    private final List<Integer> selectedProductions = new ArrayList<>();
    private List<Integer> productionPosition;
    private final List<String> inputLeadersPath = new ArrayList<>();
    private final List<String> inputDevCardsPath = new ArrayList<>();

    @FXML
    public AnchorPane productionPane;
    @FXML
    public ImageView leader1;
    @FXML
    public ImageView leader2;
    @FXML
    public ImageView devCard1;
    @FXML
    public ImageView devCard2;
    @FXML
    public ImageView devCard3;
    @FXML
    public Button backBtn;
    @FXML
    public Button activeBtn;
    @FXML
    public ImageView defaultProduction;


    @FXML
    public void initialize(){
        GuiController.draggableStage(productionPane, actionStage,null);
        defaultProduction.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::backUpdate);
        activeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::activeUpdate);

        List<ImageView> leadersArray = new ArrayList<>(){{
            add(leader1);
            add(leader2);
        }};

        for (int i = 0; i < inputLeadersPath.size(); i++) {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(inputLeadersPath.get(i))));
            setClickedEventLeader(i);
            leadersArray.get(i).setImage(img);
        }

        List<ImageView> devCardsArray = new ArrayList<>(){{
            add(devCard1);
            add(devCard2);
            add(devCard3);
        }};

        for (int i = 0; i < inputDevCardsPath.size(); i++) {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(inputDevCardsPath.get(i))));
            setClickedEventDev(i);
            devCardsArray.get(i).setImage(img);
        }
    }

    public void setClickedEventLeader(int index){
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

    public void setClickedEventDev(int index){
        switch (index){
            case 0:
                if(!inputDevCardsPath.get(index).equals("/images/cards/emptyCard.png")){
                    devCard1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
                }
                break;
            case 1:
                if(!inputDevCardsPath.get(index).equals("/images/cards/emptyCard.png")){
                    devCard2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
                }
                break;
            case 2:
                if(!inputDevCardsPath.get(index).equals("/images/cards/emptyCard.png")){
                    devCard3.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
                }
                break;
            default:
                break;
        }
    }

    public void setLeadersFace(String path){
        inputLeadersPath.add(path);
    }

    public void setDevCardsFace(String path){
        inputDevCardsPath.add(path);
    }


    public void backUpdate(Event event) {
        backBtn.setDisable(true);
        GuiController.layoutSwapper(actionStage.getScene(),"/fxml/turnActionScene.fxml",600,600);
    }

    public void activeUpdate (Event actionEvent){
        activeBtn.setDisable(true);
        backBtn.setDisable(true);
        sender.send(new ActivateProductionRequest(nickname,selectedProductions,productionPosition));
        sender.send(new ShowFaithTrackRequest(nickname));
        sender.send(new ShowOwnedResourcesRequest(nickname));
    }

    public void onClick(Event event) {
        String clickedProduction = ((ImageView) event.getSource()).getId();


        int clickedProductionValue = 6;
        switch (clickedProduction) {
            case "defaultProduction":
                clickedProductionValue = 0;
                break;
            case "devCard1":
                clickedProductionValue = 1;
                break;
            case "devCard2":
                clickedProductionValue = 2;
                break;
            case "devCard3":
                clickedProductionValue = 3;
                break;
            case "leader1":
                clickedProductionValue = 4;
                break;
            case "leader2":
                clickedProductionValue = 5;
                break;
            default:
                break;
        }
        if (clickedProductionValue != 6) {
            if (selectedProductions.contains(clickedProductionValue)) {
                selectedProductions.remove(Integer.valueOf(clickedProductionValue));
                actionStage.getScene().lookup("#" + clickedProduction).setOpacity(0.6);
            } else {
                selectedProductions.add(clickedProductionValue);
                actionStage.getScene().lookup("#" + clickedProduction).setOpacity(1);
            }
            activeBtn.setDisable(!(selectedProductions.size() > 0));
        }
    }

    public void setProductionPosition(List<Integer> productionPosition) {
        this.productionPosition = productionPosition;
    }
}

