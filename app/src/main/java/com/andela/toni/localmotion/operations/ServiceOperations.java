package com.andela.toni.localmotion.operations;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by tonie on 10/27/2015.
 */
public class ServiceOperations {

    public boolean serviceRunning(String serviceName, Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
