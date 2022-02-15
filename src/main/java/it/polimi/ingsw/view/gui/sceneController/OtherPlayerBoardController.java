package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.network.message.request.*;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.List;
import java.util.Objects;

/**
 * Handles the scene which shows other players' board. Including Development cards, resources and faith track
 * of the selected player
 */
public class OtherPlayerBoardController extends GuiController {
    String wantedNickname;
    @FXML
    public Button leadersBtn;
    @FXML
    public Button backBtn;
    @FXML
    public AnchorPane boardPane;
    @FXML
    public ImageView redCross;
    @FXML
    public ImageView depot11;
    @FXML
    public ImageView depot21;
    @FXML
    public ImageView depot22;
    @FXML
    public ImageView depot31;
    @FXML
    public ImageView depot32;
    @FXML
    public ImageView depot33;
    @FXML
    public Label eDepotsLbl;
    @FXML
    public ImageView eDepot1;
    @FXML
    public ImageView eDepot2;
    @FXML
    public ImageView eDepot3;
    @FXML
    public ImageView eDepot4;
    @FXML
    public ImageView dev1slot1;
    @FXML
    public ImageView dev2slot1;
    @FXML
    public ImageView dev3slot1;
    @FXML
    public ImageView dev1slot2;
    @FXML
    public ImageView dev2slot2;
    @FXML
    public ImageView dev3slot2;
    @FXML
    public ImageView dev1slot3;
    @FXML
    public ImageView dev2slot3;
    @FXML
    public ImageView dev3slot3;
    @FXML
    public Label strongShieldLbl;
    @FXML
    public Label strongServantLbl;
    @FXML
    public Label strongStoneLbl;
    @FXML
    public Label strongCoinLbl;
    @FXML
    public ImageView vaticanReport2;
    @FXML
    public ImageView vaticanReport3;
    @FXML
    public ImageView vaticanReport4;


