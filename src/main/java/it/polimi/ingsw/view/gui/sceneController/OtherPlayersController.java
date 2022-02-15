package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.view.gui.GuiController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.List;

/**
 * Handles the choice of whose other player's board to show
 */
public class OtherPlayersController extends GuiController {

    @FXML
    public AnchorPane otherPlayersPane;
    @FXML
    public Label noOtherLbl;
    @FXML
    public Button player1Btn;
    @FXML
    public Button player2Btn;
    @FXML
    public Button player3Btn;
    @FXML
    public Button backBtn;

    private List<String> players;

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    @FXML
    public void initialize(){
        GuiController.draggableStage(otherPlayersPane, mainStage, null);
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::goBack);
        switch (players.size()){
            case 0:
                noOtherLbl.setVisible(true);
                player1Btn.setVisible(false);
                player2Btn.setVisible(false);
                player3Btn.setVisible(false);
                break;
            case 1:
                player1Btn.setText(players.get(0));
                player1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showPlayer);
                player1Btn.setVisible(true);
                player2Btn.setVisible(false);
                player3Btn.setVisible(false);
                break;

            case 2:
                player1Btn.setText(players.get(0));
                player1Btn.setVisible(true);
                player1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showPlayer);
                player2Btn.setText(players.get(1));
                player2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showPlayer);
                player2Btn.setVisible(true);
                player3Btn.setVisible(false);
                break;

            case 3:
                player1Btn.setText(players.get(0));
                player1Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showPlayer);
                player1Btn.setVisible(true);
                player2Btn.setText(players.get(1));
                player2Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showPlayer);
                player2Btn.setVisible(true);
                player3Btn.setText(players.get(2));
                player3Btn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showPlayer);
                player3Btn.setVisible(true);
                break;
        }
    }

    public void showPlayer(Event event){
        disableAll();
        Button clickedBtn = ((Button) event.getSource());
        GuiController.updateOtherBoard();
        GuiController.getOtherBoardController().setWantedNickname(clickedBtn.getText());
        GuiController.getOtherBoardController().updateMasterBoard();
    }

    public void goBack(Event event){
        GuiController.fastBackInBoard();
    }

    private void disableAll(){
        player1Btn.setDisable(true);
        player2Btn.setDisable(true);
        player3Btn.setDisable(true);
    }
}
