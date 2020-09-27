package com.example.anotherweatherapp.data;

import android.util.Log;

import com.example.anotherweatherapp.data.database.WeatherDao;
import com.example.anotherweatherapp.data.model.Example;
import com.example.anotherweatherapp.ui.MainFragment;

public class Storage {

    private WeatherDao weatherDao;


    public Storage(WeatherDao weatherDao) {
        this.weatherDao = weatherDao;
    }

    public void insertAllForecast(Example example){
       /* for(int i =0;i<hourlyForecastsInfoList.size();i++){
            hourlyForecastsInfoList.get(i).setId(i);
        }*/
        Log.d("TAG", "insertAllForecast: " + example.getHourly().size());
        weatherDao.insertForecast(example);
        weatherDao.clearHourlyTable();
        Log.d("TAG", "insertAllForecast: " + example.getHourly().size());
        weatherDao.insertHourlyForecast(example.getHourly());
        weatherDao.clearDailyTable();
        weatherDao.insertDailyForecast(example.getDaily());
        Log.d("TAG", "insertAllForecastfgfg: " + example.getHourly().size());

    }

    public Example getHourlyForecast(){

        Example example = new Example();
        example = weatherDao.getAllForecast();
    //    Log.d(MainFragment.TAG, "getHourlyForecasffgt: " + example.getHourly().size());
        example.setHourly(weatherDao.getHourlyForecast());
        Log.d(MainFragment.TAG, "getHourlyForecaxxfst: after insert " + example.getHourly().size());
        example.setDaily(weatherDao.getDailyForecast());
        return example;
    }
}
