package ch.benzumbrunn.cigarettecounter.cigarettecounter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Month implements Comparable<Month> {

    private String month;
    private int count;

    @JsonIgnore
    private String sortPosition;

    public String getMonth() {
        return month;
    }

    public void setMonth(int monthValue, int yearValue) throws Exception {
        String month;
        switch (monthValue) {
            case 1:
                month = "Januar";
                break;
            case 2:
                month = "Februar";
                break;
            case 3:
                month = "März";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "Mai";
                break;
            case 6:
                month = "Juni";
                break;
            case 7:
                month = "Juli";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "Oktober";
                break;
            case 11:
                month = "November";
                break;
            case 12:
                month = "Dezember";
                break;
            default:
                throw new Exception("Month doesn't exist");
        }
        this.month = month + " " + yearValue;
        this.sortPosition = calcSortPosition(yearValue, monthValue);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSortPosition() {
        return sortPosition;
    }

    private String calcSortPosition(int yearValue, int monthValue) {
        String position = String.valueOf(yearValue);
        if (monthValue < 10) {
            position += "0";
        }
        position += monthValue;
        return position;
    }

    @Override
    public int compareTo(Month o) {
        return this.sortPosition.compareTo(o.getSortPosition());
    }
}
