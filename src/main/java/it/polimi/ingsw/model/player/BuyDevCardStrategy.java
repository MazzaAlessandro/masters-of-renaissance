package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.DevelopmentCard;

public interface BuyDevCardStrategy {

    public void buyDevCard(DevelopmentCard toBuy, Board playerBoard);

}
