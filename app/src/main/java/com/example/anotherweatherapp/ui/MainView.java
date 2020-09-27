package com.example.anotherweatherapp.ui;

import com.example.anotherweatherapp.common.BaseView;
import com.example.anotherweatherapp.data.model.Example;

import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;


public interface MainView extends BaseView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void getLocation();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showForecast(Example example);
}
