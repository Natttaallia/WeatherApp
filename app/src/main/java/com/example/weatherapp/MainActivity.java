package com.example.weatherapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements MainActivityFragment.MainActivityFragmentListener,
        SavedCitiesFragment.SavedCitiesFragmentListener,
        ChooseCityFragment.ChooseCityFragmentListener {

    private MainActivityFragment mainFragment;
    public static final String CITY_ID = "city_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
            mainFragment = new MainActivityFragment();
            FragmentTransaction transaction =
                    getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, mainFragment);
            transaction.commit();
    }
    @Override
    public void onSavedCities() {
        SavedCitiesFragment savedCitiesFragment = new SavedCitiesFragment();
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, savedCitiesFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onChooseCity() {
        ChooseCityFragment chooseCityFragment = new ChooseCityFragment();
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, chooseCityFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onCitySelected(int woeId) {

        WeatherForecastFragment weatherForecastFragment = new WeatherForecastFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CITY_ID, woeId);
        weatherForecastFragment.setArguments(arguments);
        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, weatherForecastFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void deleteCity() {
        getSupportFragmentManager().popBackStack();
    }


}
