package com.example.anotherweatherapp.di;


import androidx.room.Room;

import com.example.anotherweatherapp.AppDelegate;
import com.example.anotherweatherapp.data.Storage;
import com.example.anotherweatherapp.data.database.WeatherDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private AppDelegate application;

    public AppModule(AppDelegate application) {
        this.application = application;
    }

    @Provides
    @Singleton
    AppDelegate getApplication(){
        return application;
    }

    @Provides
    @Singleton
    Storage provideStorage(){
        final WeatherDatabase weatherDatabase = Room.databaseBuilder(application,WeatherDatabase.class,"another_weather")
            .build();
        return new Storage(weatherDatabase.getWeatherDao());
    }

}
