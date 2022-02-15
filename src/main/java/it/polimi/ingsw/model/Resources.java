package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.StorableResources;
import it.polimi.ingsw.view.cli.Graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A set of five ints that represent all the different types of Resources at the same time
 */
public class Resources{

    private int coins;
    private int stones;
    private int servants;
    private int shields;
    private int faith;


    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getFaith() {
        return faith;
    }

    public void setFaith(int faith) {
        this.faith = faith;
    }

    public int getServants() {
        return servants;
    }

    public void setServants(int servants) {
        this.servants = servants;
    }

    public int getShields() {
        return shields;
    }

    public void setShields(int shields) {
        this.shields = shields;
    }

    public int getStones() {
        return stones;
    }

    public void setStones(int stones) {
        this.stones = stones;
    }

    public void setResources(int coins, int stones, int servants, int shields, int faith) {
        this.coins = coins;
        this.stones = stones;
        this.servants = servants;
        this.shields = shields;
        this.faith = faith;
    }


    /**
     * Resources constructor that already sets the various parameters to the chosen amounts
     * @param coins amount of coins
     * @param stones amount of stones
     * @param servants amount of servants
     * @param shields amount of shields
     * @param faith amount of faith
     */
    public Resources(int coins, int stones, int servants, int shields, int faith) {
        this.coins = coins;
        this.stones = stones;
        this.servants = servants;
        this.shields = shields;
        this.faith = faith;
    }

    /**
     * Resources constructor that sets the faith to 0, while the others are set to the chosen amounts
     * @param coins amount of coins
     * @param stones amount of stones
     * @param servants amount of servants
     * @param shields amounts of shields
     */
    public Resources(int coins, int stones, int servants, int shields) {
        this(coins, stones, servants, shields, 0);
    }

    /**
     * Resources constructor that sets all the parameters to 0
     */
    public Resources() {
        this(0, 0, 0, 0, 0);
    }

    /**
     * @return how many different types of resources are stored in a singular Resources object
     */
    public int typeVariety() {
        int variety = 0;
        if (coins > 0) variety++;
        if (stones > 0) variety++;
        if (servants > 0) variety++;
        if (shields > 0) variety++;
        if (faith > 0) variety++;
        return variety;
    }

    /**
     * Adds all the parameters of the selected instance of Resources to the respective parameters of this instance of Resources
     * @param toAdd is the instance of Resources that is added to this
     */
    public void add(Resources toAdd) {
        this.coins = this.coins + toAdd.getCoins();
        this.stones = this.stones + toAdd.getStones();
        this.servants = this.servants + toAdd.getServants();
        this.shields = this.shields + toAdd.getShields();
        this.faith = this.faith + toAdd.getFaith();
    }

    /**
     * Subtract all the parameters of the selected instance of Resources from the respective parameters of this instance of Resources
     * If a parameter tries to get below 0, it is set to 0
     * @param toSubtract is the instance of Resources that is subtracted from this
     */
    public void subtract(Resources toSubtract) {
        this.coins = this.coins - toSubtract.getCoins();
        if (this.coins < 0) this.coins = 0;
        this.stones = this.stones - toSubtract.getStones();
        if (this.stones < 0) this.stones = 0;
        this.servants = this.servants - toSubtract.getServants();
        if (this.servants < 0) this.servants = 0;
        this.shields = this.shields - toSubtract.getShields();
        if (this.shields < 0) this.shields = 0;
        this.faith = this.faith - toSubtract.getFaith();
        if (this.faith < 0) this.faith = 0;
    }

    /**
     * Subtract a certain amount of the selected resource from this set of Resources
     * Subtracting the minimum between amount and the respective resource assures that the resource doesn't get below 0
     * @param amount is the amount subtracted
     * @param resourceTaken is the resource that gets subtracted
     */
    public void subtractAmount(int amount, StorableResources resourceTaken) {
        if (resourceTaken == StorableResources.COINS) this.coins = this.coins - Math.min(amount, this.coins);
        if (resourceTaken == StorableResources.STONES) this.stones = this.stones - Math.min(amount, this.stones);
        if (resourceTaken == StorableResources.SERVANTS) this.servants = this.servants - Math.min(amount, this.servants);
        if (resourceTaken == StorableResources.SHIELDS) this.shields = this.shields - Math.min(amount, this.shields);
    }

