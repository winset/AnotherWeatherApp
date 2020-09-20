package com.example.anotherweatherapp.ui;

import android.location.Location;

import com.example.anotherweatherapp.common.BaseView;
import com.example.anotherweatherapp.data.model.HourlyForecastsInfo;
import com.example.anotherweatherapp.data.model.HourlyForecastsTemperature;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;


public interface MainView extends BaseView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void getLocation();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showForecast(List<HourlyForecastsInfo> hourlyForecastsInfoList);
}
