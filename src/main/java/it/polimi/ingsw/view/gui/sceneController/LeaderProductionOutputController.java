package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.request.LeaderProductionRequest;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles Leader cards production choice of the output resource scene
 */
public class LeaderProductionOutputController extends GuiController {
    private List<Integer> leaders;
    private int resourcesAmount = 0;
    private int counter = 0;

    @FXML
    public Button enterBtn;
    @FXML
    public Button minusShieldBtn;
    @FXML
    public Button plusShieldBtn;
    @FXML
    public Button minusServantBtn;
    @FXML
    public Button plusServantBtn;
    @FXML
    public Button minusStoneBtn;
    @FXML
    public Button plusStoneBtn;
    @FXML
    public Button minusCoinBtn;
    @FXML
    public Button plusCoinBtn;
    @FXML
    public TextField amountShield;
    @FXML
    public TextField amountStone;
    @FXML
    public TextField amountCoin;
    @FXML
    public TextField amountServant;
    @FXML
    public AnchorPane leaderProductionOutputPane;
    @FXML
    public Label productionLabel;

    public void setNumberOfLeaders(List<Integer> leaders){
        this.leaders = leaders;
        resourcesAmount = leaders.size();
    }

    public void initialize(){
        GuiController.draggableStage(leaderProductionOutputPane, mainStage,null);
        minusShieldBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::shieldUpdate);
        plusShieldBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::shieldUpdate);
        minusServantBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::servantUpdate);
        plusServantBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::servantUpdate);
        minusStoneBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::stoneUpdate);
        plusStoneBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::stoneUpdate);
        minusCoinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::coinUpdate);
        plusCoinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::coinUpdate);
        enterBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::confirmUpdate);
        if(resourcesAmount==1) {
            productionLabel.setText("Select one Leader Production Output Resource:");
        }else{
            productionLabel.setText("Select two Leader Production Output Resources:");
        }
    }

    public void shieldUpdate(Event actionEvent) {
        String clickedBtn = ((Control) actionEvent.getSource()).getId();
        int amount = Integer.parseInt(amountShield.getText());

        switch (clickedBtn){
            case "minusShieldBtn":
                if(amount>0){
                    amount--;
                    counter--;
                    amountShield.setText(Integer.toString(amount));
                }
                break;
            case "plusShieldBtn":
                if(amount<resourcesAmount){
                    amount++;
                    counter++;
                    amountShield.setText(Integer.toString(amount));
                }
                break;
            default:
                break;
        }
        check();
    }

    public void servantUpdate(Event actionEvent) {
        String clickedBtn = ((Control) actionEvent.getSource()).getId();
        int amount = Integer.parseInt(amountServant.getText());

        switch (clickedBtn){
            case "minusServantBtn":
                if(amount>0){
                    amount--;
                    counter--;
                    amountServant.setText(Integer.toString(amount));
                }
                break;
            case "plusServantBtn":
                if(amount<resourcesAmount){
                    amount++;
                    counter++;
                    amountServant.setText(Integer.toString(amount));
                }
                break;
            default:
                break;
        }
        check();
    }

    public void stoneUpdate(Event actionEvent) {
        String clickedBtn = ((Control) actionEvent.getSource()).getId();
        int amount = Integer.parseInt(amountStone.getText());

        switch (clickedBtn){
            case "minusStoneBtn":
                if(amount>0){
                    amount--;
                    counter--;
                    amountStone.setText(Integer.toString(amount));
                }
                break;
            case "plusStoneBtn":
                if(amount<resourcesAmount){
                    amount++;
                    counter++;
                    amountStone.setText(Integer.toString(amount));
                }
                break;
            default:
                break;
        }
        check();
    }

    public void coinUpdate(Event actionEvent) {
        String clickedBtn = ((Control) actionEvent.getSource()).getId();
        int amount = Integer.parseInt(amountCoin.getText());

        switch (clickedBtn){
            case "minusCoinBtn":
                if(amount>0){
                    amount--;
                    counter--;
                    amountCoin.setText(Integer.toString(amount));
                }
                break;
            case "plusCoinBtn":
                if(amount<resourcesAmount){
                    amount++;
                    counter++;
                    amountCoin.setText(Integer.toString(amount));
                }
                break;
            default:
                break;
        }
        check();
    }

    private void check(){
        enterBtn.setDisable(counter != resourcesAmount);
    }

    private void setAllElementDisable(){
        minusCoinBtn.setDisable(true);
        plusCoinBtn.setDisable(true);
        minusServantBtn.setDisable(true);
        plusServantBtn.setDisable(true);
        minusShieldBtn.setDisable(true);
        plusShieldBtn.setDisable(true);
        minusStoneBtn.setDisable(true);
        plusStoneBtn.setDisable(true);
    }

    public void confirmUpdate(Event event) {
        setAllElementDisable();
        List<Color> colors = new ArrayList<>();

        for (int i = 0; i < Integer.parseInt(amountStone.getText()) && Integer.parseInt(amountStone.getText())!=0; i++) {
            colors.add(Color.GREY);
        }

        for (int i = 0; i < Integer.parseInt(amountServant.getText()) && Integer.parseInt(amountServant.getText())!=0; i++) {
            colors.add(Color.PURPLE);
        }

        for (int i = 0; i < Integer.parseInt(amountShield.getText()) && Integer.parseInt(amountShield.getText())!=0; i++) {
            colors.add(Color.BLUE);
        }

        for (int i = 0; i < Integer.parseInt(amountCoin.getText()) && Integer.parseInt(amountCoin.getText())!=0; i++) {
            colors.add(Color.YELLOW);
        }

        sender.send(new LeaderProductionRequest(nickname, colors, leaders));
    }
}
