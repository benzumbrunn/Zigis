package ch.benzumbrunn.zigis.dataprovider;

import org.json.JSONException;

/**
 * Created by benzumbrunn on 25.12.17.
 */

public interface IDataProvider<T> {
    T[] getData(String jsonString) throws JSONException;

    String getUrl();
}
