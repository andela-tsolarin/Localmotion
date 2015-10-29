package com.andela.toni.localmotion.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.andela.toni.localmotion.config.Constants;
import com.andela.toni.localmotion.models.LocationRecord;

import java.util.ArrayList;

/**
 * Created by tonie on 10/29/2015.
 */
public class DbOperations extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    public DbOperations(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table locations ( _id integer primary key autoincrement, latitude, " +
            "longitude, date, duration, address)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertRecord(LocationRecord record) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("latitude", record.getLatitude());
        contentValues.put("longitude", record.getLongitude());
        contentValues.put("date", record.getDate());
        contentValues.put("duration", record.getDuration());
        contentValues.put("address", record.getAddress());
        long result = db.insert("locations", null, contentValues);
        return !(result == -1);
    }

    public ArrayList<String> getUniqueDates() {
        ArrayList<String> dates = new ArrayList<>();
        try {
            Cursor cursor = db.query(true, "locations", new String[]{"date"}, null, null, "date", null, null, null);
            while (cursor.moveToNext()) {
                dates.add(cursor.getString(cursor.getColumnIndex("date")));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dates;
    }

    public ArrayList<LocationRecord> getDateLocations(String date) {
        ArrayList<LocationRecord> locations = new ArrayList<>();
        Cursor res = db.query("locations", null, "date" + "=?", new String[]{date}, null, null, null);
        while (res.moveToNext()) {
            LocationRecord location = new LocationRecord();
            location.setLatitude(res.getString(res.getColumnIndex("latitude")));
            location.setLongitude(res.getString(res.getColumnIndex("longitude")));
            location.setDate(res.getString(res.getColumnIndex("date")));
            location.setDuration(res.getString(res.getColumnIndex("duration")));
            location.setAddress(res.getString(res.getColumnIndex("address")));
            locations.add(location);
        }
        return locations;
    }

    public ArrayList<String> getUniqueAddresses() {
        ArrayList<String> addresses = new ArrayList<>();
        try {
            Cursor cursor = db.query(true, "locations", new String[]{"address"}, null, null, "address", null, null, null);
            while (cursor.moveToNext()) {
                addresses.add(cursor.getString(cursor.getColumnIndex("address")));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addresses;
    }

    public long getAverageAddressTime(String address) {
        Cursor res = db.query("locations", null, "address" + "=?", new String[]{address}, null, null, null);
        long j = 0;
        while (res.moveToNext()) {
            j = j + res.getLong(res.getColumnIndex("duration"));
        }
        return j;
    }
}
