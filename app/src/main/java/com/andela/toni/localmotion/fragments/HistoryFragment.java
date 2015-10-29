package com.andela.toni.localmotion.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.andela.toni.localmotion.R;
import com.andela.toni.localmotion.adapters.AddressHistoryAdapter;
import com.andela.toni.localmotion.adapters.DateHistoryAdapter;
import com.andela.toni.localmotion.db.DbOperations;
import com.andela.toni.localmotion.models.LocationRecord;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private ListView lvHistory;
    private DbOperations dbOperations;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dbOperations = new DbOperations(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        Log.d("Pos", Integer.toString(args.getInt("index")));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        lvHistory = (ListView) view.findViewById(R.id.lvHistory);
        lvHistory.setAdapter(getAddressAdapter());
        return view;
    }

    private ArrayList<LocationRecord> getDateRecords() {
        ArrayList<LocationRecord> locations = new ArrayList<>();
        ArrayList<String> dates = dbOperations.getUniqueDates();
        for (int i = 0; i < dates.size(); i++) {
            LocationRecord locationRecord = new LocationRecord();
            locationRecord.setDate(dates.get(i));
            locationRecord.setLocationCount(dbOperations.getDateLocations(dates.get(i)).size());
            locations.add(locationRecord);
        }

        return locations;
    }

    private ArrayList<LocationRecord> getAddressRecords() {
        ArrayList<LocationRecord> locations = new ArrayList<>();
        ArrayList<String> addresses = dbOperations.getUniqueAddresses();
        for (int i = 0; i < addresses.size(); i++) {
            LocationRecord locationRecord = new LocationRecord();
            locationRecord.setAddress(addresses.get(i));
            locationRecord.setAverageAddressTime(dbOperations.getAverageAddressTime(addresses.get(i)));
            locations.add(locationRecord);
        }

        return locations;
    }

    private DateHistoryAdapter getDatesAdapter() {
        return new DateHistoryAdapter(getActivity(), getDateRecords());
    }

    private AddressHistoryAdapter getAddressAdapter() {
        return new AddressHistoryAdapter(getActivity(), getAddressRecords());
    }

}
