package com.example.anotherweatherapp.data.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.anotherweatherapp.data.model.HourlyForecastsInfo;

@Database(entities = {HourlyForecastsInfo.class},version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract WeatherDao getWeatherDao();
}
