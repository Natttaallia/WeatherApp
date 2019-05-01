package com.example.weatherapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MetaWeatherService {
    @GET("/api/location/search")
    Call<List<City>> getCities(@Query("query") String city);
}

