package com.example.anotherweatherapp.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.anotherweatherapp.data.model.Daily;
import com.example.anotherweatherapp.data.model.WeatherForecast;
import com.example.anotherweatherapp.data.model.Hourly;

import java.util.List;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM WeatherForecast")
    WeatherForecast getAllForecast();

    @Query("SELECT * FROM hourly")
    List<Hourly> getHourlyForecast();

    @Query("SELECT * FROM daily")
    List<Daily> getDailyForecast();

/*    @Query("SELECT * FROM hourlyforecastsinfo where id=  :id")
    List<HourlyForecastsInfo> getByIdHourlyForecast(int id);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForecast(WeatherForecast weatherForecast);

  /*  @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrentForecast(Current current);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHourlyForecast(List<Hourly> hourlyList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDailyForecast(List<Daily> dailyList);

    @Query("delete from hourly")
    void clearHourlyTable();

    @Query("delete from daily")
    void clearDailyTable();


}
