package com.andela.toni.localmotion.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.andela.toni.localmotion.callbacks.LocationCallback;
import com.andela.toni.localmotion.locationproviders.GoogleLocationProvider;
import com.andela.toni.localmotion.locationproviders.LocationProvider;

/**
 * Created by tonie on 10/27/2015.
 */
public class LocationTrackingService extends Service {

    private LocationProvider locationProvider;

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
                Toast.makeText(that, locationProvider.getAddress(location.getLongitude(), location.getLatitude()), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }
}
