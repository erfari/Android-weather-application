package com.example.weatherforyou.model.weatherapi;

import androidx.room.PrimaryKey;

import com.example.weatherforyou.data.db.entities.DailyEntity;
import com.example.weatherforyou.data.db.entities.HourlyEntity;
import com.example.weatherforyou.data.db.entities.InfoEntity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ForecastResponse {

    String getSource();

    String getIconId();

    String getDescription();

    Double getTemp();

    Integer getSunRise();

    Integer getSunSet();

    Integer getPressure();

    Integer getHumidity();

    Double getWindSpeed();

    Double getWindDeg();

    Integer getVisibility();

    List<DailyResponse> getDailyResponse();

    List<HourlyResponse> getHourlyResponse();
}
