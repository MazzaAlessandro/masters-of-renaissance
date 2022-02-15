package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import it.polimi.ingsw.model.enumerations.Color;

import java.io.InputStreamReader;
import java.util.*;

/**
 * Create the DevelopmentCardTray randomly, dividing DevelopmentCards in 12 decks of 5 cards,
 * first 4 cards are DevelopmentCards of the same color and level,
 * the one remaining is a fictitious card which says whether or not all cards of a deck are all taken
 */
public class DevCardsTray {
    private final List<List<DevelopmentCard>> tray;
    public DevCardsTray() {
        tray = new ArrayList<>();

        List<DevelopmentCard> same = builder();

        same.add(4, DevelopmentCard.addEmptyCards());
        same.add(9, DevelopmentCard.addEmptyCards());
        same.add(14, DevelopmentCard.addEmptyCards());
        same.add(19, DevelopmentCard.addEmptyCards());
        same.add(24, DevelopmentCard.addEmptyCards());
        same.add(29, DevelopmentCard.addEmptyCards());
        same.add(34, DevelopmentCard.addEmptyCards());
        same.add(39, DevelopmentCard.addEmptyCards());
        same.add(44, DevelopmentCard.addEmptyCards());
        same.add(49, DevelopmentCard.addEmptyCards());
        same.add(54, DevelopmentCard.addEmptyCards());
        same.add(59, DevelopmentCard.addEmptyCards());
        same.add(DevelopmentCard.addEmptyCards());

        for (int i = 0; i < 12; i++) {
            int fromIndex = i * 5;
            int toIndex = fromIndex + 5;

            tray.add(same.subList(fromIndex, toIndex));

            Collections.shuffle(tray.get(i).subList(0, 4));
        }
    }

    /**
     * Take data from json and create all DevelopmentCards
     *
     * @return list of DevelopmentCards
     */
    private List<DevelopmentCard> builder() {

        Gson gson = new Gson();
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/json/DevelopmentCard.json")));
        DevelopmentCard[] devCards = gson.fromJson(reader, DevelopmentCard[].class);

        return new ArrayList<>(Arrays.asList(devCards));
    }

    /**
     * Sets the card as taken
     *
     * @param card identifies the card bought from the Player
     */
    public void buy(DevelopmentCard card) {
        card.setTaken(true);
    }

    public List<List<DevelopmentCard>> getTray() {
        return tray;
    }


    /**
     * @param position identifies in which deck the Player wants to buy a DevelopmentCard
     *
     * @return first card the Player can buy of a certain deck
     */
    public DevelopmentCard select(int position){
        return firstDevCardFree(position);
    }


    /**
     * Implements the action of the Token which makes the Player discard 2 DevelopmentCards
     *
     * @param color says which color of DevelopmentCards the Player has to discard
     */
    public void tokenDiscardDevCards(Color color) {
        int flag=0;

        switch (color) {

            case GREEN:
                for(int i=0; i<3 && flag<2; i++){
                    for(int j=0; j<4 && flag<2; j++){
                        if(!(tray.get(i).get(j).getIsTaken())){
                            tray.get(i).get(j).setTaken(true);
                            flag++;
                        }
                    }
                }
                break;

            case YELLOW:
                for(int i=3; i<6 && flag<2; i++){
                    for(int j=0; j<4 && flag<2; j++){
                        if(!tray.get(i).get(j).getIsTaken()){
                            tray.get(i).get(j).setTaken(true);
                            flag++;
                        }
                    }
                }
                break;

            case PURPLE:
                for(int i=6; i<9 && flag<2; i++){
                    for(int j=0; j<4 && flag<2; j++){
                        if(! tray.get(i).get(j).getIsTaken()){
                            tray.get(i).get(j).setTaken(true);
                            flag++;
                        }
                    }
                }
                break;

            case BLUE:
                for(int i=9; i<12 && flag<2; i++){
                    for(int j=0; j<4 && flag<2; j++){
                        if(! tray.get(i).get(j).getIsTaken()){
                            tray.get(i).get(j).setTaken(true);
                            flag++;
                        }
                    }
                }
                break;

            default: break;
        }
    }


    /**
     *
     * @param position identifies the deck
     *
     * @return first DevelopmentCard free in a certain deck
     */

    public DevelopmentCard firstDevCardFree(int position){
        for(int i=0; i<4; i++){
            if(!tray.get(position).get(i).getIsTaken())
                return tray.get(position).get(i);
        }
        return tray.get(position).get(4);
    }


    /**
     *
     * @param color identifies the color
     *
     * @return a list of DevelopmentCards of the same color
     */
    public List<DevelopmentCard> getColor(Color color){
        List<DevelopmentCard> list = new ArrayList<>();
        switch (color){
            case GREEN:
                for (int i = 0; i < 3; i++) {
                    list.addAll(tray.get(i));
                }
                break;
            case YELLOW:
                for (int i = 3; i < 6; i++) {
                    list.addAll(tray.get(i));
                }
                break;
            case PURPLE:
                for (int i = 6; i < 9; i++) {
                    list.addAll(tray.get(i));
                }
                break;
            case BLUE:
                for (int i = 9; i < 12; i++) {
                    list.addAll(tray.get(i));
                }
                break;
        }
        return list;
    }


    /**
     *
     * @param list list of the four DevelopmentCards in a deck
     *
     * @return first DevelopmentCard free in the deck
     */
    public DevelopmentCard firstDevCardInList(List<DevelopmentCard> list){
        for (DevelopmentCard developmentCard:list) {
            if(!developmentCard.getIsTaken())  return developmentCard;

        }
        return null;
    }

    public List<List<String>> getFreeDevCardsInfo(){
        List <DevelopmentCard> freeDevCards = new ArrayList<>();
        for (List <DevelopmentCard> slot: tray) {
            freeDevCards.add(firstDevCardInList(slot));
        }
        return  DevelopmentCard.getDevCardsPrintInfo(freeDevCards);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<12; i++){
            sb.append(firstDevCardFree(i));
        }

        return sb.toString();
    }
}


