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
            "longitude, date, address)";
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
}
