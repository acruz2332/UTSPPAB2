package com.ppab.utsppab2.api;

import com.google.gson.annotations.SerializedName;

public class MapData {

    private String name;

    private double latitude;

    private double longitude;

    private String country;

    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getID() {
        return country;
    }

    public void setID(String ID) {
        this.country = ID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
