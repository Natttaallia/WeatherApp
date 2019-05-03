package com.example.weatherapp;

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

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {

    private final CityClickListener clickListener;
    private final List<City> cities;
    private final Drawable iconBtn;

    public interface CityClickListener {
        void onClick(int woeId);
        void onBtnClick(int woeId, String title);
    }
    public CitiesAdapter(List<City> cities,CityClickListener clickListener, Drawable d) {
        this.clickListener = clickListener;
        this.cities=cities;
        this.iconBtn=d;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        public final Button button;
        private int woeId;


        public ViewHolder(final View itemView) {
            super(itemView);
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
        holder.textView.setText(cities.get(position).getTitle());
        holder.setWoedID(cities.get(position).getWoeId());
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
