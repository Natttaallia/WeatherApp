package com.example.weatherapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder> {

    private final List<WeatherForecast.Weather> weatherForecast;
    private Map<String, Bitmap> bitmaps = new HashMap<>();


    public WeatherForecastAdapter(List<WeatherForecast.Weather> w) {
        this.weatherForecast=w;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView dayTextView;
        public final TextView minTextView;
        public final TextView maxTextView;
        public final TextView tempTextView;
        public final TextView humidityTextView;
        public final TextView windTextView;
        public final ImageView conditionImageView;
        public final Drawable iconBlock;


        public ViewHolder(View itemView) {
            super(itemView);
            dayTextView = (TextView) itemView.findViewById(R.id.dayTextView);
            minTextView = (TextView) itemView.findViewById(R.id.lowTextView);
            maxTextView = (TextView) itemView.findViewById(R.id.hiTextView);
            tempTextView = (TextView) itemView.findViewById(R.id.tempTextView);
            humidityTextView = (TextView) itemView.findViewById(R.id.humidityTextView);
            windTextView = (TextView) itemView.findViewById(R.id.windTextView);
            conditionImageView=(ImageView) itemView.findViewById(R.id.conditionImageView);
            iconBlock = itemView.getResources().getDrawable(R.drawable.ic_block_24dp);
        }

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        WeatherForecast.Weather w=weatherForecast.get(position);
        Context c=holder.dayTextView.getContext();
        holder.dayTextView.setText(c.getString(R.string.day_description, w.getDate(), w.getTitle()));
        holder.minTextView.setText(c.getString(R.string.low_temp, w.getMin()));
        holder.maxTextView.setText(c.getString(R.string.high_temp, w.getMax()));
        holder.tempTextView.setText(c.getString(R.string.temp, w.getTemp()));
        holder.humidityTextView.setText(c.getString(R.string.humidity, w.getHumidity()));
        holder.windTextView.setText(c.getString(R.string.wind, w.getWind()));
//        if (bitmaps.containsKey(w.getAbbr()+".png")) {
//            holder.conditionImageView.setImageBitmap(
//                    bitmaps.get(w.getAbbr()+".png"));
//        }
//        else {
            NetworkService.getInstance()
                    .getJSONApi()
                    .loadIcon(w.getAbbr()+".png")
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                                holder.conditionImageView.setImageBitmap(bmp);
                                bitmaps.put(response.raw().request().url().toString(), bmp);
//                                holder.dayTextView.setText(""+response.raw().request().url().to);
                            }
                            else
                                holder.conditionImageView.setImageDrawable(holder.iconBlock);
                         }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            holder.conditionImageView.setImageDrawable(holder.iconBlock);
                        }
                    });
//        }
    }

    @Override
    public int getItemCount() {
        return weatherForecast.size();
    }
}
