package ch.benzumbrunn.zigis;

import java.text.SimpleDateFormat;

import ch.benzumbrunn.zigis.data.Day;
import ch.benzumbrunn.zigis.data.Month;

/**
 * Created by benzumbrunn on 23.12.17.
 */

public class ZigisUtils {

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    public static Day toDay(String rawDate, String rawCount) {
        String date = formatter.format(java.sql.Date.valueOf(rawDate));
        int count = Integer.valueOf(rawCount);
        return new Day(date, count);
    }

    public static Month toMonth(String rawMonth, String rawCount) {
        int count = Integer.valueOf(rawCount);
        return new Month(rawMonth, count);
    }
}
