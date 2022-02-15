package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.request.MarketLeadersRequest;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handles the White Marble special power of a Leader card.
 * This scene handles in which the player has to choose the number of white marbles, taken with the market, to convert.
 */
public class NumberOfWhiteToConvertController extends GuiController {
    private List<Color> leadersColor;
    private List<Color> takenColor;
    private int numberOfWhite;
    private int counter = 0;


    @FXML
    public AnchorPane numberOfWhitePane;
    @FXML
    public ImageView marble1;
    @FXML
    public ImageView marble2;
    @FXML
    public TextField amount1;
    @FXML
    public TextField amount2;
    @FXML
    public Button minus1Btn;
    @FXML
    public Button plus1Btn;
    @FXML
    public Button minus2Btn;
    @FXML
    public Button plus2Btn;
    @FXML
    public Button enterBtn;

    public void setLeadersColor(List<Color> leadersColor) {
        this.leadersColor = leadersColor;
    }

    public void setTakenColor(List<Color> takenColor) {
        this.takenColor = takenColor;
    }

    public void initialize(){
        GuiController.draggableStage(numberOfWhitePane, actionStage,null);
        enterBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::confirmUpdate);
        minus1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::marble1Update);
        plus1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::marble1Update);
        minus2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::marble2Update);
        plus2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::marble2Update);
        marble1.setImage(colorToMarbleImage(leadersColor.get(0)));
        marble2.setImage(colorToMarbleImage(leadersColor.get(1)));
        numberOfWhite= Collections.frequency(takenColor,Color.WHITE);
    }

    public void marble1Update(Event actionEvent) {
        String clickedBtn = ((Control) actionEvent.getSource()).getId();
        int amount = Integer.parseInt(amount1.getText());

        switch (clickedBtn){
            case "minus1Btn":
                if(amount>0){
                    amount--;
                    counter--;
                    amount1.setText(Integer.toString(amount));
                }
                break;
            case "plus1Btn":
                if(amount<numberOfWhite){
                    amount++;
                    counter++;
                    amount1.setText(Integer.toString(amount));
                }
                break;
            default:
                break;
        }
        check();
    }

    public void marble2Update(Event actionEvent) {
        String clickedBtn = ((Control) actionEvent.getSource()).getId();
        int amount = Integer.parseInt(amount2.getText());

        switch (clickedBtn){
            case "minus2Btn":
                if(amount>0){
                    amount--;
                    counter--;
                    amount2.setText(Integer.toString(amount));
                }
                break;
            case "plus2Btn":
                if(amount<numberOfWhite){
                    amount++;
                    counter++;
                    amount2.setText(Integer.toString(amount));
                }
                break;
            default:
                break;
        }
        check();
    }

    public void confirmUpdate(Event actionEvent) {
        setAllElementDisable();
        List <Integer> outList = new ArrayList<>();
        outList.add(0,Integer.parseInt(amount1.getText()));
        outList.add(1,Integer.parseInt(amount2.getText()));
        sender.send(new MarketLeadersRequest(nickname,outList,takenColor));
    }

    private void check(){
        enterBtn.setDisable(counter != numberOfWhite);
    }

    private void setAllElementDisable(){
        minus1Btn.setDisable(true);
        minus2Btn.setDisable(true);
        plus1Btn.setDisable(true);
        plus2Btn.setDisable(true);
        enterBtn.setDisable(true);
    }

    private Image colorToMarbleImage(Color color) {
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
        return new Image(imgPath);
    }
}
