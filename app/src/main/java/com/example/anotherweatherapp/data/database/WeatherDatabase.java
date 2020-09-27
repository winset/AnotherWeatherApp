package com.example.anotherweatherapp.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.anotherweatherapp.data.model.Daily;
import com.example.anotherweatherapp.data.model.Example;
import com.example.anotherweatherapp.data.model.Hourly;

@Database(entities = {Example.class, Hourly.class, Daily.class},version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {

    public abstract WeatherDao getWeatherDao();
}
