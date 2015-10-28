package com.andela.toni.localmotion.callbacks;

import android.location.Location;

/**
 * Created by tonie on 10/28/2015.
 */
public interface LocationCallback {
    void handleLocationChange(Location location);
}
