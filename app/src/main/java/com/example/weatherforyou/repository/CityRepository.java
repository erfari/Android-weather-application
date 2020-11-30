package com.example.weatherforyou.repository;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;

import com.example.weatherforyou.App;
import com.example.weatherforyou.data.db.WeatherDatabase;
import com.example.weatherforyou.data.db.entities.CityEntity;
import com.example.weatherforyou.model.city.City;
import com.example.weatherforyou.model.city.CityImpl;
import com.example.weatherforyou.model.city.FavoriteCity;
import com.example.weatherforyou.model.city.FavoriteCityImpl;
import com.example.weatherforyou.ui.MainActivity;
import com.example.weatherforyou.utils.CityFinderByCoordinates;
import com.facebook.stetho.common.ArrayListAccumulator;
import com.google.android.libraries.places.api.model.Place;

import java.util.ArrayList;
import java.util.List;

public class CityRepository implements LocationListener {


    private WeatherDatabase database = App.AppInstance.getInstance().getDatabase();

    private Context context;

    private MutableLiveData<Integer> cityCount = new MutableLiveData<>();

    LocationManager locationManager;

    public CityRepository(Context context) {
        this.context = context;
    }

    public LiveData<Integer> getCityCount(){
        return cityCount;
    }

    @SuppressLint("MissingPermission")
    public int calculateCityCount(LocationManager locationManager, Context context) {
        this.locationManager = locationManager;
        int size = database.getCityDao().getAll().size();
        if (size == 0) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        }else {
            cityCount.setValue(size);
        }

        return 0;
    }

    public City getCityBy(int id){
        CityEntity entity = database.getCityDao().getAll().get(id);

        return new CityImpl(entity.name, entity.lat, entity.lon);
    }


    public void delete(City city){
        database.getCityDao().delete(new CityEntity(city.getName(), city.getLat(), city.getLon(),0));
    }

    public void saveCity(Place place){
        database.getCityDao().insert(new CityEntity(place.getName(), place.getLatLng().latitude, place.getLatLng().longitude, 0));
    }

    public List<FavoriteCity> getFavoriteCities() {

        ArrayList<FavoriteCity> favoriteCities = new ArrayList<>();

        List<CityEntity> response = database.getCityDao().getAll();
        for(CityEntity entity: response){
            favoriteCities.add(new FavoriteCityImpl(entity.name, "15:44", "17", Color.parseColor("#FFFFFF")));
        }

        return favoriteCities;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        String name = CityFinderByCoordinates.getCityName(context, location.getLatitude(), location.getLongitude());
        database.getCityDao().insert(new CityEntity(name, location.getLatitude(),location.getLongitude(), 0));
        cityCount.postValue(database.getCityDao().getAll().size());
        locationManager.removeUpdates(this);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
