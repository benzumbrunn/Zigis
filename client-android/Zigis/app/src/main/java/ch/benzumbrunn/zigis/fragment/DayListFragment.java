package ch.benzumbrunn.zigis.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ch.benzumbrunn.zigis.adapter.DayAdapter;
import ch.benzumbrunn.zigis.dataprovider.DayDataProvider;
import ch.benzumbrunn.zigis.dataprovider.IDataProvider;
import ch.benzumbrunn.zigis.data.Day;
import ch.benzumbrunn.zigis.FetchCigarettesTask;
import ch.benzumbrunn.zigis.R;

/**
 * Created by benzumbrunn on 23.12.17.
 */

public class DayListFragment extends ListFragment {

    private DayAdapter dayAdapter;
    private IDataProvider<Day> dayDataProvider;

    public DayListFragment() {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchCigarettesTask<Day> fetchCigarettesTask = new FetchCigarettesTask<Day>(dayAdapter, dayDataProvider);
            fetchCigarettesTask.execute();
            return true;
        }
        /*
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), ZigisPreferenceActivity.class);
            startActivity(intent);
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<Day> dayCount = new ArrayList<>();

        dayAdapter =
                //new ArrayAdapter<String>(
                new DayAdapter(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_day, // The name of the layout ID.
                        R.id.list_item_day_layout, // The ID of the textview to populate.
                        dayCount);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = rootView.findViewById(R.id.listview_day);
        listView.setAdapter(dayAdapter);

        dayDataProvider = new DayDataProvider();
        FetchCigarettesTask<Day> fetchCigarettesTask = new FetchCigarettesTask<Day>(dayAdapter, dayDataProvider);
        fetchCigarettesTask.execute();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.daylistfragment, menu);
    }

}
