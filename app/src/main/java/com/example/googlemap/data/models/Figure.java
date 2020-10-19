package com.example.googlemap.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Figure {


    double latitude;

    double latLng;

    public Figure(double latitude, double latLng) {
        this.latitude = latitude;
        this.latLng = latLng;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatLng() {
        return latLng;
    }

    public void setLatLng(double latLng) {
        this.latLng = latLng;
    }

    @PrimaryKey (autoGenerate = true)
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
