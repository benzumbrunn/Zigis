package ch.benzumbrunn.cigarettecounter.cigarettecounter.web;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtils {
    /**
     * Helper method to get the SQL Date of today.
     * @return
     */
    public static Date getTodayDate() {
        return new Date(java.util.Date.from(Instant.now()).getTime());
    }

    /**
     * Helper method to get the SQL Date of yesterday.
     * @return
     */
    public static Date getYesterdayDate() {
        LocalDate localYesterday = LocalDate.now().minusDays(1L);
        return java.sql.Date.valueOf(localYesterday.toString());
    }

    public static List<Date> getLast30Dates() {
        Date thirtyDaysAgo = java.sql.Date.valueOf(LocalDate.now().minusDays(29L));
        Date today = new Date(java.util.Date.from(Instant.now()).getTime());
        return getDaysBetweenDates(thirtyDaysAgo, today);
    }

    private static List<Date> getDaysBetweenDates(Date startDate, Date endDate)
    {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate))
        {
            Date result = new Date(calendar.getTime().getTime());
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

}
