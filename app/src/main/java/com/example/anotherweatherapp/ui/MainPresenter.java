package com.example.anotherweatherapp.ui;

import android.location.Location;
import android.util.Log;

import com.example.anotherweatherapp.BuildConfig;
import com.example.anotherweatherapp.common.BasePresenter;
import com.example.anotherweatherapp.data.Storage;
import com.example.anotherweatherapp.data.api.WeatherApi;
import com.example.anotherweatherapp.utils.ApiUtils;

import javax.inject.Inject;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
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
                mApi.getForecast("33.441792",
                      "-94.037689", "minutely",BuildConfig.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(mStorage::insertAllForecast)
                        .onErrorReturn(throwable -> {
                            Log.d("TAG", "execeptionm: "+ throwable.getClass());
                            if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())){
                                Log.d("TAG", "execeptionm: ");
                                return mStorage.getHourlyForecast();
                            }else {
                            //     view.showError(throwable.getMessage());
                                return null;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> view.showRefresh())
                        .doFinally(view::hideRefresh)
                        .subscribe(
                                response -> {
                                   // Log.d(MainFragment.TAG, "Response is " + responses.size());
                                    view.showForecast(response);
                                },

                                throwable -> {
                                    view.showError(throwable.getMessage()+ throwable.getClass() );
                                    throwable.getCause().fillInStackTrace();
                                throwable.printStackTrace();}
                        )
        );
    }

}
