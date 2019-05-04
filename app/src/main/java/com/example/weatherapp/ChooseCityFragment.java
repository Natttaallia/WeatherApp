package com.example.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ChooseCityFragment extends Fragment {
    public interface ChooseCityFragmentListener {
        void onCitySelected(int woeId);
    }

    private ChooseCityFragmentListener listener;
    private CitiesAdapter citiesAdapter;
    private List<City> cities=new ArrayList<>();
    private EditText cityEditText;
    private SharedPreferences savedCities;
    public static final String CITIES = "cities";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(
                R.layout.fragment_choosen_cities, container, false);
        cityEditText = ((TextInputLayout) view.findViewById(
                R.id.cityTextInputLayout)).getEditText();
        savedCities = this.getActivity().getSharedPreferences(CITIES, MODE_PRIVATE);
        RecyclerView recyclerView =
                (RecyclerView) view.findViewById(R.id.chooseCityRecyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getActivity().getBaseContext()));
        citiesAdapter = new CitiesAdapter(
                cities,
                new CitiesAdapter.CityClickListener() {
                    @Override
                    public void onClick(int woeId) {
                        listener.onCitySelected(woeId);
                    }

                    @Override
                    public void onBtnClick(int woeId, String title) {
                        saveCity(woeId,title);
                    }
                },
                R.drawable.ic_save_24dp,
                this.getActivity()
        );
        recyclerView.setAdapter(citiesAdapter);
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
                                    cities.addAll(response.body());
                                    citiesAdapter.notifyDataSetChanged();
                                      if(cities.size()==0) {
                                        Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                                                R.string.no_results, Snackbar.LENGTH_LONG).show();
                                    }
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
    private void saveCity(int woeId, String title) {
        SharedPreferences.Editor preferencesEditor = savedCities.edit();
        preferencesEditor.putInt(title,woeId);
        preferencesEditor.apply();
        Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                R.string.saved_city, Snackbar.LENGTH_LONG).show();
    }

}
