package com.example.weatherforyou;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.work.PeriodicWorkRequest;

import com.example.weatherforyou.data.db.WeatherDatabase;
import com.example.weatherforyou.di.component.ApplicationComponent;
import com.example.weatherforyou.di.component.DaggerApplicationComponent;
import com.example.weatherforyou.di.module.ApplicationModule;
import com.example.weatherforyou.ui.PushWorker;
import com.google.android.libraries.places.api.Places;

import java.util.concurrent.TimeUnit;

public class App extends Application {

    WeatherDatabase database;

    public PeriodicWorkRequest getMyWorkRequest() {
        return myWorkRequest;
    }

    PeriodicWorkRequest myWorkRequest;

    private ApplicationComponent applicationComponent;

    public WeatherDatabase getDatabase() {
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(getApplicationContext(), WeatherDatabase.class, "weatherdatabase").allowMainThreadQueries().build();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        myWorkRequest = new PeriodicWorkRequest.Builder(PushWorker.class, 30, TimeUnit.MINUTES, 25, TimeUnit.MINUTES)
                .build();


        if (!Places.isInitialized()) {
            Places.initialize(this.getApplicationContext(), "AIzaSyCs_3xUDy1n-m6UKp47vvpKflxtWqpHVY4");
        }

        AppInstance.setInstance(this);
    }


    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static class AppInstance {
        private static App mApp;

        public static void setInstance(App app) {
            mApp = app;
        }

        public static App getInstance() {
            return mApp;
        }

    }
}


