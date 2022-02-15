package it.polimi.ingsw.view.gui.sceneController;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.network.message.request.DefaultProductionOutputRequest;
import it.polimi.ingsw.view.gui.GuiController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles Default production choice of the output resource scene
 */
public class DefaultProductionOutputController extends GuiController {
    List<Color> colors = new ArrayList<>();
    @FXML
    public ImageView BLUE;
    @FXML
    public ImageView PURPLE;
    @FXML
    public ImageView GREY;
    @FXML
    public ImageView YELLOW;
    @FXML
    public Button enterBtn;
    @FXML
    public AnchorPane defaultProductionOutputPane;

    public void initialize(){GuiController.draggableStage(defaultProductionOutputPane, actionStage,null);}


    public void onClick(MouseEvent mouseEvent) {
        String clickedImg = ((ImageView) mouseEvent.getSource()).getId();
        Color clickedColor = Color.WHITE;

        switch (clickedImg){
            case "BLUE":
                clickedColor = Color.BLUE;
                break;
            case "PURPLE":
                clickedColor = Color.PURPLE;
                break;
            case "GREY":
                clickedColor = Color.GREY;
                break;
            case "YELLOW":
                clickedColor = Color.YELLOW;
                break;
            default:
                break;
        }

        if(colors.size()==0){
            colors.add(clickedColor);
            actionStage.getScene().lookup("#" + clickedImg).setOpacity(1);
        } else{
            if(colors.contains(clickedColor)){
                colors.remove(0);
                actionStage.getScene().lookup("#" + clickedImg).setOpacity(0.6);
            }else{
                actionStage.getScene().lookup("#" + colors.get(0).toString()).setOpacity(0.6);
                colors.remove(0);
                colors.add(clickedColor);
                actionStage.getScene().lookup("#" + clickedImg).setOpacity(1);
            }
        }
        enterBtn.setDisable(!(colors.size() > 0));
    }


    public void confirmUpdate() {
        sender.send(new DefaultProductionOutputRequest(nickname, colors));
    }
}
