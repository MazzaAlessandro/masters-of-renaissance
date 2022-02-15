package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Resources;


/**
 * Represents productions of DevelopmentCards
 */
public class ProductionPower {
    private Resources inputResources;
    private Resources outputResources;

    public ProductionPower(Resources inputResources, Resources outputResources) {
        this.inputResources = inputResources;
        this.outputResources = outputResources;
    }

    public Resources getInputResources(){
        return inputResources;
    }

    public Resources getOutputResources(){
        return outputResources;
    }

    public void setInputResources(Resources inputResources) {
        this.inputResources = inputResources;
    }

    public void setOutputResources(Resources outputResources) {
        this.outputResources = outputResources;
    }


    @Override
    public String toString() {
        return "ProductionPower{" +
                "inputResources=" + inputResources +
                ", outputResources=" + outputResources +
                '}';
    }
}
