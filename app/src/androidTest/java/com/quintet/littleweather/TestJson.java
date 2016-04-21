package com.quintet.littleweather;

import android.test.AndroidTestCase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quintet.littleweather.bean.Weather;
import com.quintet.littleweather.bean.WeatherAPI;
import com.quintet.littleweather.https.ApiInterface;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hanami on 2016/4/20.
 */
public class TestJson extends AndroidTestCase {


    public void testJson() {

        Log.d("TestJson", "TestJson");

        Executor executor = Executors.newCachedThreadPool();

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(ApiInterface.HOST)
                .callbackExecutor(executor)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        Observer<Weather> observer = new Observer<Weather>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Weather weather) {
                String s = weather.toString();

                Log.d("TestJson", s);
            }
        };


        apiInterface.getWeatherAPI("北京", "e7de0d6bf92744a5aeb829bb3cf6c826")
                .subscribeOn(Schedulers.io())
                .filter(new Func1<WeatherAPI, Boolean>() {
                    @Override
                    public Boolean call(WeatherAPI weatherAPI) {
                        return weatherAPI.heWeatherDataService.get(0).status.equals("ok");
                    }
                })
                .map(new Func1<WeatherAPI, Weather>() { //返回了一个Observable发送Weather
                    @Override
                    public Weather call(WeatherAPI weatherAPI) {
                        return weatherAPI.heWeatherDataService.get(0);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }
}
