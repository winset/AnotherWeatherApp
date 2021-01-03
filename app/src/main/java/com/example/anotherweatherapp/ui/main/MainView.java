package com.example.anotherweatherapp.ui.main;

import com.example.anotherweatherapp.common.BaseView;
import com.example.anotherweatherapp.data.model.Daily;
import com.example.anotherweatherapp.data.model.Hourly;

import java.util.List;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;


public interface MainView extends BaseView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void getLocation();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showCurrentForecast(String iconPhrase,String currentTemp, int iconId,String currentWind,String currentHumidity,String currentVisibility);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showHourlyForecast(List<Hourly> hourlyList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDailyForecast(List<Daily> dailyList);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showSolarCycle(Long sunrise,Long sunset);

    @StateStrategyType(SkipStrategy.class)
    void addScrollListener();
}
