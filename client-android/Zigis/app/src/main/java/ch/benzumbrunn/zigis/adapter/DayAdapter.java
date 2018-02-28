package ch.benzumbrunn.zigis.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.benzumbrunn.zigis.data.Day;
import ch.benzumbrunn.zigis.R;

/**
 * Created by benzumbrunn on 23.12.17.
 */

public class DayAdapter extends ArrayAdapter {

    public static class ViewHolder {
        public final TextView dateView;
        public final TextView countView;
        public final View colorView;

        public ViewHolder(View view) {
            dateView = view.findViewById(R.id.list_item_date_textview);
            countView = view.findViewById(R.id.list_item_count_textview);
            colorView = view.findViewById(R.id.list_item_color_view);
        }
    }

    private List<Day> items;

    public DayAdapter(@NonNull Context context, int list_item_day, int resource, List<Day> days) {
        super(context, list_item_day, resource, days);
        items = days;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = View.inflate(getContext(), R.layout.list_item_day, null);

        Day day = items.get(position);

        int colorId;
        if (day.getCount() < 1) {
            colorId = R.color.green;
        }
        else if (day.getCount() < 10) {
            colorId = R.color.yellow;
        }
        else {
            colorId = R.color.red;
        }

        ViewHolder viewHolder = new ViewHolder(view);
        if (position == 0) {
            viewHolder.dateView.setText("Heute");
        } else if (position == 1) {
            viewHolder.dateView.setText("Gestern");
        } else {
            viewHolder.dateView.setText(day.getDate());
        }
        viewHolder.countView.setText(String.valueOf(day.getCount()));
        viewHolder.colorView.setBackgroundColor((getContext().
                getResources().
                getColor(colorId, getContext().getTheme())));

        view.setTag(viewHolder);

        return view;
    }
}
