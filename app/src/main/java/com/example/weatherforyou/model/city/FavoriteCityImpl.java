package com.example.weatherforyou.model.city;

import androidx.annotation.ColorInt;

import java.util.zip.Inflater;

public class FavoriteCityImpl implements FavoriteCity {

    String name;

    String someTime;

    String temp;

    Double lat;

    Double lon;


    int color;

    public FavoriteCityImpl(String name, String someTime, String temp) {
        this.name = name;
        this.someTime = someTime;
        this.temp = temp;
    }

    public FavoriteCityImpl(String name, String someTime, String temp, int color, Double lat, Double lon) {
        this.name = name;
        this.someTime = someTime;
        this.temp = temp;
        this.color = color;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String getSomeTime() {
        return someTime;
    }

    @Override
    public String getCityName() {
        return name;
    }

    @Override
    public String getTemp() {
        return temp;
    }

    @Override
    public Double getLat() {
        return lat;
    }

    @Override
    public Double getLon() {
        return lon;
    }

    @Override
    public int backColor() {
        return color;
    }


}
