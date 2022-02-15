package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the structure and functionality of the Market game component
 */
public class MarketTray {
    private final List<List<Color>> columns;
    private final List<List<Color>> rows;
    private Color slideMarble;

    /**
     * Create a MarketTray with random marbles position and set a random slide marble
     */
    public MarketTray() {
        List<Color> marbles = builder();
        columns = new ArrayList<>();
        rows = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int fromIndex = i * 3;
            int toIndex = fromIndex + 3;
            columns.add(marbles.subList(fromIndex, toIndex));
        }
        rowsSanitize();
        this.slideMarble = marbles.get(12);
    }

    public List<List<Color>> getRows() {
        return rows;
    }

    public Color getSlideMarble() {
        return slideMarble;
    }

    /**
     * @return a list with the exact distribution of the marbles
     */
    private List<Color> builder() {
        List<Color> marbles = new ArrayList<>() {{
            add(Color.WHITE);
            add(Color.WHITE);
            add(Color.WHITE);
            add(Color.WHITE);
            add(Color.BLUE);
            add(Color.BLUE);
            add(Color.YELLOW);
            add(Color.YELLOW);
            add(Color.PURPLE);
            add(Color.PURPLE);
            add(Color.GREY);
            add(Color.GREY);
            add(Color.RED);
        }};// double brace initialization
        Collections.shuffle(marbles);
        return marbles;
    }

    /**
     * Sanitize the rows of the market tray,
     * you have to call this method every time you call insertInColumn
     */
    private void rowsSanitize() {
        this.rows.clear();
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            List<Color> tmp = new ArrayList<>() {{
                add(columns.get(0).get(finalI));
                add(columns.get(1).get(finalI));
                add(columns.get(2).get(finalI));
                add(columns.get(3).get(finalI));
            }};
            this.rows.add(tmp);
        }
    }

    /**
     * Sanitize the columns of the market tray,
     * you have to call this method every time you call insertInRow
     */
    private void columnsSanitize() {
        this.columns.clear();
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            List<Color> tmp = new ArrayList<>() {{
                add(rows.get(0).get(finalI));
                add(rows.get(1).get(finalI));
                add(rows.get(2).get(finalI));
            }};
            this.columns.add(tmp);
        }
    }

    /**
     * @param value (from left to right) 1->first column, 2->second column, 3->third column, 4-> fourth column,
     *              (from top to bottom) 5->first row, 6-> second row, 7-> third row
     * @return the list of colors that represent the chosen column or row
     */
    public List<Color> takeMarble(int value) {
        value -= 1;
        return (value >= 4)
                ? insertInRow(value - 4)
                : insertInColumn(value);
    }

    /**
     * Move the slide Marble at the end of the row and set old top of the row as new slide marble
     *
     * @param value (from top to bottom) 0->first row, 1-> second row, 2-> third row
     * @return the list of colors that represent the chosen row
     */
    private List<Color> insertInRow(int value) {
        List<Color> result = new ArrayList<>(this.rows.get(value));
        List<Color> tmp = new ArrayList<>(this.rows.get(value));
        tmp.add(this.slideMarble);
        this.slideMarble = tmp.get(0);
        tmp.remove(0);
        this.rows.set(value, tmp);
        columnsSanitize();
        return result;
    }

    /**
     * Move the slide Marble at the end of the column and set old top of the column as new slide marble
     *
     * @param value (from left to right) 0->first column, 1->second column, 2->third column, 3-> fourth column,
     * @return the list of colors that represent the chosen column
     */
    private List<Color> insertInColumn(int value) {
        List<Color> result = new ArrayList<>(this.columns.get(value));
        List<Color> tmp = new ArrayList<>(this.columns.get(value));
        tmp.add(this.slideMarble);
        this.slideMarble = tmp.get(0);
        tmp.remove(0);
        this.columns.set(value, tmp);
        rowsSanitize();
        return result;
    }

    @Override
    public String toString() {
        return "{\"MarketTray\":{"
                + "\"rows\":" + rows
                + ", \"slideMarble\":\"" + slideMarble + "\""
                + "}}";
    }
}
