package com.example.anotherweatherapp.common;


import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;


public interface BaseView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showRefresh();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void hideRefresh();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(String error);

}
