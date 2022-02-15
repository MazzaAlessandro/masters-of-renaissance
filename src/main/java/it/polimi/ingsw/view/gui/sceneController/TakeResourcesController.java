package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.request.MarketPositionRequest;
import it.polimi.ingsw.network.message.request.ShowFaithTrackRequest;
import it.polimi.ingsw.network.message.request.ShowOwnedResourcesRequest;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Handle the scene to take resources from the market
 */
public class TakeResourcesController extends GuiController {
    List<String> inputMarblePath = new ArrayList<>();
    private int position = 0;
    @FXML
    public AnchorPane takeResourcesPane;
    @FXML
    public ImageView slideMarble;
    @FXML
    public ImageView marble00;
    @FXML
    public ImageView marble01;
    @FXML
    public ImageView marble02;
    @FXML
    public ImageView marble03;
    @FXML
    public ImageView marble10;
    @FXML
    public ImageView marble11;
    @FXML
    public ImageView marble12;
    @FXML
    public ImageView marble13;
    @FXML
    public ImageView marble20;
    @FXML
    public ImageView marble21;
    @FXML
    public ImageView marble22;
    @FXML
    public ImageView marble23;

    @FXML
    public ImageView position1;
    @FXML
    public ImageView position2;
    @FXML
    public ImageView position3;
    @FXML
    public ImageView position4;
    @FXML
    public ImageView position5;
    @FXML
    public ImageView position6;
    @FXML
    public ImageView position7;

    @FXML
    public Button confirmBtn;
    @FXML
    public Button backBtn;

    public void setMarbleImage(Color color) {
        String imgPath;
        switch (color) {
            case RED:
                imgPath = "/images/market/Marble/MarbleRed.png";
                break;
            case GREY:
                imgPath = "/images/market/Marble/MarbleGrey.png";
                break;
            case BLUE:
                imgPath = "/images/market/Marble/MarbleBlue.png";
                break;
            case PURPLE:
                imgPath = "/images/market/Marble/MarblePurple.png";
                break;
            case YELLOW:
                imgPath = "/images/market/Marble/MarbleYellow.png";
                break;
            default:
                imgPath = "/images/market/Marble/MarbleWhite.png";
                break;
        }
        inputMarblePath.add(imgPath);
    }

    @FXML
    public void initialize(){
        confirmBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::confirmUpdate);
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::backUpdate);

        position1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::positionUpdate);
        position2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::positionUpdate);
        position3.addEventHandler(MouseEvent.MOUSE_CLICKED, this::positionUpdate);
        position4.addEventHandler(MouseEvent.MOUSE_CLICKED, this::positionUpdate);
        position5.addEventHandler(MouseEvent.MOUSE_CLICKED, this::positionUpdate);
        position6.addEventHandler(MouseEvent.MOUSE_CLICKED, this::positionUpdate);
        position7.addEventHandler(MouseEvent.MOUSE_CLICKED, this::positionUpdate);

        GuiController.draggableStage(takeResourcesPane, actionStage,null);

        List<ImageView> marbles = new ArrayList<>(){{
            add(marble00);
            add(marble01);
            add(marble02);
            add(marble03);
            add(marble10);
            add(marble11);
            add(marble12);
            add(marble13);
            add(marble20);
            add(marble21);
            add(marble22);
            add(marble23);
        }};
        for (int i = 0; i < 12; i++) {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(inputMarblePath.get(i))));
            marbles.get(i).setImage(img);
        }
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(inputMarblePath.get(12))));
        slideMarble.setImage(img);
    }

    public void positionUpdate(Event event){
        String clickedPosition = ((ImageView) event.getSource()).getId();
        switch (clickedPosition){
            case "position1":
                position = 1;
                break;
            case "position2":
                position = 2;
                break;
            case "position3":
                position = 3;
                break;
            case "position4":
                position = 4;
                break;
            case "position5":
                position = 5;
                break;
            case "position6":
                position = 6;
                break;
            case "position7":
                position = 7;
                break;
            default:break;
        }

        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/market/square.png")));
        slideMarble.setVisible(false);
        position1.setImage(img);
        position2.setImage(img);
        position3.setImage(img);
        position4.setImage(img);
        position5.setImage(img);
        position6.setImage(img);
        position7.setImage(img);
        ((ImageView) actionStage.getScene().lookup("#" + clickedPosition)).setImage(slideMarble.getImage());
        confirmBtn.setDisable(false);
    }

    public void backUpdate(Event event) {
        confirmBtn.setDisable(true);
        backBtn.setDisable(true);
        GuiController.layoutSwapper(actionStage.getScene(),"/fxml/turnActionScene.fxml",600,600);
    }

    public void confirmUpdate(Event event) {
        confirmBtn.setDisable(true);
        backBtn.setDisable(true);
        sender.send(new MarketPositionRequest(nickname,position));
        sender.send(new ShowFaithTrackRequest(nickname));
        sender.send(new ShowOwnedResourcesRequest(nickname));

    }
}