    /**
     * @return the number of total resources stored in this instance
     * Faith isn't counted since it is always subtracted before being stored
     */
    public int getAmount() {
        int total;
        total = this.coins + this.stones + this.servants + this.shields;
        return total;
    }

    /**
     * @return the StorableResources with the highest amount stored.
     * If there are two different parameters with the highest amount, it returns the first one (following the order: coins->stones->servants->shield)
     * Faith isn't counted since it is always subtracted before being stored
     */
    public StorableResources getBiggerResourceType() {
        List<Integer> resourceList = new ArrayList<>();
        resourceList.add(0, this.coins);
        resourceList.add(1, this.stones);
        resourceList.add(2, this.servants);
        resourceList.add(3, this.shields);
        int maxvalue = Collections.max(resourceList);
        return StorableResources.values()[resourceList.indexOf(maxvalue)];
    }

    /**
     * @param storedAmount is the chosen resource type
     * @return how many of the chosen resources are stored in this instance of Resources
     */
    public int getResourceAmount(StorableResources storedAmount) {
        int result = 0;
        if (storedAmount.equals(StorableResources.COINS)) result = this.coins;
        if (storedAmount.equals(StorableResources.SERVANTS)) result = this.servants;
        if (storedAmount.equals(StorableResources.SHIELDS)) result = this.shields;
        if (storedAmount.equals(StorableResources.STONES)) result = this.stones;
        return result;
    }

    /**
     * @param compare is the resources to compare
     * @return {@code true} if {@param compare} is less than the resource that calls the method
     */
    public boolean biggerThan(Resources compare) {
        if (this.coins < compare.getCoins()) return false;
        else if (this.servants < compare.getServants()) return false;
        else if (this.shields < compare.getShields()) return false;
        else return this.stones >= compare.getStones();
    }


    /**
     * @param compare is the resource to compare
     * @return {@code true} if {@param compare} has the same amount of each resource
     */
    public boolean equals(Resources compare) {
        if (this == compare) return true;
        return this.coins == compare.getCoins() && this.stones == compare.getStones() && this.servants == compare.getServants() && this.shields == compare.getShields() && this.faith == compare.getFaith();
    }

    /**
     * @return {@code true} if the resource set is empty
     */
    public boolean isEmpty(){
        Resources emptyResourceSet = new Resources();
        return  this.equals(emptyResourceSet);
    }


    /**
     * @return a list of integer used to represent resources in view,
     * the correct order of elements is coins,stones,servants,shields,faith
     */
    public List<Integer> resourceToInteger(){
        List<Integer> list = new ArrayList<>();
        list.add(this.coins);
        list.add(this.stones);
        list.add(this.servants);
        list.add(this.shields);
        list.add(this.faith);
        return list;
    }


    /**
     * @return Convert resources into String of colored balls for a compact and simple view in CLI
     */
    public String resourceToFancyString(){
        StringBuilder builder = new StringBuilder();

        if(coins > 0){
            builder.append(coins).append(Graphics.ANSI_YELLOW).append("●").append(Graphics.ANSI_RESET);
        }

        if(servants > 0){
            builder.append(servants).append(Graphics.ANSI_PURPLE).append("●").append(Graphics.ANSI_RESET);
        }

        if(stones > 0){
            builder.append(stones).append(Graphics.ANSI_GREY).append("●").append(Graphics.ANSI_RESET);
        }

        if(shields > 0){
            builder.append(shields).append(Graphics.ANSI_BLUE).append("●").append(Graphics.ANSI_RESET);
        }

        if(faith > 0){
            builder.append(faith).append(Graphics.ANSI_RED).append("●").append(Graphics.ANSI_RESET);
        }

        return builder.toString();

    }


    @Override
    public String toString() {
        return "{\"Resources\":{"
                + "\"coins\":\"" + coins + "\""
                + ",\"stones\":\"" + stones + "\""
                + ",\"servants\":\"" + servants + "\""
                + ",\"shields\":\"" + shields + "\""
                + ",\"faith\":\"" + faith + "\""
                + "}}";
    }
}
