package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.network.message.request.BuyDevSlotPositionRequest;
import it.polimi.ingsw.network.message.request.ShowDevCardBoardSlotsRequest;
import it.polimi.ingsw.network.message.request.ShowOwnedResourcesRequest;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;

/**
 * Handles Development card's choice of the slot scene
 */
public class DevCardSlotController extends GuiController {

    @FXML
    public AnchorPane selectSlotPane;
    @FXML
    public Button slot1Btn;
    @FXML
    public Button slot2Btn;
    @FXML
    public Button slot3Btn;

    public void initialize(){
        GuiController.draggableStage(selectSlotPane, actionStage,null);
    }

    public void onClick(Event event) {
        setAllElementDisable();
        String clickedBtn = ((Control) event.getSource()).getId();
        int numSlot = 7;
        switch (clickedBtn){
            case "slot1Btn":
                numSlot=1;
                break;
            case "slot2Btn":
                numSlot=2;
                break;
            case "slot3Btn":
                numSlot=3;
                break;
            default:
                break;
        }
        sender.send(new BuyDevSlotPositionRequest(nickname, numSlot));
        sender.send(new ShowOwnedResourcesRequest(nickname));
        sender.send(new ShowDevCardBoardSlotsRequest(nickname));

    }

    private void setAllElementDisable(){
        slot1Btn.setDisable(true);
        slot2Btn.setDisable(true);
        slot3Btn.setDisable(true);
    }


}
