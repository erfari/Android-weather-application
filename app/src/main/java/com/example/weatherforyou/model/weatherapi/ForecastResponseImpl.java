package com.example.weatherforyou.model.weatherapi;

import java.util.List;

public class ForecastResponseImpl implements ForecastResponse {

    private String description;

    private String iconId;

    private Double temp;

    private Integer sunRise;

    private Integer sunSet;

    private Integer pressure;

    private Integer humidity;

    private Double windSpeed;

    private Double windDeg;

    private Integer visibility;

    private List<DailyResponse> dailyResponses;

    private List<HourlyResponse> hourlyResponses;

    private String source;


    public ForecastResponseImpl(String description, String iconId, Double temp, Integer sunRise, Integer sunSet, Integer pressure, Integer humidity, Double windSpeed, Double windDeg, Integer visibility, List<DailyResponse> dailyResponses, List<HourlyResponse> hourlyResponses, String source) {
        this.description = description;
        this.iconId = iconId;
        this.temp = temp;
        this.sunRise = sunRise;
        this.sunSet = sunSet;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.visibility = visibility;
        this.dailyResponses = dailyResponses;
        this.hourlyResponses = hourlyResponses;
        this.source = source;
    }

    public ForecastResponseImpl(String description, String iconId, Double temp, Integer sunRise, Integer sunSet, Integer pressure, Integer humidity, Double windSpeed, Double windDeg, Integer visibility, List<DailyResponse> dailyResponses, List<HourlyResponse> hourlyResponses) {
        this.description = description;
        this.iconId = iconId;
        this.temp = temp;
        this.sunRise = sunRise;
        this.sunSet = sunSet;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDeg = windDeg;
        this.visibility = visibility;
        this.dailyResponses = dailyResponses;
        this.hourlyResponses = hourlyResponses;
    }

    public void setDailyResponses(List<DailyResponse> dailyResponses) {
        this.dailyResponses = dailyResponses;
    }

    public void setHourlyResponses(List<HourlyResponse> hourlyResponses) {
        this.hourlyResponses = hourlyResponses;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public void setSunRise(Integer sunRise) {
        this.sunRise = sunRise;
    }

    public void setSunSet(Integer sunSet) {
        this.sunSet = sunSet;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDeg(Double windDeg) {
        this.windDeg = windDeg;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    @Override
    public String getSource() {
        return null;
    }

    @Override
    public String getIconId() {
        return iconId;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Double getTemp() {
        return temp;
    }

    @Override
    public Integer getSunRise() {
        return sunRise;
    }

    @Override
    public Integer getSunSet() {
        return sunSet;
    }

    @Override
    public Integer getPressure() {
        return pressure;
    }

    @Override
    public Integer getHumidity() {
        return humidity;
    }

    @Override
    public Double getWindSpeed() {
        return windSpeed;
    }

    @Override
    public Double getWindDeg() {
        return windDeg;
    }

    @Override
    public Integer getVisibility() {
        return visibility;
    }

    @Override
    public List<DailyResponse> getDailyResponse() {
        return dailyResponses;
    }

    @Override
    public List<HourlyResponse> getHourlyResponse() {
        return hourlyResponses;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }
}
