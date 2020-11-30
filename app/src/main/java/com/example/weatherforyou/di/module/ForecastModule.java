package com.example.weatherforyou.di.module;

import android.content.Context;

import com.example.weatherforyou.di.scope.FunctionScope;
import com.example.weatherforyou.model.city.City;
import com.example.weatherforyou.repository.CityRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ForecastModule {


    @FunctionScope
    @Provides
    public CityRepository provideCityRepository(Context context){
        return new CityRepository(context);
    }
}
