package com.example.anotherweatherapp.data.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class WeatherTypeConverters {

    static Gson gson = new Gson();


    @TypeConverter
    public static List<Weather> stringToWeatherList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Weather>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }
    @TypeConverter
    public static String weatherListToString(List<Weather> weatherList) {
        return gson.toJson(weatherList);
    }
}
