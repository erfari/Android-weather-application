package com.example.weatherforyou.model.weatherapi;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.weatherforyou.data.db.entities.DailyEntity;
import com.example.weatherforyou.data.db.entities.HourlyEntity;
import com.example.weatherforyou.data.db.entities.InfoEntity;
import com.example.weatherforyou.data.db.entities.WeatherEntity;
import com.example.weatherforyou.model.city.City;
import com.example.weatherforyou.model.weather.WeatherInfoImpl;
import com.example.weatherforyou.model.weather.WeatherInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherApi {


    private ArrayList<WeatherService> weatherServices = new ArrayList<>();

    private MediatorLiveData<ForecastResponse> isUpdate = new MediatorLiveData<>();

    private ArrayList<ForecastResponse> forecastResponses = new ArrayList<>();

    private int update = 0;

    private City city;


    public boolean addService(WeatherService weatherService) {

        if (weatherService != null) {

            try {

                isUpdate.addSource(weatherService.getResponse(), new Observer<ForecastResponse>() {
                    @Override
                    public void onChanged(ForecastResponse response) {
                        forecastResponses.add(response);
                        Log.i("ForecastResponse", "msg" + response.getSource());
                        update++;
                        if (update == weatherServices.size()){
                            isUpdate.postValue(getMean(forecastResponses));
                        }

                    }
                });
                weatherServices.add(weatherService);
            }catch (IllegalArgumentException e){

            }

            return true;
        }

        return false;

    }

    public LiveData<ForecastResponse> getForResponse(){
        return isUpdate;
    }

    public void updateCurrentWeather(City city) {
        this.city = city;
        for (WeatherService weatherService : weatherServices) {

            new Thread(() -> {
                try {
                    weatherService.update(city);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ).start();

        }

    }

    public WeatherEntity mapToDatabaseModel(ForecastResponse response){

        ArrayList<DailyEntity> dailyEntities = new ArrayList<>();
        for(DailyResponse dailyResponse: response.getDailyResponse()){
            dailyEntities.add(new DailyEntity(dailyResponse.getCity(),dailyResponse.getTime(), dailyResponse.getMinTemp(), dailyResponse.getMaxTemp(), dailyResponse.getIconWithUrl()));
        }

        ArrayList<HourlyEntity> hourlyEntities = new ArrayList<>();
        for(HourlyResponse hourlyResponse: response.getHourlyResponse()){
            hourlyEntities.add(new HourlyEntity(hourlyResponse.getCity(),hourlyResponse.getTime(), hourlyResponse.getTemp(), hourlyResponse.getIconWithUrl()));
        }

        WeatherEntity entity = new WeatherEntity(city.getLat()
                , city.getLon()
                , city.getName()
                , response.getDescription()
                , response.getTemp()
                , 1
                , response.getIconId()
                , new InfoEntity(response.getHumidity().toString()
                , response.getVisibility().toString()
                , response.getWindSpeed().toString()
                , response.getWindDeg().toString()
                , response.getPressure().toString()
                , response.getPressure().toString()
                ,response.getSunRise()
                , response.getSunSet())
                ,dailyEntities
                ,hourlyEntities);

        return entity;
    }

    public WeatherInfo map(ForecastResponse response) {
        WeatherInfo result = new WeatherInfoImpl(
                response.getTemp().toString(),
                response.getHumidity().toString(),
                response.getVisibility().toString(),
                response.getWindSpeed().toString(),
                response.getWindDeg().toString(),
                "0",
                response.getPressure().toString(),
                response.getSunRise().toString(),
                response.getSunSet().toString());

        return result;
    }

   public ForecastResponse getMean(List<ForecastResponse> forecastResponses){

        ForecastResponseImpl result = new ForecastResponseImpl("", "",0.0,0,0,0,0,0.0,0.0,0, forecastResponses.get(0).getDailyResponse(), forecastResponses.get(0).getHourlyResponse());





        for (ForecastResponse forecastResponse: forecastResponses){

            if (forecastResponse.getSource().equals("openWeather")){
                result.setDescription(forecastResponse.getDescription());
                result.setIconId(forecastResponse.getIconId());
                result.setSunRise(forecastResponse.getSunRise());
                result.setSunSet(forecastResponse.getSunSet());
                result.setDailyResponses(forecastResponse.getDailyResponse());
                result.setHourlyResponses(forecastResponse.getHourlyResponse());
            }

            Log.e("Size", String.valueOf(forecastResponses.size()));


            result.setTemp(forecastResponse.getTemp() + result.getTemp());
            result.setPressure(forecastResponse.getPressure() + result.getPressure());
            result.setHumidity(forecastResponse.getHumidity() + result.getHumidity());
            result.setWindSpeed(forecastResponse.getWindSpeed() + result.getWindSpeed());
            result.setWindDeg(forecastResponse.getWindDeg() + result.getWindDeg());
            result.setVisibility(forecastResponse.getVisibility() + result.getVisibility());

        }

       result.setTemp(result.getTemp()/forecastResponses.size());
       result.setPressure( result.getPressure()/forecastResponses.size());
       result.setHumidity(result.getHumidity()/forecastResponses.size());
       result.setWindSpeed(result.getWindSpeed()/forecastResponses.size());
       result.setWindDeg(result.getWindDeg()/forecastResponses.size());
       result.setVisibility(result.getVisibility()/forecastResponses.size());



       return result;

   }
}
