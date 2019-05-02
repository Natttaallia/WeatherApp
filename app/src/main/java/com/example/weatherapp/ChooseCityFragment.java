package com.example.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChooseCityFragment extends Fragment {
    public interface ChooseCityFragmentListener {
        void onCitySelected(int fragment_id,int woeId);
    }

    private ChooseCityFragmentListener listener;
    private CitiesAdapter citiesAdapter;
    private List<City> cities=new ArrayList<>();
    private EditText cityEditText;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(
                R.layout.fragment_choosen_cities, container, false);
        cityEditText = ((TextInputLayout) view.findViewById(
                R.id.cityTextInputLayout)).getEditText();
         recyclerView =
                (RecyclerView) view.findViewById(R.id.chooseCityRecyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity().getBaseContext()));
        citiesAdapter = new CitiesAdapter(
                cities,
                new CitiesAdapter.CityClickListener() {
                    @Override
                    public void onClick(int woeId) {
                        listener.onCitySelected(R.layout.fragment_choosen_cities,woeId);
                    }
                }
        );
        recyclerView.setAdapter(citiesAdapter);
        recyclerView.addItemDecoration(new ItemDivider(getActivity().getBaseContext()));
        FloatingActionButton fab =
                (FloatingActionButton) view.findViewById(R.id.findCityFloatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cityEditText.getText().toString().isEmpty()) {
                    dismissKeyboard(cityEditText);
                    NetworkService.getInstance()
                            .getJSONApi()
                            .getCities(cityEditText.getText().toString())
                            .enqueue(new Callback<List<City>>() {
                                @Override
                                public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                                    cities.clear();
                                    cities = response.body();
                                    citiesAdapter.notifyDataSetChanged();
                                    citiesAdapter = new CitiesAdapter(
                                            cities,
                                            new CitiesAdapter.CityClickListener() {
                                                @Override
                                                public void onClick(int woeId) {
                                                    listener.onCitySelected(R.layout.fragment_choosen_cities,woeId);
                                                }
                                            }
                                    );
                                    recyclerView.setAdapter(citiesAdapter);
                                }

                                @Override
                                public void onFailure(Call<List<City>> call, Throwable t) {
                                    Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                                            R.string.connect_error, Snackbar.LENGTH_LONG).show();

                                }
                            });
                }
            }
        });

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ChooseCityFragmentListener) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void dismissKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
