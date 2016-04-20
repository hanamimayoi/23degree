package com.quintet.littleweather.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hanami on 2016/4/19.
 */
public class WeatherAPI {

    @SerializedName("HeWeather data service 3.0")
    @Expose
    public List<Weather> heWeatherDataService;

    @Override
    public String toString() {
        return "WeatherAPI{" +
                "heWeatherDataService=" + heWeatherDataService +
                '}';
    }
}
