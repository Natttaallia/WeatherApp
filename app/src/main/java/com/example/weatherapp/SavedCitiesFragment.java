package com.example.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SavedCitiesFragment extends Fragment {
    public interface SavedCitiesFragmentListener {
        void onCitySelected(int woeId);
        void deleteCity();
    }
    private SavedCitiesFragmentListener listener;
    private CitiesAdapter citiesAdapter;
    private List<City> cities=new ArrayList<>();
    private SharedPreferences savedCities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(
                R.layout.fragment_saved_cities, container, false);
        savedCities = this.getActivity().getSharedPreferences(ChooseCityFragment.CITIES, MODE_PRIVATE);
        cities.clear();
        List<String> s= new ArrayList<>(savedCities.getAll().keySet());
        for (String str:s)
            cities.add(new City(savedCities.getInt(str,0),str));
        if(cities.size()==0) {
            Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                    R.string.no_results, Snackbar.LENGTH_LONG).show();
        }
        else {
            RecyclerView recyclerView =
                    (RecyclerView) view.findViewById(R.id.citiesRecyclerView);
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
                            deleteCity(woeId, title);
                        }
                    },
                    R.drawable.ic_delete_24dp,
                    this.getActivity()
            );
            recyclerView.setAdapter(citiesAdapter);
        }

        return view;
    }

    private void deleteCity(int woeId, String title) {
        SharedPreferences.Editor preferencesEditor =
                savedCities.edit();
        preferencesEditor.remove(title);
        preferencesEditor.apply();
        Snackbar.make(getActivity().findViewById(R.id.coordinatorLayout),
                R.string.removed_city, Snackbar.LENGTH_LONG).show();
        listener.deleteCity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (SavedCitiesFragmentListener) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
