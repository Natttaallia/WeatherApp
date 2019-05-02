package com.example.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SavedCitiesFragment extends Fragment {
    public interface SavedCitiesFragmentListener {
        void onCitySelected(int woeId);
    }
    private SavedCitiesFragmentListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(
                R.layout.fragment_saved_cities, container, false);

        return view;
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
