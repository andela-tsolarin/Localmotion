package com.andela.toni.localmotion.services;

import android.app.Service;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.andela.toni.localmotion.callbacks.LocationCallback;
import com.andela.toni.localmotion.config.Constants;
import com.andela.toni.localmotion.db.DbOperations;
import com.andela.toni.localmotion.locationproviders.GoogleLocationProvider;
import com.andela.toni.localmotion.locationproviders.LocationProvider;
import com.andela.toni.localmotion.models.LocationRecord;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tonie on 10/27/2015.
 */
public class LocationTrackingService extends Service {

    private CountDownTimer countDownTimer;
    private LocationProvider locationProvider;
    private  Location location;
    private DbOperations dbOperations;
    private long duration;

    private long previousTime;
    private long presentTime;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.dbOperations = new DbOperations(this);

        previousTime = new Date().getTime();
        final LocationTrackingService that = this;
        this.locationProvider = new GoogleLocationProvider(this, new LocationCallback() {
            @Override
            public void handleLocationChange(Location location) {
                double distance = 0;

                if (that.location != null) {
                    distance = that.location.distanceTo(location);
                } else {
                    that.location = location;
                    Toast.makeText(that, "Location null. Starting timer", Toast.LENGTH_SHORT).show();
                    initializeTimer();
                }

                if (distance >= Constants.DIFFERENCE_THRESHOLD) {
                    Toast.makeText(that, "Significant movement. Restarting timer", Toast.LENGTH_SHORT).show();
                    initializeTimer();
                    that.location = location;
                }
            }
        });

        this.locationProvider.connect();
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.locationProvider.disconnect();
        this.countDownTimer.cancel();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }

    private void initializeTimer() {
        if (this.countDownTimer != null) {
            this.countDownTimer.cancel();
        }

        final LocationTrackingService that = this;
        this.countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                presentTime = new Date().getTime();
                duration = presentTime - previousTime;
                previousTime = presentTime;

                // Save to database
                Toast.makeText(that, "Saving location to database with duration: " + Long.toString(duration), Toast.LENGTH_SHORT).show();
                dbOperations.insertRecord(buildLocationRecord());
            }
        }.start();
    }

    public String getAddress() {
        String address = "No address found for this location";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            address = geocoder.getFromLocation(this.location.getLatitude(),
            this.location.getLongitude(), 1).get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

    private LocationRecord buildLocationRecord() {
        return this.location == null ? new LocationRecord() :
                new LocationRecord(Double.toString(this.location.getLatitude()),
                        Double.toString(this.location.getLongitude()),
                        new SimpleDateFormat("dd/MM/yyyy").format(new Date()),
                        Long.toString(duration), getAddress());
    }

}
