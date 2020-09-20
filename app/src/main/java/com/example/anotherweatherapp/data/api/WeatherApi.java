package com.example.anotherweatherapp.data.api;

import com.example.anotherweatherapp.data.model.Example;
import com.example.anotherweatherapp.data.model.HourlyForecastsInfo;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApi  {

  /*  @GET("/forecasts/v1/hourly/12hour/{locationId}" )
    Single<List<HourlyForecastsInfo>> getHourlyForcast(
            @Path("locationId") String locationId,
            @Query("apikey") String apiKey,
            @Query("language") String language,
            @Query("metric") String metric);*/

    @GET("data/2.5/onecall/" )
    Single<Example> getHourlyForcast(
            //  @Path("locationId") String locationId,
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("exclude") String exclude,
            @Query("appid") String apiKey);
           /* @Query("language") String language,
    @Query("metric") String metric);*/
}
