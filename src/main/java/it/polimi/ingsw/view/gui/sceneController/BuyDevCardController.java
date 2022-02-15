package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.network.message.request.BuyDevInTrayPositionRequest;
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
 * Handles the action of buying a Development card scene, showing the Development card set up
 */
public class BuyDevCardController extends GuiController {
    private final List<Integer> selectedDevCards = new ArrayList<>();
    List<String> inputDevCardPath = new ArrayList<>();

    @FXML
    public AnchorPane buyDevCardPane;

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
    @FXML
    public Button buyBtn;

    public void setDevCardFace(String path){
        inputDevCardPath.add(path);
    }

    @FXML
    public void initialize(){
        GuiController.draggableStage(buyDevCardPane, actionStage,null);
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::backUpdate);
        buyBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::buyUpdate);
        devCard0.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        devCard1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        devCard2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        devCard3.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        devCard4.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        devCard5.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        devCard6.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        devCard7.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        devCard8.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        devCard9.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        devCard10.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);
        devCard11.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onClick);

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

        for (int i = 0; i < 12; i++) {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(inputDevCardPath.get(i))));
            devCardArray.get(i).setImage(img);
        }
    }

    public void backUpdate(Event event) {
        backBtn.setDisable(true);
        GuiController.layoutSwapper(actionStage.getScene(),"/fxml/turnActionScene.fxml",600,600);
    }

    public void buyUpdate(Event event) {
        buyBtn.setDisable(true);
        backBtn.setDisable(true);
        sender.send(new BuyDevInTrayPositionRequest(nickname, selectedDevCards.get(0)));
    }

    public void onClick(MouseEvent event) {
        String clickedDevCard = ((ImageView) event.getSource()).getId();


        int clickedDevCardValue = 12;
        switch (clickedDevCard) {
            case "devCard0":
                clickedDevCardValue = 0;
                break;
            case "devCard1":
                clickedDevCardValue = 1;
                break;
            case "devCard2":
                clickedDevCardValue = 2;
                break;
            case "devCard3":
                clickedDevCardValue = 3;
                break;
            case "devCard4":
                clickedDevCardValue = 4;
                break;
            case "devCard5":
                clickedDevCardValue = 5;
                break;
            case "devCard6":
                clickedDevCardValue = 6;
                break;
            case "devCard7":
                clickedDevCardValue = 7;
                break;
            case "devCard8":
                clickedDevCardValue = 8;
                break;
            case "devCard9":
                clickedDevCardValue = 9;
                break;
            case "devCard10":
                clickedDevCardValue = 10;
                break;
            case "devCard11":
                clickedDevCardValue = 11;
                break;
            default:
                break;
        }
        if(selectedDevCards.size()==0){
            selectedDevCards.add(clickedDevCardValue);
            buyBtn.setDisable(false);
            actionStage.getScene().lookup("#" + clickedDevCard).setOpacity(1);
        } else{
            if(selectedDevCards.contains(clickedDevCardValue)){
                selectedDevCards.remove(0);
                buyBtn.setDisable(true);
                actionStage.getScene().lookup("#" + clickedDevCard).setOpacity(0.6);
            }else{
                actionStage.getScene().lookup("#devCard" + selectedDevCards.get(0)).setOpacity(0.6);
                selectedDevCards.remove(0);
                selectedDevCards.add(clickedDevCardValue);
                buyBtn.setDisable(false);
                actionStage.getScene().lookup("#" + clickedDevCard).setOpacity(1);
            }
        }
        buyBtn.setDisable(!(selectedDevCards.size() > 0));
    }



}
