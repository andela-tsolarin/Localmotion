package com.andela.toni.localmotion.models;

/**
 * Created by tonie on 10/29/2015.
 */
public class LocationRecord {

    private String latitude;
    private String longitude;
    private String date;
    private String duration;
    private String address;
    private int locationCount;
    private long averageAddressTime;

    public LocationRecord() {

    }

    public LocationRecord(String latitude, String longitude,
                          String date, String duration, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.duration = duration;
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getLocationCount() {
        return locationCount;
    }

    public void setLocationCount(int locationCount) {
        this.locationCount = locationCount;
    }

    public long getAverageAddressTime() {
        return averageAddressTime;
    }

    public void setAverageAddressTime(long averageAddressTime) {
        this.averageAddressTime = averageAddressTime;
    }
}
