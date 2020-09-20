package com.example.anotherweatherapp.ui;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.example.anotherweatherapp.BuildConfig;
import com.example.anotherweatherapp.common.BasePresenter;
import com.example.anotherweatherapp.data.Storage;
import com.example.anotherweatherapp.data.api.WeatherApi;
import com.example.anotherweatherapp.data.model.HourlyForecastsInfo;
import com.example.anotherweatherapp.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    private MainView view;

    @Inject
    Storage mStorage;

    @Inject
    WeatherApi mApi;
    @Inject
    public MainPresenter() {
    }

    public void setView(MainView view) {
        this.view = view;
    }

    public void getLocation(Location location) {

        Log.d(MainFragment.TAG, "getLocation: " + location.getLatitude() + " " + location.getLongitude());
    }

    public void getHourlyForecast() {
        Log.d(MainFragment.TAG, "getHourlyForecast: ");
        compositeDisposable.add(
                mApi.getHourlyForcast("294021",
                        BuildConfig.API_KEY, "en-us", "true")
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(mStorage::insertHourlyForecast)
                        .onErrorReturn(throwable -> {
                            if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())){
                                return mStorage.getHourlyForecast();
                            }else {
                                 view.showError(throwable.getMessage());
                                List<HourlyForecastsInfo> hourlyForecastsInfo= new ArrayList<>();
                                hourlyForecastsInfo.clear();
                                return hourlyForecastsInfo;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> view.showRefresh())
                        .doFinally(view::hideRefresh)
                        .subscribe(
                                responses -> {
                                    Log.d(MainFragment.TAG, "Response is " + responses.size());
                                    view.showForecast(responses);
                                },

                                throwable -> view.showError(throwable.getMessage()))
        );
    }

}
