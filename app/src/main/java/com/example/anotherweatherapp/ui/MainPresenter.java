package com.example.anotherweatherapp.ui;

import android.util.Log;

import com.example.anotherweatherapp.BuildConfig;
import com.example.anotherweatherapp.common.BasePresenter;
import com.example.anotherweatherapp.data.Storage;
import com.example.anotherweatherapp.data.api.WeatherApi;
import com.example.anotherweatherapp.data.model.Example;
import com.example.anotherweatherapp.utils.ApiUtils;

import javax.inject.Inject;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.InjectViewState;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    private MainView view;
    private String longitude;
    private String latitude;

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

    public void setLocation(String lon,String lat) {
        longitude = lon;
        latitude = lat;
        Log.d(MainFragment.TAG, "getLocation in presenter: " + lon + " " + lat);
    }

    public void getForecast() {
        Log.d(MainFragment.TAG, "getForecast: ");
        compositeDisposable.add(
                mApi.getForecast(latitude,
                      longitude, "minutely",BuildConfig.API_KEY)
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
                                    showForecast(response);

                                },
                                throwable -> {
                                    view.showError(throwable.getMessage()+ throwable.getClass() );
                                    throwable.getCause().fillInStackTrace();
                                throwable.printStackTrace();}
                        )
        );
    }
    public void showForecast(Example example){
        view.showCurrentForecast(example);
        view.showHourlyForecast(example.getHourly());
        view.showDailyForecast(example.getDaily());
    }
}
