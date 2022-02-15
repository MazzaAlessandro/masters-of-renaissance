package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Resources;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.SpecialAbility;

public class BuyDevCardDiscount implements  BuyDevCardStrategy{

    @Override
    public void buyDevCard(DevelopmentCard toBuy, Board playerBoard) {
        Resources cardCost = new Resources(toBuy.getCost().getCoins(), toBuy.getCost().getStones(), toBuy.getCost().getServants(), toBuy.getCost().getShields());
        for (LeaderCard ownedLeaderCard: playerBoard.getLeaderCards()){
            if (ownedLeaderCard.getSpecialAbility() == SpecialAbility.DISCOUNT) discountCost(cardCost, ownedLeaderCard.getSpecialAbilityColor());
        }
        playerBoard.spendResources(cardCost);
    }

    private void discountCost(Resources toDiscount, Color colorDiscount){
        if ((colorDiscount == Color.YELLOW) && (toDiscount.getCoins() > 0)) toDiscount.setCoins(toDiscount.getCoins() - 1);
        if ((colorDiscount == Color.BLUE) && (toDiscount.getShields() > 0)) toDiscount.setShields(toDiscount.getShields() - 1);
        if ((colorDiscount == Color.GREY) && (toDiscount.getStones() > 0)) toDiscount.setStones(toDiscount.getStones() - 1);
        if ((colorDiscount == Color.PURPLE) && (toDiscount.getServants() > 0)) toDiscount.setServants(toDiscount.getServants() - 1);
    }

}
