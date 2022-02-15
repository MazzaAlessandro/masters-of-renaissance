package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.enumerations.StorableResources;

import java.util.ArrayList;
import java.util.List;

/**
 * A DepotsLayer is composed of two ints, representing the current amount stored and the maximum amount storable, and a StorableResource that set the type of resource stored in the layer
 * It is used for the 3 standard layers in the Depots and for the EXTRADEPOTS special ability
 */
public class DepotsLayer {

    private int amountStored;
    private int maxStorable;
    private StorableResources layerType;

    /**
     * DepotsLayer constructor that set the maxStorable, while the amount stored is set to 0 and the layerResource is EMPTYLAYER
     * @param layerLevel is the maxStorable for the layer
     */
    public DepotsLayer(int layerLevel){
        this(layerLevel, StorableResources.EMPTYLAYER);
    }

    /**
     * DepotsLayer constructor that set the maxStorable and the type of resource stored, while the amount stored is set to 0
     * @param layerLevel is the maxStorable for the layer
     * @param layerResource is the kind of resource the layer will store
     */
    public DepotsLayer(int layerLevel, StorableResources layerResource){
        this.amountStored = 0;
        this.maxStorable = layerLevel;
        this.layerType = layerResource;
    }

    public int getAmountStored() {
        return amountStored;
    }

    public int getMaxStorable() {
        return maxStorable;
    }

    public StorableResources getLayerType() {
        return layerType;
    }

    public void setLayerType(StorableResources layerType) {
        this.layerType = layerType;
    }

    public void setAmountStored(int amountStored) {
        this.amountStored = amountStored;
    }

    public void setMaxStorable(int maxStorable) {
        this.maxStorable = maxStorable;
    }

    /**
     * @return a Resources instance with the right amount stored into the layer
     */
    public Resources addToTotal(){
        Resources layerValue = new Resources();
        if(this.layerType==StorableResources.COINS) { layerValue.setCoins(this.amountStored); }
        else if(this.layerType==StorableResources.SERVANTS) { layerValue.setServants(this.amountStored); }
        else if(this.layerType==StorableResources.STONES) { layerValue.setStones(this.amountStored); }
        else if(this.layerType==StorableResources.SHIELDS) { layerValue.setShields(this.amountStored); }
        return layerValue;
    }

    /**
     * @return {@code true} if the layer is of the EMPTYLAYER kind
     */
    public boolean isEmpty(){
        return this.getLayerType() == StorableResources.EMPTYLAYER;
    }

    /**
     * Subtract from the layer the respective resource
     * If amountStored gets to 0, the layerType is set to EMPTYLAYER
     * @param toTake is the resource set that is taken from the Depots in general
     */
    public void takeFromLayer(Resources toTake){
        int amountTaken = 0;
        if (this.layerType == StorableResources.COINS){
            amountTaken = Math.min(this.amountStored, toTake.getCoins());
            toTake.subtractAmount(amountTaken, StorableResources.COINS);
        }
        if (this.layerType == StorableResources.STONES){
            amountTaken = Math.min(this.amountStored, toTake.getStones());
            toTake.subtractAmount(amountTaken, StorableResources.STONES);
        }
        if (this.layerType == StorableResources.SERVANTS){
            amountTaken = Math.min(this.amountStored, toTake.getServants());
            toTake.subtractAmount(amountTaken, StorableResources.SERVANTS);
        }
        if (this.layerType == StorableResources.SHIELDS){
            amountTaken = Math.min(this.amountStored, toTake.getShields());
            toTake.subtractAmount(amountTaken, StorableResources.SHIELDS);
        }
        this.amountStored = this.amountStored - amountTaken;
        //if (this.amountStored<=0) this.layerType = StorableResources.EMPTYLAYER;
        if (this.amountStored < 0) this.amountStored = 0;
    }

    /**
     * @return a list of integer representing the amount of each resources stored
     */
    public List<Integer> toList(){
        List<Integer> result = new ArrayList<>();
        if (this.layerType == StorableResources.COINS) result.add(this.amountStored);
        else result.add(0);
        if (this.layerType == StorableResources.STONES) result.add(this.amountStored);
        else result.add(0);
        if (this.layerType == StorableResources.SERVANTS) result.add(this.amountStored);
        else result.add(0);
        if (this.layerType == StorableResources.SHIELDS) result.add(this.amountStored);
        else result.add(0);
        return result;
    }

    @Override
    public String toString() {
        return "\"DepotsLayer\":\""
                + amountStored + "\""
                + "}";
    }
}
