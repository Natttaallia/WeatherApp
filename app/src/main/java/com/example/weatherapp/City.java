package com.example.weatherapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("title")
    @Expose
    private final String title;
    @SerializedName("woeid")
    @Expose
    private final int woeId;
    // constructor
    public City(int woeId,String title) {
        this.title = title;
        this.woeId = woeId;
    }
    public String getTitle() {
        return title;
    }
    public int getWoeId() {
        return woeId;
    }

}
