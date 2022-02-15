package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.model.enumerations.Color;
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
 * Handles show market tray scene
 */
public class MarketTrayController extends GuiController {
    private final List<String> inputMarblePath = new ArrayList<>();
    @FXML
    public AnchorPane marketTayPane;
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
    public void initialize() {
        GuiController.draggableStage(marketTayPane, mainStage, null);
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::backUpdate);
        List<ImageView> marbleArray = new ArrayList<>() {{
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
            marbleArray.get(i).setImage(img);
        }
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(inputMarblePath.get(12))));
        slideMarble.setImage(img);
    }


    public void backUpdate(Event event) {
        backBtn.setDisable(true);
        Platform.runLater(GuiController::fastBackInBoard);
    }
}
