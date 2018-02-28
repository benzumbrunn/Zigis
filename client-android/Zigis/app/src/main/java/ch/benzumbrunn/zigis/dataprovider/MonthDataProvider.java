package ch.benzumbrunn.zigis.dataprovider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.benzumbrunn.zigis.R;
import ch.benzumbrunn.zigis.ZigisUtils;
import ch.benzumbrunn.zigis.data.Month;

/**
 * Created by benzumbrunn on 25.12.17.
 */

public class MonthDataProvider implements IDataProvider<Month> {
    @Override
    public Month[] getData(String jsonString) throws JSONException {
        JSONArray monthsArray = new JSONArray(jsonString);

        final String OWM_MONTH = "month";
        final String OWM_COUNT = "count";

        Month[] months = new Month[monthsArray.length()];

        for (int i = 0; i < monthsArray.length(); i++) {
            JSONObject o = (JSONObject) monthsArray.get(i);
            String date = o.getString(OWM_MONTH);
            String count = o.getString(OWM_COUNT);
            months[i] = ZigisUtils.toMonth(date, count);
        }

        return months;
    }

    @Override
    public String getUrl() {
        return R.string.serveraddress + "/cigarettecounter/cigarettes/months";
    }
}
