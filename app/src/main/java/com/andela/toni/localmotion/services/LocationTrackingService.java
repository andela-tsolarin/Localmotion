package com.andela.toni.localmotion.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

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
        this.locationProvider = new GoogleLocationProvider(this);
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
