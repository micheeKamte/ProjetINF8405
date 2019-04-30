package com.example.myapplication;

public class DataUser {
    private double latitude;
    private double longitude;
    private double sPressure;
    private double sLight;
    private double sTemperature;

    public DataUser(double latitude, double longitude, double sPressure, double sLight, double sTemperature) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.sPressure = sPressure;
        this.sLight = sLight;
        this.sTemperature = sTemperature;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getsPressure() {
        return sPressure;
    }

    public double getsLight() {
        return sLight;
    }

    public double getsTemperature() {
        return sTemperature;
    }

    public void setsPressure(double sPressure) {
        this.sPressure = sPressure;
    }

    public void setsLight(double sLight) {
        this.sLight = sLight;
    }

    public void setsTemperature(double sTemperature) {
        this.sTemperature = sTemperature;
    }
}
