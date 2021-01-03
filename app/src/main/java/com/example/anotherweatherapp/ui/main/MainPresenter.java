package com.example.anotherweatherapp.ui.main;

import android.util.Log;


import com.example.anotherweatherapp.common.BasePresenter;
import com.example.anotherweatherapp.data.Storage;
import com.example.anotherweatherapp.data.api.WeatherApi;
import com.example.anotherweatherapp.data.model.WeatherForecast;
import com.example.anotherweatherapp.utils.ApiUtils;
import com.example.anotherweatherapp.utils.MetricUtils;
import com.example.anotherweatherapp.BuildConfig;

import javax.inject.Inject;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Action;
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


    public void setLocation(String lon, String lat) {
        Log.d(MainFragment.TAG, "getLocation in presenter: " + lon + " " + lat);
        longitude = lon;
        latitude = lat;

    }

    public void getForecast() {
        Log.d(MainFragment.TAG, "getForecast: ");
        compositeDisposable.add(
                mApi.getForecast(latitude,
                        longitude, "minutely", BuildConfig.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(mStorage::insertAllForecast)
                        .onErrorReturn(throwable -> {
                            Log.d("TAG", "execeptionm: " + throwable.getClass());
                            if (ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
                                Log.d("TAG", "execeptionm: ");
                                return mStorage.getHourlyForecast();
                            } else {
                                return null;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> view.showRefresh())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Throwable {
                                view.hideRefresh();
                                view.addScrollListener();
                            }
                        })
                        .subscribe(
                                response -> {
                                    // Log.d(MainFragment.TAG, "Response is " + responses.size());
                                    showForecast(response);

                                },
                                throwable -> {
                                    view.showError(throwable.getMessage() + throwable.getClass());
                                    throwable.getCause().fillInStackTrace();
                                    throwable.printStackTrace();
                                }
                        )
        );
    }

    public void showForecast(WeatherForecast weatherForecast) {
        String iconPhrase = String.valueOf(weatherForecast.getCurrent().getWeather().get(0).getDescription());
        String currentTemp = String.valueOf(MetricUtils.kelvinToCelsius(weatherForecast.getCurrent().getTemp()));
        int iconId = weatherForecast.getCurrent().getWeather().get(0).getId();
        String currentWind = weatherForecast.getCurrent().getWindSpeed() + " m/s";
        String currentHumidity = weatherForecast.getCurrent().getHumidity() + "%";
        String currentVisibility = weatherForecast.getCurrent().getVisibility() / 1000 + " km";
        Long sunrise = weatherForecast.getCurrent().getSunrise();
        Long sunset = weatherForecast.getCurrent().getSunset();

        view.showCurrentForecast(iconPhrase, currentTemp, iconId, currentWind, currentHumidity, currentVisibility);
        view.showHourlyForecast(weatherForecast.getHourly());
        view.showDailyForecast(weatherForecast.getDaily());
        view.showSolarCycle(sunrise,sunset);
    }
}
