package com.quintet.littleweather.https;

import com.quintet.littleweather.bean.WeatherAPI;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by hanami on 2016/4/19.
 */
public interface ApiInterface {

    /**
     * 示例
     * https://api.heweather.com/x3/weather?cityid=CN101010100&key=e7de0d6bf92744a5aeb829bb3cf6c826
     */

    String HOST = "https://api.heweather.com/x3/";

    @GET("weather")
    Observable<WeatherAPI> mWeatherAPI(@Query("city") String city, @Query("key") String key);
}
