package ch.benzumbrunn.zigisupdater;

import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            Button todayButton = findViewById(R.id.today_button);
            todayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AsyncTask task = new AddOneTask();
                    String[] strings = new String[] {"today"};
                    task.execute(strings);
                }
            });

            Button yesterdayButton = findViewById(R.id.yesterday_button);
            yesterdayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AsyncTask task = new AddOneTask();
                    String[] strings = new String[] {"yesterday"};
                    task.execute(strings);
                }
            });
        }
    }

    public class AddOneTask extends AsyncTask<String, Void, String> {

        private TextView textView;

        @Override
        protected String doInBackground(String... params) {
            String cigarettecounter_url;

            if (params[0] == "today") {
                cigarettecounter_url = R.string.serveraddress + "cigarettecounter/cigarettes/today";
                textView = findViewById(R.id.today_count_textview);
            } else if (params[0] == "yesterday") {
                cigarettecounter_url = R.string.serveraddress + "cigarettecounter/cigarettes/yesterday";
                textView = findViewById(R.id.yesterday_count_textview);
            } else {
                return "";
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String updatedCigarettes = null;

            Uri builtUri = Uri.parse(cigarettecounter_url).buildUpon().build();

            try {
                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                updatedCigarettes = buffer.toString();

                JSONObject o = new JSONObject(updatedCigarettes);
                String count = o.getString("count");

                return count;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // on error:
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                textView.setText(result);
            }
        }
    }
}
