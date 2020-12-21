package com.example.weatherforyou.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weatherforyou.data.db.entities.WeatherEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface WeatherDao {


    @Query("SELECT * FROM weather ORDER BY cityName limit 1")
    LiveData<WeatherEntity> getCurrentCity();

    //выбор города по имени
    @Query("SELECT * FROM weather WHERE cityName =:cityName")
    LiveData<WeatherEntity> getBy(String cityName);

    //удаление города
    @Delete
    public void delete(WeatherEntity entity);

    //добавление города в бд
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(WeatherEntity weather);

    @Update
    public void update(WeatherEntity weather);

    //список городов
    @Query("SELECT cityName FROM weather")
    Observable<List<String>> getCityList();

    //там где нужен любой из этих методов вызываем
    //App.AppInstance.getInstance().getDatabase().getWeatherDao().имя_метода


}
