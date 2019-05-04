package com.example.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private final CityClickListener clickListener;
    private final List<City> cities;
    private final Drawable iconBtn;
    private final Context context;
    private SharedPreferences savedCities;


    public interface CityClickListener {
        void onClick(int woeId);
        void onBtnClick(int woeId, String title);
    }
    public CitiesAdapter(List<City> cities,CityClickListener clickListener, int d, Context c) {
        this.clickListener = clickListener;
        this.cities=cities;
        this.context=c;
        this.iconBtn=context.getResources().getDrawable(d);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        public final Button button;
        private int woeId;


        public ViewHolder(final View itemView) {
            super(itemView);
            savedCities = context.getSharedPreferences(ChooseCityFragment.CITIES, MODE_PRIVATE);
            textView = (TextView) itemView.findViewById(R.id.textView);
            button=(Button)itemView.findViewById(R.id.btn_save_delete);
            button.setBackground(iconBtn);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onBtnClick(woeId,textView.getText().toString());
                    button.setVisibility(View.GONE);
                }
            });

            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            clickListener.onClick(woeId);
                        }
                    }
            );
        }

        public void setWoedID(int woeId) {
            this.woeId = woeId;
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.city_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        City city=cities.get(position);
        holder.textView.setText(city.getTitle());
        holder.setWoedID(city.getWoeId());
        if(savedCities.getInt(city.getTitle(),-1)!=-1 && iconBtn.getConstantState().equals(context.getResources().getDrawable(R.drawable.ic_save_24dp).getConstantState()))
            holder.button.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