    @FXML
    public void initialize() {
        GuiController.draggableStage(boardPane, mainStage, null);
        leadersBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showUpdate);
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showUpdate);
        updateMasterBoard();
    }

    public void setWantedNickname(String wantedNickname) {
        this.wantedNickname = wantedNickname;
    }

    public void updateMasterBoard(){
        if(wantedNickname!=null) {
            sender.send(new ShowOtherFaithTrackRequest(nickname, wantedNickname));
            sender.send(new ShowOtherOwnedResourcesRequest(nickname, wantedNickname));
            sender.send(new ShowOtherDevCardBoardSlotsRequest(nickname, wantedNickname));
        }
    }

    public void setExtraDepotLabelVisibility(boolean status){
        eDepotsLbl.setVisible(status);
    }

    public void setDevCardSlots(List<List<String>> topCards, List<String> middleCards, List<String> bottomCards){
        dev1slot1.setImage(new Image(topCards.get(7).get(0)));
        dev1slot2.setImage(new Image(topCards.get(7).get(1)));
        dev1slot3.setImage(new Image(topCards.get(7).get(2)));
        dev2slot1.setImage(new Image(middleCards.get(3)));
        dev2slot2.setImage(new Image(middleCards.get(4)));
        dev2slot3.setImage(new Image(middleCards.get(5)));
        dev3slot1.setImage(new Image(bottomCards.get(3)));
        dev3slot2.setImage(new Image(bottomCards.get(4)));
        dev3slot3.setImage(new Image(bottomCards.get(5)));
    }

    public void showUpdate(Event event) {
        String clickedBtn = ((Control) event.getSource()).getId();
        switch (clickedBtn) {

            case "leadersBtn":
                sender.send(new ShowOthersLeaderRequest(nickname, wantedNickname));
                break;

            case "backBtn":
                GuiController.fastBackInBoard();
                break;

            default:
                leadersBtn.setDisable(false);
                backBtn.setDisable(false);
                break;

        }
    }

    public void setStrongBox(List<Integer> input) {
        strongCoinLbl.setText(buildStrongBoxLabel(input.get(0)));
        strongStoneLbl.setText(buildStrongBoxLabel(input.get(1)));
        strongServantLbl.setText(buildStrongBoxLabel(input.get(2)));
        strongShieldLbl.setText(buildStrongBoxLabel(input.get(3)));
    }

    private String buildStrongBoxLabel(int amount) {
        return "x" + amount;
    }

    public void setFaith(int faith) {
        switch (faith) {
            case 0:
                redCross.setLayoutX(33);
                redCross.setLayoutY(115);
                break;
            case 1:
                redCross.setLayoutX(77);
                redCross.setLayoutY(115);
                break;
            case 2:
                redCross.setLayoutX(119);
                redCross.setLayoutY(115);
                break;
            case 3:
                redCross.setLayoutX(119);
                redCross.setLayoutY(72);
                break;
            case 4:
                redCross.setLayoutX(119);
                redCross.setLayoutY(30);
                break;
            case 5:
                redCross.setLayoutX(159);
                redCross.setLayoutY(30);
                break;
            case 6:
                redCross.setLayoutX(199);
                redCross.setLayoutY(30);
                break;
            case 7:
                redCross.setLayoutX(241);
                redCross.setLayoutY(30);
                break;
            case 8:
                redCross.setLayoutX(283);
                redCross.setLayoutY(30);
                break;
            case 9:
                redCross.setLayoutX(328);
                redCross.setLayoutY(30);
                break;
            case 10:
                redCross.setLayoutX(328);
                redCross.setLayoutY(73);
                break;
            case 11:
                redCross.setLayoutX(328);
                redCross.setLayoutY(115);
                break;
            case 12:
                redCross.setLayoutX(368);
                redCross.setLayoutY(115);
                break;
            case 13:
                redCross.setLayoutX(412);
                redCross.setLayoutY(115);
                break;
            case 14:
                redCross.setLayoutX(452);
                redCross.setLayoutY(115);
                break;
            case 15:
                redCross.setLayoutX(495);
                redCross.setLayoutY(115);
                break;
            case 16:
                redCross.setLayoutX(538);
                redCross.setLayoutY(115);
                break;
            case 17:
                redCross.setLayoutX(538);
                redCross.setLayoutY(73);
                break;
            case 18:
                redCross.setLayoutX(538);
                redCross.setLayoutY(30);
                break;
            case 19:
                redCross.setLayoutX(580);
                redCross.setLayoutY(30);
                break;
            case 20:
                redCross.setLayoutX(620);
                redCross.setLayoutY(30);
                break;
            case 21:
                redCross.setLayoutX(660);
                redCross.setLayoutY(30);
                break;
            case 22:
                redCross.setLayoutX(705);
                redCross.setLayoutY(30);
                break;
            case 23:
                redCross.setLayoutX(747);
                redCross.setLayoutY(30);
                break;
            case 24:
                redCross.setLayoutX(789);
                redCross.setLayoutY(30);
                break;
            default:
                break;
        }
    }

    public void setDepotLayer1(List<Integer> input) {
        int sum = input.stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (sum == 1) depot11.setImage(integerImageResource(input));
        else depot11.setImage(null);
    }

    public void setDepotLayer2(List<Integer> input) {
        int sum = input.stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (sum == 1) depot21.setImage(integerImageResource(input));
        else if (sum == 2) {
            depot21.setImage(integerImageResource(input));
            depot22.setImage(integerImageResource(input));
        } else {
            depot21.setImage(null);
            depot22.setImage(null);
        }
    }

    public void setDepotLayer3(List<Integer> input) {
        int sum = input.stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (sum == 1) depot31.setImage(integerImageResource(input));
        else if (sum == 2) {
            depot31.setImage(integerImageResource(input));
            depot32.setImage(integerImageResource(input));
        } else if (sum == 3) {
            depot31.setImage(integerImageResource(input));
            depot32.setImage(integerImageResource(input));
            depot33.setImage(integerImageResource(input));
        } else {
            depot31.setImage(null);
            depot32.setImage(null);
            depot33.setImage(null);
        }
    }

    public void setFirstExtraDepots(List<Integer> input) {
        int sum = input.stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (sum == 1){
            eDepot1.setImage(integerImageResource(input));
            eDepot2.setImage(null);
        }
        else if (sum == 2) {
            eDepot1.setImage(integerImageResource(input));
            eDepot2.setImage(integerImageResource(input));
        } else {
            eDepot1.setImage(null);
            eDepot2.setImage(null);
        }
    }

    public void setSecondExtraDepots(List<Integer> input) {
        int sum = input.stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (sum == 1){
            eDepot3.setImage(integerImageResource(input));
            eDepot4.setImage(null);
        }
        else if (sum == 2) {
            eDepot3.setImage(integerImageResource(input));
            eDepot4.setImage(integerImageResource(input));
        } else {
            eDepot3.setImage(null);
            eDepot4.setImage(null);
        }
    }

    private Image integerImageResource(List<Integer> resources) {
        if (resources.get(0) != 0) return new Image("/images/resources/coin.png");
        else if (resources.get(1) != 0) return new Image("/images/resources/stone.png");
        else if (resources.get(2) != 0) return new Image("/images/resources/servant.png");
        else if (resources.get(3) != 0) return new Image("/images/resources/shield.png");
        else return null;
    }

    public void setVaticanReport(List<Boolean> popeFavors) {

        Image imgVaticanReport2True = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/board/vaticanReport2-true.png")));
        Image imgVaticanReport3True = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/board/vaticanReport3-true.png")));
        Image imgVaticanReport4True = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/board/vaticanReport4-true.png")));

        if (popeFavors.get(0))
            vaticanReport2.setImage(imgVaticanReport2True);
        if (popeFavors.get(1))
            vaticanReport3.setImage(imgVaticanReport3True);
        if (popeFavors.get(2))
            vaticanReport4.setImage(imgVaticanReport4True);

    }
}
