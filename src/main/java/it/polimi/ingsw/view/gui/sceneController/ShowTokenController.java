package it.polimi.ingsw.view.gui.sceneController;


import it.polimi.ingsw.view.gui.GuiController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

/**
 * Handles Lorenzo's faith Track scene for solo mode. This scene also shows the token picked by Lorenzo
 */
public class ShowTokenController extends GuiController {
    String tokenPath;
    String action;
    @FXML
    public AnchorPane showTokenPane;

    @FXML
    public ImageView token;

    @FXML
    public ImageView blackCross;

    @FXML
    public Button closeBtn;

    @FXML
    public Label lorenzoActionLabel;

    private int faithPosition;

    public void setFaithPosition(int faithPosition) {
        this.faithPosition = faithPosition;
    }

    private void setBlackCrossPosition(int position){
        switch (position){
            case 0:
                blackCross.setLayoutX(18);
                blackCross.setLayoutY(194);
                break;
            case 1:
                blackCross.setLayoutX(53);
                blackCross.setLayoutY(194);
                break;
            case 2:
                blackCross.setLayoutX(88);
                blackCross.setLayoutY(194);
                break;
            case 3:
                blackCross.setLayoutX(88);
                blackCross.setLayoutY(159);
                break;
            case 4:
                blackCross.setLayoutX(88);
                blackCross.setLayoutY(124);
                break;
            case 5:
                blackCross.setLayoutX(123);
                blackCross.setLayoutY(124);
                break;
            case 6:
                blackCross.setLayoutX(161);
                blackCross.setLayoutY(124);
                break;
            case 7:
                blackCross.setLayoutX(194);
                blackCross.setLayoutY(124);
                break;
            case 8:
                blackCross.setLayoutX(228);
                blackCross.setLayoutY(124);
                break;
            case 9:
                blackCross.setLayoutX(266);
                blackCross.setLayoutY(124);
                break;
            case 10:
                blackCross.setLayoutX(266);
                blackCross.setLayoutY(157);
                break;
            case 11:
                blackCross.setLayoutX(266);
                blackCross.setLayoutY(192);
                break;
            case 12:
                blackCross.setLayoutX(300);
                blackCross.setLayoutY(192);
                break;
            case 13:
                blackCross.setLayoutX(337);
                blackCross.setLayoutY(192);
                break;
            case 14:
                blackCross.setLayoutX(371);
                blackCross.setLayoutY(192);
                break;
            case 15:
                blackCross.setLayoutX(406);
                blackCross.setLayoutY(192);
                break;
            case 16:
                blackCross.setLayoutX(443);
                blackCross.setLayoutY(192);
                break;
            case 17:
                blackCross.setLayoutX(443);
                blackCross.setLayoutY(159);
                break;
            case 18:
                blackCross.setLayoutX(443);
                blackCross.setLayoutY(123);
                break;
            case 19:
                blackCross.setLayoutX(477);
                blackCross.setLayoutY(123);
                break;
            case 20:
                blackCross.setLayoutX(513);
                blackCross.setLayoutY(123);
                break;
            case 21:
                blackCross.setLayoutX(550);
                blackCross.setLayoutY(123);
                break;
            case 22:
                blackCross.setLayoutX(584);
                blackCross.setLayoutY(123);
                break;
            case 23:
                blackCross.setLayoutX(620);
                blackCross.setLayoutY(123);
                break;
            case 24:
                blackCross.setLayoutX(654);
                blackCross.setLayoutY(123);
                break;
            default:
                break;
        }
    }

    public void setTokenImg(String type){
        switch (type) {
            case "Lorenzo move the Black Cross token forward by 2 spaces.":
                tokenPath = "/images/tokens/moveToken.png";
                action = "Lorenzo move the Black Cross token \nforward by 2 spaces.";

                break;
            case "Lorenzo move the Black Cross token forward by 1 space.\n" +
                    "Then, he shuffle all the Solo Action tokens.":
                tokenPath = "/images/tokens/moveAndShuffleToken.png";
                action = "Lorenzo move the Black Cross token \nforward by 1 space.\n" +
                        "Then, he shuffle all the \nSolo Action tokens.";
                break;
            case "Lorenzo Discard 2 YELLOW Development Cards\n" +
                    "from the bottom of the grid, from the lowest level to the highest":
                tokenPath = "/images/tokens/yellowToken.png";
                action = "Lorenzo Discard 2 YELLOW Development Cards\n" +
                        "from the bottom of the grid, \nfrom the lowest level to the highest";
                break;
            case "Lorenzo Discard 2 BLUE Development Cards\n" +
                    "from the bottom of the grid, from the lowest level to the highest":
                tokenPath = "/images/tokens/blueToken.png";
                action = "Lorenzo Discard 2 BLUE Development Cards\n" +
                        "from the bottom of the grid, \nfrom the lowest level to the highest";
                break;
            case "Lorenzo Discard 2 GREEN Development Cards\n" +
                    "from the bottom of the grid, from the lowest level to the highest":
                tokenPath = "/images/tokens/greenToken.png";
                action = "Lorenzo Discard 2 GREEN Development Cards\n" +
                        "from the bottom of the grid, \nfrom the lowest level to the highest";
                break;
            case "Lorenzo Discard 2 PURPLE Development Cards\n" +
                    "from the bottom of the grid, from the lowest level to the highest":
                tokenPath = "/images/tokens/purpleToken.png";
                action = "Lorenzo Discard 2 PURPLE Development Cards\n" +
                        "from the bottom of the grid, \nfrom the lowest level to the highest";
                break;
            default:
                tokenPath = "";
                break;
        }
    }

    @FXML
    public void initialize(){
        GuiController.draggableStage(showTokenPane, lorenzoStage,null);
        closeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::closeUpdate);

        ImageView tokenImg = token;
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(tokenPath)));
        tokenImg.setImage(img);
        lorenzoActionLabel.setText(action);

        setBlackCrossPosition(faithPosition);
    }

    public void closeUpdate(MouseEvent event){
        closeBtn.setDisable(true);
        GuiController.closeLorenzoStage();
    }

}
