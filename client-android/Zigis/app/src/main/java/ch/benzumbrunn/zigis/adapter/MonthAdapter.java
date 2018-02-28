package ch.benzumbrunn.zigis.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.benzumbrunn.zigis.R;
import ch.benzumbrunn.zigis.data.Month;

/**
 * Created by benzumbrunn on 25.12.17.
 */

public class MonthAdapter extends ArrayAdapter {

    public static class ViewHolder {
        public final TextView dateView;
        public final TextView countView;

        public ViewHolder(View view) {
            dateView = view.findViewById(R.id.list_item_monthdate_textview);
            countView = view.findViewById(R.id.list_item_monthcount_textview);
        }
    }

    private List<Month> items;

    public MonthAdapter(@NonNull Context context, int list_item_month, int resource, List<Month> months) {
        super(context, list_item_month, resource, months);
        items = months;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = View.inflate(getContext(), R.layout.list_item_month, null);

        Month month = items.get(position);

        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.dateView.setText(month.getMonth());
        viewHolder.countView.setText(String.valueOf(month.getCount()));

        view.setTag(viewHolder);

        return view;
    }

}
