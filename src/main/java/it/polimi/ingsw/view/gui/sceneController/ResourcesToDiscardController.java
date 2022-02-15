package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.network.message.request.MarketDepotsRequest;
import it.polimi.ingsw.network.message.request.ShowOwnedResourcesRequest;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the scene to discard resources when the depot exceeds the allowed number
 */
public class ResourcesToDiscardController extends GuiController {
    private List<Integer> totalResources;
    @FXML
    public Pane resourceDiscardPane;
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
    public TextField amountServant;
    @FXML
    public TextField amountStone;
    @FXML
    public TextField amountCoin;
    @FXML
    public Button enterBtn;

    @FXML
    public void initialize(){
        GuiController.draggableStage(resourceDiscardPane, actionStage,null);
        minusShieldBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::shieldUpdate);
        plusShieldBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::shieldUpdate);
        minusServantBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::servantUpdate);
        plusServantBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::servantUpdate);
        minusStoneBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::stoneUpdate);
        plusStoneBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::stoneUpdate);
        minusCoinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::coinUpdate);
        plusCoinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::coinUpdate);
        enterBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::confirmUpdate);
        amountCoin.setText(totalResources.get(0).toString());
        amountStone.setText(totalResources.get(1).toString());
        amountServant.setText(totalResources.get(2).toString());
        amountShield.setText(totalResources.get(3).toString());
    }

    public void setResourcesAmount(List<Integer> resourcesAmount) {
        this.totalResources = resourcesAmount;
    }

    public void shieldUpdate(Event actionEvent) {

        String clickedBtn = ((Control) actionEvent.getSource()).getId();
        int amount = Integer.parseInt(amountShield.getText());

        switch (clickedBtn){
            case "minusShieldBtn":
                if(amount>0){
                    amount--;

                    amountShield.setText(Integer.toString(amount));
                }
                break;
            case "plusShieldBtn":
                if(amount<totalResources.get(3)){
                    amount++;

                    amountShield.setText(Integer.toString(amount));
                }
                break;
            default:
                break;
        }
    }

    public void servantUpdate(Event actionEvent) {
        String clickedBtn = ((Control) actionEvent.getSource()).getId();
        int amount = Integer.parseInt(amountServant.getText());

        switch (clickedBtn){
            case "minusServantBtn":
                if(amount>0){
                    amount--;
                    amountServant.setText(Integer.toString(amount));
                }
                break;
            case "plusServantBtn":
                if(amount<totalResources.get(2)){
                    amount++;
                    amountServant.setText(Integer.toString(amount));
                }
                break;
            default:
                break;
        }
    }

    public void stoneUpdate(Event actionEvent) {
        String clickedBtn = ((Control) actionEvent.getSource()).getId();
        int amount = Integer.parseInt(amountStone.getText());

        switch (clickedBtn){
            case "minusStoneBtn":
                if(amount>0){
                    amount--;
                    amountStone.setText(Integer.toString(amount));
                }
                break;
            case "plusStoneBtn":
                if(amount<totalResources.get(1)){
                    amount++;
                    amountStone.setText(Integer.toString(amount));
                }
                break;
            default:
                break;
        }
    }

    public void coinUpdate(Event actionEvent) {
        String clickedBtn = ((Control) actionEvent.getSource()).getId();
        int amount = Integer.parseInt(amountCoin.getText());

        switch (clickedBtn){
            case "minusCoinBtn":
                if(amount>0){
                    amount--;
                    amountCoin.setText(Integer.toString(amount));
                }
                break;
            case "plusCoinBtn":
                if(amount<totalResources.get(0)){
                    amount++;
                    amountCoin.setText(Integer.toString(amount));
                }
                break;
            default:
                break;
        }
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
        List <Integer> outList = new ArrayList<>(){{
            add(Integer.parseInt(amountCoin.getText()));
            add(Integer.parseInt(amountStone.getText()));
            add(Integer.parseInt(amountServant.getText()));
            add(Integer.parseInt(amountShield.getText()));
        }};
        sender.send(new MarketDepotsRequest(nickname,totalResources,outList));
        sender.send(new ShowOwnedResourcesRequest(nickname));
    }


}
