package com.example.weatherforyou.di.module;

import android.app.Activity;

import com.example.weatherforyou.di.scope.ActivityScope;
import com.example.weatherforyou.utils.permission.PermissionManager;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    Activity activity;

    public MainActivityModule(Activity activity) {
        this.activity = activity;
    }


    @ActivityScope
    @Provides
    public PermissionManager providePermissionManager(){
        return new PermissionManager(activity);
    }
}
