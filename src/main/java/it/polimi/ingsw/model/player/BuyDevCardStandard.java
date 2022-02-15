package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.DevelopmentCard;

public class BuyDevCardStandard implements  BuyDevCardStrategy{

    @Override
    public void buyDevCard(DevelopmentCard toBuy, Board playerBoard) {
        playerBoard.spendResources(toBuy.getCost());
    }

}
