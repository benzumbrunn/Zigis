package ch.benzumbrunn.zigis;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ch.benzumbrunn.zigis.fragment.DayListFragment;
import ch.benzumbrunn.zigis.fragment.MonthListFragment;

public class MainActivity extends AppCompatActivity {

    private Fragment dayListFragment;
    private Fragment monthListFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_days:
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, dayListFragment)
                            .commit();
                    return true;
                case R.id.navigation_months:
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, monthListFragment)
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            setContentView(R.layout.activity_mainactivity);

            dayListFragment = new DayListFragment();
            monthListFragment = new MonthListFragment();

            BottomNavigationView navigation = findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, dayListFragment)
                    .commit();
        }
    }

}
