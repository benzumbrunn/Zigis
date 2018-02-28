package ch.benzumbrunn.zigis;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.benzumbrunn.zigis.dataprovider.IDataProvider;

/**
 * Created by benzumbrunn on 25.12.17.
 */

public class FetchCigarettesTask<T> extends AsyncTask<String, Void, T[]> {
    private final String LOG_TAG = FetchCigarettesTask.class.getSimpleName();
    private ArrayAdapter adapter;
    private IDataProvider dataProvider;

    public FetchCigarettesTask(ArrayAdapter adapter, IDataProvider dataProvider) {
        this.adapter = adapter;
        this.dataProvider = dataProvider;
    }


    @Override
    protected T[] doInBackground(String... params) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String jsonString = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            final String CIGARETTECOUNTER_BASEURL =
                    dataProvider.getUrl();

            Uri builtUri = Uri.parse(CIGARETTECOUNTER_BASEURL).buildUpon().build();

            URL url = new URL(builtUri.toString());

            Log.v(LOG_TAG, "Built URI " + builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonString = buffer.toString();

            Log.v(LOG_TAG, "Today string: " + jsonString);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            //T[] last30Data = (T[]) getLast30DataFromJson(todayJsonString);
            T[] data = (T[]) dataProvider.getData(jsonString);
            return data;
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }

    @Override
    protected void onPostExecute(T[] result) {
        if (result != null) {
            adapter.clear();
            for(T t : result) {
                adapter.add(t);
            }
        }
    }
}
