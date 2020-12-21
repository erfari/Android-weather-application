package com.example.weatherforyou.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "city")
public class CityEntity {

    @PrimaryKey
    @NotNull
    public String name;

    public double lat;

    public double lon;

    public long lastUpdate;


    public CityEntity(@NotNull String name, Double lat, Double lon, long lastUpdate) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.lastUpdate = lastUpdate;
    }
}
