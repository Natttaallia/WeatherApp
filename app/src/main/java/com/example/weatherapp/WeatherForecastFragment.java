package com.example.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.weatherapp.WeatherForecast.Weather;

public class WeatherForecastFragment extends Fragment {
    private int woeId;
    private WeatherForecastAdapter weatherAdapter;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle arguments = getArguments();

        if (arguments != null)
            woeId = arguments.getInt(MainActivity.CITY_ID);
        View view = inflater.inflate(
                R.layout.fragment_weather_forecast, container, false);
        recyclerView =
                (RecyclerView) view.findViewById(R.id.forecastRecyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity().getBaseContext()));

        NetworkService.getInstance()
                .getJSONApi()
                .getWeatherForecast(woeId)
                .enqueue(new Callback<WeatherForecast>() {
                    @Override
                    public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                        weatherAdapter = new WeatherForecastAdapter(response.body().getWeather());
                        recyclerView.setAdapter(weatherAdapter);
                    }

                    @Override
                    public void onFailure(Call<WeatherForecast> call, Throwable t) {
                        Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                                R.string.connect_error, Snackbar.LENGTH_LONG).show();

                    }
                });


        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
