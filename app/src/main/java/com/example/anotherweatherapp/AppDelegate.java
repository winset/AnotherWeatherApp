package com.example.anotherweatherapp;

import android.app.Application;

import com.example.anotherweatherapp.di.AppComponent;
import com.example.anotherweatherapp.di.AppModule;
import com.example.anotherweatherapp.di.DaggerAppComponent;
import com.example.anotherweatherapp.di.NetworkModule;

public class AppDelegate extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule()).build();

    }

    public static AppComponent getAppComponent(){
        return appComponent;
    }
}
