package com.example.anotherweatherapp.data;

import android.util.Log;

import com.example.anotherweatherapp.data.database.WeatherDao;
import com.example.anotherweatherapp.data.model.WeatherForecast;
import com.example.anotherweatherapp.ui.main.MainFragment;

public class Storage {

    private WeatherDao weatherDao;


    public Storage(WeatherDao weatherDao) {
        this.weatherDao = weatherDao;
    }

    public void insertAllForecast(WeatherForecast weatherForecast){
       /* for(int i =0;i<hourlyForecastsInfoList.size();i++){
            hourlyForecastsInfoList.get(i).setId(i);
        }*/
        Log.d("TAG", "insertAllForecast: " + weatherForecast.getHourly().size());
        weatherDao.insertForecast(weatherForecast);
        weatherDao.clearHourlyTable();
        Log.d("TAG", "insertAllForecast: " + weatherForecast.getHourly().size());
        weatherDao.insertHourlyForecast(weatherForecast.getHourly());
        weatherDao.clearDailyTable();
        weatherDao.insertDailyForecast(weatherForecast.getDaily());
        Log.d("TAG", "insertAllForecastfgfg: " + weatherForecast.getHourly().size());

    }

    public WeatherForecast getHourlyForecast(){

        WeatherForecast weatherForecast = new WeatherForecast();
        weatherForecast = weatherDao.getAllForecast();
        Log.d(MainFragment.TAG, "getHourlyForecasffgt: " +weatherForecast.getCurrent().getSunrise());
        weatherForecast.setHourly(weatherDao.getHourlyForecast());
        Log.d(MainFragment.TAG, "getHourlyForecaxxfst: after insert " + weatherForecast.getHourly().size());
        weatherForecast.setDaily(weatherDao.getDailyForecast());
        return weatherForecast;
    }
}
