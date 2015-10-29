package com.andela.toni.localmotion.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.andela.toni.localmotion.R;
import com.andela.toni.localmotion.models.LocationRecord;

import java.util.ArrayList;

/**
 * Created by tonie on 10/29/2015.
 */
public class AddressHistoryAdapter extends BaseAdapter {

    private LayoutInflater historyInflater;
    private Activity activity;
    private ArrayList<LocationRecord> records;

    public AddressHistoryAdapter(Activity activity, ArrayList<LocationRecord> records) {
        this.activity = activity;
        this.records = records;
        this.historyInflater = LayoutInflater.from(activity);
    }


    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public LocationRecord getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = historyInflater.inflate(R.layout.history_item, parent, false);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.primary);
            holder.locationCount = (TextView) convertView.findViewById(R.id.secondary);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //get song using position
        LocationRecord locationRecord = records.get(position);
        //get title and artist strings
        holder.date.setText(locationRecord.getAddress());
        holder.locationCount.setText(Long.toString(locationRecord.getAverageAddressTime() / 60000) + " Minutes");

        return convertView;
    }

    public class ViewHolder {
        TextView date;
        TextView locationCount;
    }
}
