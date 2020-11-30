package com.example.weatherforyou.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weatherforyou.data.db.entities.WeatherEntity;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM weather")
    LiveData<WeatherEntity> getAll();

    @Query("SELECT * FROM weather WHERE cityName =:cityName")
    LiveData<WeatherEntity> getBy(String cityName);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherEntity weather);

    @Update
    void update(WeatherEntity weather);
}
