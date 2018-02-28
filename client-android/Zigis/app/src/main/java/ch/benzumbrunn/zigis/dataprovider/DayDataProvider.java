package ch.benzumbrunn.zigis.dataprovider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.benzumbrunn.zigis.R;
import ch.benzumbrunn.zigis.data.Day;
import ch.benzumbrunn.zigis.ZigisUtils;

/**
 * Created by benzumbrunn on 25.12.17.
 */

public class DayDataProvider implements IDataProvider<Day> {
    @Override
    public Day[] getData(String jsonString) throws JSONException {
        JSONArray last30DaysArray = new JSONArray(jsonString);

        final String OWM_DATE = "date";
        final String OWM_COUNT = "count";

        Day[] last30Days = new Day[last30DaysArray.length()];

        for (int i = 0; i < last30DaysArray.length(); i++) {
            JSONObject o = (JSONObject) last30DaysArray.get(i);
            String date = o.getString(OWM_DATE);
            String count = o.getString(OWM_COUNT);
            last30Days[i] = ZigisUtils.toDay(date, count);
        }

        return last30Days;
    }

    @Override
    public String getUrl() {
        return R.string.serveraddress + "/cigarettecounter/cigarettes/last30";
    }
}
