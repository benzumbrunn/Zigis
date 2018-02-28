package ch.benzumbrunn.zigis.data;

/**
 * Created by benzumbrunn on 23.12.17.
 */

public class Day {
    private String date;
    private int count;

    public Day(String date, int count) {
        this.date = date;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public int getCount() {
        return count;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return date + ": " + count;
    }
}
