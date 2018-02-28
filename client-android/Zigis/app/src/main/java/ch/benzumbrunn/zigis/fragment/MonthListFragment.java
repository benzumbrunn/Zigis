package ch.benzumbrunn.zigis.fragment;

import android.app.Fragment;
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

import ch.benzumbrunn.zigis.adapter.MonthAdapter;
import ch.benzumbrunn.zigis.dataprovider.IDataProvider;
import ch.benzumbrunn.zigis.dataprovider.MonthDataProvider;
import ch.benzumbrunn.zigis.data.Day;
import ch.benzumbrunn.zigis.FetchCigarettesTask;
import ch.benzumbrunn.zigis.data.Month;
import ch.benzumbrunn.zigis.R;

/**
 * Created by benzumbrunn on 25.12.17.
 */

public class MonthListFragment extends Fragment {

    private MonthAdapter monthAdapter;
    private IDataProvider<Month> monthDataProvider;

    public MonthListFragment() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchCigarettesTask<Month> fetchCigarettesTask = new FetchCigarettesTask<>(monthAdapter, monthDataProvider);
            fetchCigarettesTask.execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<Month> months = new ArrayList<>();

        monthAdapter =
                //new ArrayAdapter<String>(
                new MonthAdapter(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_month, // The name of the layout ID.
                        R.id.list_item_month_layout, // The ID of the textview to populate.
                        months);

        //View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = rootView.findViewById(R.id.listview_month);
        listView.setAdapter(monthAdapter);

        monthDataProvider = new MonthDataProvider();
        FetchCigarettesTask<Month> fetchCigarettesTask = new FetchCigarettesTask<>(monthAdapter, monthDataProvider);
        fetchCigarettesTask.execute();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.monthlistfragment, menu);
    }
}
