package com.example.weatherforyou.repository;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.weatherforyou.App;
import com.example.weatherforyou.data.db.WeatherDatabase;
import com.example.weatherforyou.data.db.entities.CityEntity;
import com.example.weatherforyou.model.city.City;
import com.example.weatherforyou.model.city.CityImpl;
import com.example.weatherforyou.model.city.FavoriteCity;
import com.example.weatherforyou.model.city.FavoriteCityImpl;
import com.example.weatherforyou.utils.CityFinderByCoordinates;
import com.example.weatherforyou.viewmodel.PermissionViewModel;
import com.google.android.libraries.places.api.model.Place;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CityRepository{


    private WeatherDatabase database = App.AppInstance.getInstance().getDatabase();

    private Context context;

    private MutableLiveData<Integer> cityCount = new MutableLiveData<>();

    public CityRepository(Context context) {
        this.context = context;
    }

    public LiveData<Integer> getCityCount(){
        return cityCount;
    }


    public void calculateCityCount() {
        int size = database.getCityDao().getAll().size();
        Log.e("calculate", Integer.toString(size));
        cityCount.setValue(size);

    }

    public City getCityBy(int id){

        CityEntity entity = database.getCityDao().getAll().get(id);

        return new CityImpl(entity.name, entity.lat, entity.lon);
    }


    public void delete(City city){
        database.getCityDao().delete(new CityEntity(city.getName(), city.getLat(), city.getLon(),0));
    }

    public void saveCity(Place place){
        database.getCityDao().insert(new CityEntity(place.getName(), place.getLatLng().latitude, place.getLatLng().longitude, Calendar.getInstance().getTimeInMillis()));
    }


    public void saveCity(String cityName, Location location){

        database.getCityDao().insert(new CityEntity(cityName,location.getLatitude(), location.getLongitude(),  Calendar.getInstance().getTimeInMillis()));
    }

    public List<FavoriteCity> getFavoriteCities() {

        ArrayList<FavoriteCity> favoriteCities = new ArrayList<>();

        List<CityEntity> response = database.getCityDao().getAll();


        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        for(CityEntity entity: response){
            Date date = new Date(entity.lastUpdate);
            Log.e("time", date.toString());
            favoriteCities.add(new FavoriteCityImpl(entity.name, dateFormat.format(date), "17", Color.parseColor("#66707070"), entity.lat, entity.lon));
        }

        return favoriteCities;
    }


}
