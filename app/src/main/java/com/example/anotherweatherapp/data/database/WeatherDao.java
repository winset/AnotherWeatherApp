package com.example.anotherweatherapp.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.anotherweatherapp.data.model.HourlyForecastsInfo;

import java.util.List;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM hourlyforecastsinfo")
    List<HourlyForecastsInfo> getAllHourlyForecast();

    @Query("SELECT * FROM hourlyforecastsinfo where id=  :id")
    List<HourlyForecastsInfo> getByIdHourlyForecast(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHourlyForecast(List<HourlyForecastsInfo> hourlyForecastsInfoList);



}
