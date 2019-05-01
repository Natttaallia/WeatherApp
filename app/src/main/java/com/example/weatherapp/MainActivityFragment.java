package com.example.weatherapp;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public interface MainActivityFragmentListener {
        // выбор кнопки SavedCities
        void onSavedCities();
        // выбор кнопки ChooseCity
        void onChooseCity();
    }

    private MainActivityFragmentListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(
                R.layout.fragment_main, container, false);
        Button chooseCityButton =
                (Button) view.findViewById(R.id.choose_city_btn);
        chooseCityButton.setOnClickListener(
                new View.OnClickListener() {
                    // displays the AddEditFragment when FAB is touched
                    @Override
                    public void onClick(View view) {
                        listener.onChooseCity();
                    }
                }
        );
        Button savedCitiesButton =
                (Button) view.findViewById(R.id.my_city_btn);
        savedCitiesButton.setOnClickListener(
                new View.OnClickListener() {
                    // displays the AddEditFragment when FAB is touched
                    @Override
                    public void onClick(View view) {
                        listener.onSavedCities();
                    }
                }
        );
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (MainActivityFragmentListener) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
