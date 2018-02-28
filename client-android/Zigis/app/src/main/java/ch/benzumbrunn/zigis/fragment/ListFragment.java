package ch.benzumbrunn.zigis.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import ch.benzumbrunn.zigis.R;

/**
 * Created by benzumbrunn on 25.12.17.
 */

public class ListFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if (savedInstanceState == null) {
            setHasOptionsMenu(true);
        //}
    }

}
