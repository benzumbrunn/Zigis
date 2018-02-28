package ch.benzumbrunn.zigis.data;

/**
 * Created by benzumbrunn on 25.12.17.
 */

public class Month {

    private String month;
    private int count;

    public Month(String month, int count) {
        this.month = month;
        this.count = count;
    }

    public String getMonth() {
        return month;
    }

    public int getCount() {
        return count;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
