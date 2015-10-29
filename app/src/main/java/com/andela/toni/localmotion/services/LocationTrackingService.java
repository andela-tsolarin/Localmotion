package com.andela.toni.localmotion.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.andela.toni.localmotion.callbacks.LocationCallback;
import com.andela.toni.localmotion.config.Constants;
import com.andela.toni.localmotion.locationproviders.GoogleLocationProvider;
import com.andela.toni.localmotion.locationproviders.LocationProvider;

/**
 * Created by tonie on 10/27/2015.
 */
public class LocationTrackingService extends Service {

    private CountDownTimer countDownTimer;
    private LocationProvider locationProvider;
    private  Location location;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

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
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.locationProvider.disconnect();
        this.countDownTimer.cancel();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }

    public void initializeTimer() {
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
                // Save to database
                Toast.makeText(that, "Saving location to database", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }


}
