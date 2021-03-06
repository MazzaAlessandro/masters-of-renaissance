package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.StorableResources;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent the Depots as a list of DepotsLayers, handling the correct storing of resources
 */
public class Depots {

    private List<DepotsLayer> layers;

    public List<DepotsLayer> getLayers() {
        return layers;
    }

    public void setLayers(List<DepotsLayer> layers) {
        this.layers = layers;
    }

    /**
     * Constructor of the starting Depots, with 3 empty DepotsLayer with maximum size 1, 2 and 3 respectively
     */
    public Depots(){
        layers = new ArrayList<>();
        layers.add(0, new DepotsLayer(1));
        layers.add(1, new DepotsLayer(2));
        layers.add(2,new DepotsLayer(3));
    }

    /**
     * Converts all the owned resources in the different layers in one singular Resources instance
     * @return the Resources instance representing the Depots
     */
    public Resources getValue(){
        Resources depotsValue = new Resources();
        for (DepotsLayer depotsLayer: layers){
            depotsValue.add(depotsLayer.addToTotal());
        }
        return depotsValue;
    }

    /**
     * Creates a new DepotsLayer for the corresponding resource, specified in the Leader Card
     * @param activatedLeaderCard is the Leader Card with the EXTRADEPOTS ability
     */
    public void addLeaderCardLayer(LeaderCard activatedLeaderCard){
        StorableResources newLayerResource = leaderToLayerType(activatedLeaderCard);
        layers.add(layers.size(), new DepotsLayer(2, newLayerResource));
    }

    /**
     * @return the Resource stored in an extra layer generated by the {@param layerLeaderCard }
     */
    private StorableResources leaderToLayerType(LeaderCard layerLeaderCard){
        StorableResources layerResource = StorableResources.EMPTYLAYER;
        if (layerLeaderCard.getSpecialAbilityColor() == Color.GREY) layerResource = StorableResources.STONES;
        if (layerLeaderCard.getSpecialAbilityColor() == Color.YELLOW) layerResource = StorableResources.COINS;
        if (layerLeaderCard.getSpecialAbilityColor() == Color.BLUE) layerResource = StorableResources.SHIELDS;
        if (layerLeaderCard.getSpecialAbilityColor() == Color.PURPLE) layerResource = StorableResources.SERVANTS;
        return layerResource;
    }

    /**
     * Subtract the resources from the Depots iterating on each layer
     * @param toTake is the resource set that is taken from the Depots
     */
    public void takeResources(Resources toTake){
        for (DepotsLayer depotsLayer: layers){
            depotsLayer.takeFromLayer(toTake);
        }
    }

    /**
     * Stores the resources in the Depots, reordering all the resources to store everything in the correct way
     * It starts storing from the biggest layer to the smaller one
     * @param boughtResources is the new resources set that gets added to the Depots
     */
    public void storeResources(Resources boughtResources){
        int whileIndex = 2;
        boughtResources.add(this.getValue());
        ResetDepots();
        if (layers.size() > 3){
            for(int index = 3; index < layers.size(); index++) {
                if (layers.get(index).getLayerType().equals(StorableResources.COINS))
                    layers.get(index).setAmountStored(Math.min(2, boughtResources.getCoins()));
                if (layers.get(index).getLayerType().equals(StorableResources.STONES))
                    layers.get(index).setAmountStored(Math.min(2, boughtResources.getStones()));
                if (layers.get(index).getLayerType().equals(StorableResources.SERVANTS))
                    layers.get(index).setAmountStored(Math.min(2, boughtResources.getServants()));
                if (layers.get(index).getLayerType().equals(StorableResources.SHIELDS))
                    layers.get(index).setAmountStored(Math.min(2, boughtResources.getShields()));
                boughtResources.subtractAmount(2, layers.get(index).getLayerType());
            }
        }
        while ((whileIndex >= 0) && (boughtResources.getAmount() > 0)){
            StorableResources storingIn = boughtResources.getBiggerResourceType();
            layers.get(whileIndex).setLayerType(storingIn);
            if (layers.get(whileIndex).getLayerType().equals(StorableResources.COINS)){
                layers.get(whileIndex).setAmountStored(boughtResources.getCoins());
                boughtResources.setCoins(0);
            }
            if (layers.get(whileIndex).getLayerType().equals(StorableResources.STONES)){
                layers.get(whileIndex).setAmountStored(boughtResources.getStones());
                boughtResources.setStones(0);
            }
            if (layers.get(whileIndex).getLayerType().equals(StorableResources.SERVANTS)){
                layers.get(whileIndex).setAmountStored(boughtResources.getServants());
                boughtResources.setServants(0);
            }
            if (layers.get(whileIndex).getLayerType().equals(StorableResources.SHIELDS)){
                layers.get(whileIndex).setAmountStored(boughtResources.getShields());
                boughtResources.setShields(0);
            }
            whileIndex--;
        }
    }

    /**
     * Sets to 0 the amount of resources stored in each layer
     * For the standard layers, it also sets its status to EMPTYLAYER
     */
    private void ResetDepots(){
        int index = 0;
        for (DepotsLayer depotsLayer: layers){
            depotsLayer.setAmountStored(0);
            if (index<3) depotsLayer.setLayerType(StorableResources.EMPTYLAYER);
            index++;
        }
    }

    /**
     * @param toStore is the set of Resources that the player want to store in the Depots
     * @return {@code true} if there's enough space to fit {@param toStore} in
     */
    public boolean canBeStored(Resources toStore){
        Resources currentDepotsStatus = this.getValue();
        currentDepotsStatus.add(toStore);
        if (layers.size() > 3){
            for(int index = 3; index < layers.size(); index++) {
                currentDepotsStatus.subtractAmount(2, layers.get(index).getLayerType());
            }
        }
        if (currentDepotsStatus.typeVariety() > 3) return false;
        if (currentDepotsStatus.getAmount() > 6) return false;
        if (currentDepotsStatus.getResourceAmount(currentDepotsStatus.getBiggerResourceType()) > 3) return false;
        if ((currentDepotsStatus.typeVariety() == 2) && (currentDepotsStatus.getAmount() == 6)) return false;
        if ((currentDepotsStatus.typeVariety() == 1) && (currentDepotsStatus.getAmount() > 3)) return false;
        if ((currentDepotsStatus.typeVariety() == 3) && (currentDepotsStatus.getAmount() == 6)){
            return currentDepotsStatus.getResourceAmount(currentDepotsStatus.getBiggerResourceType()) != 2;
        }
        return true;
    }

    /**
     * @return a list of lists, representing the whole Depots with Lists of Integers
     */
    public List<List<Integer>> depotsToLists(){
        List<List<Integer>> result = new ArrayList();
        for(DepotsLayer depotLayer : layers){
            result.add(depotLayer.toList());
        }
        return result;
    }

    @Override
    public String toString() {
        return "{\"Depots\":{"
                + "\"layers\":" + layers
                + "}}";
    }
}
