package com.example.anotherweatherapp.di;

import com.example.anotherweatherapp.ui.main.MainFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,NetworkModule.class})
public interface AppComponent {
    void inject(MainFragment injector);
}
