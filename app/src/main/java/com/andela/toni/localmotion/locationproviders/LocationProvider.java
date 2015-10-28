package com.andela.toni.localmotion.locationproviders;

/**
 * Created by tonie on 10/28/2015.
 */
public interface LocationProvider {
    void connect();
    void disconnect();
    String getAddress(double longitude, double latitude);
}
