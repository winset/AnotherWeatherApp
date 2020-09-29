package com.example.anotherweatherapp.ui;

import com.example.anotherweatherapp.common.BaseView;
import com.example.anotherweatherapp.data.model.Daily;
import com.example.anotherweatherapp.data.model.Example;
import com.example.anotherweatherapp.data.model.Hourly;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;


public interface MainView extends BaseView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void getLocation();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showCurrentForecast(Example example);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showHourlyForecast(List<Hourly> hourlyList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDailyForecast(List<Daily> dailyList);
}
