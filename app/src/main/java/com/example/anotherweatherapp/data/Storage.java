package com.example.anotherweatherapp.data;

import com.example.anotherweatherapp.data.database.WeatherDao;
import com.example.anotherweatherapp.data.model.HourlyForecastsInfo;

import java.util.List;

public class Storage {

    private WeatherDao weatherDao;


    public Storage(WeatherDao weatherDao) {
        this.weatherDao = weatherDao;
    }

    public void insertHourlyForecast(List<HourlyForecastsInfo> hourlyForecastsInfoList){
        for(int i =0;i<hourlyForecastsInfoList.size();i++){
            hourlyForecastsInfoList.get(i).setId(i);
        }
        weatherDao.insertHourlyForecast(hourlyForecastsInfoList);
    }

    public List<HourlyForecastsInfo> getHourlyForecast(){
        return weatherDao.getAllHourlyForecast();
    }
}
