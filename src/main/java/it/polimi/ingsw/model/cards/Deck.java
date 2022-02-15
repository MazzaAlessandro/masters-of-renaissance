package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Represents the structure of the deck of LeaderCards, creates the deck with all LeaderCards in random position
 */
public class Deck {
    private final List<LeaderCard> cards;

    public Deck() {
        this.cards = builder();
        this.cards.forEach(LeaderCard::setRequirements);
        shuffle();
    }


    /**
     * Take data from json and create all LeaderCards
     */
    private List<LeaderCard> builder() {
        Gson gson = new Gson();
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/json/LeaderCard.json")));
        LeaderCard[] leaderCards = gson.fromJson(reader, LeaderCard[].class);

        return new ArrayList<>(Arrays.asList(leaderCards));
    }


    /**
     * shuffle the deck
     */
    private void shuffle() {
        Collections.shuffle(this.cards);
    }


    /**
     * Pick the first card and remove it from the deck
     *
     * @return first card
     */
    public LeaderCard pickCard() {
        LeaderCard result = this.cards.get(0);
        this.cards.remove(0);
        return result;
    }


    public List<LeaderCard> getCards() {
        return cards;
    }

}
