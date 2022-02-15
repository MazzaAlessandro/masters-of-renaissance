package it.polimi.ingsw.model.solo;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.util.*;

/**
 * Represents a stack of SoloActionToken
 */
public class SoloActionTokens {
    private List<SoloActionToken> stack;

    public SoloActionTokens() {
        this.stack = new ArrayList<>();
        builder();
    }

    /**
     * Make first token last
     *
     * @return the old first token on stack
     */
    public SoloActionToken pop() {
        SoloActionToken pickedToken = this.stack.get(0);
        putTopBottom();
        return pickedToken;
    }

    /**
     * Move first SoloActionToken at the bottom of the stack
     */
    private void putTopBottom() {
        SoloActionToken tmp = this.stack.get(0);
        this.stack.add(tmp);
        this.stack.remove(0);
    }

    /**
     * Take data from json and create all SoloActionTokens
     */
    private void builder() {
        Gson gson = new Gson();
        InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/json/SoloActionTokens.json")));
        SoloActionToken[] actionToken = gson.fromJson(reader, SoloActionToken[].class);
        this.stack = new ArrayList<>(Arrays.asList(actionToken));
        stack.forEach(SoloActionToken::setEffect);
        shuffle();
    }

    /**
     * Shuffle the stack
     */
    public void shuffle() {
        Collections.shuffle(this.stack);
    }

    /**
     * @return the current situation of stack, in json like format
     */
    @Override
    public String toString() {
        return String.join(",", this.stack.toString());
    }
}
