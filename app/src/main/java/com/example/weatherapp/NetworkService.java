package com.example.weatherapp;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class NetworkService {

    public interface ApiService {
        @GET("api/location/search")
        Call<List<City>> getCities(@Query("query") String city);

        @GET("api/location/{city}")
        Call<WeatherForecast> getWeatherForecast(@Path("city") int city);

        @GET("static/img/weather/png/64/{img}")
        Call<ResponseBody> loadIcon(@Path("img") String img);

    }

    private static NetworkService mInstance;
    private Retrofit mRetrofit;
    private static final String BASE_URL = "https://www.metaweather.com/";

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }
    public ApiService getJSONApi() {
        return mRetrofit.create(ApiService.class);
    }


}
