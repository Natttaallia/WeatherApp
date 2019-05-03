package com.example.weatherapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherForecast {


    @SerializedName("consolidated_weather")
    @Expose
    private ArrayList<Weather> weather;

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public class Weather{
    @SerializedName("applicable_date")
    @Expose
    private  String date;
    @SerializedName("weather_state_name")
    @Expose
    private  String title;
    @SerializedName("weather_state_abbr")
    @Expose
    private  String abbr;
    @SerializedName("min_temp")
    @Expose
    private  float min;
    @SerializedName("max_temp")
    @Expose
    private  float max;
    @SerializedName("the_temp")
    @Expose
    private  float temp;
    @SerializedName("wind_speed")
    @Expose
    private  float wind;
    @SerializedName("humidity")
    @Expose
    private  int humidity;



    public String getDate() {
        DateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d=s.parse(date);
            s=new SimpleDateFormat("EEEE");
            return s.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getAbbr() {
        return abbr+".png";
    }

    public String getMin() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(min)+ "\u00B0";
    }

    public String getMax() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(max)+ "\u00B0";
    }
    public String getTemp() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(temp)+ "\u00B0";
    }

    public String getWind() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(1);
        return numberFormat.format(wind)+ " m/sec";
    }

    public String getHumidity() {
        return NumberFormat.getPercentInstance().format(humidity / 100.0);
    }
    }
}
