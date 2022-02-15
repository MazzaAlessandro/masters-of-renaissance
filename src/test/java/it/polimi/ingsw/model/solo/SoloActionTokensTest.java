package it.polimi.ingsw.model.solo;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SoloActionTokensTest {
    private SoloActionTokens actionTokens;

    @Before
    public void setUp() {
        actionTokens = new SoloActionTokens();
    }

    @Test
    public void pop() {
        SoloActionToken first = actionTokens.pop();
        for (int i = 0; i < 6; i++) {
            actionTokens.pop();
        }
        assertEquals(first,actionTokens.pop());
    }
}