package com.quintet.littleweather.https;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by hanami on 2016/4/19.
 */
public class RetrofitSingleton {

    private static Retrofit retrofit;

    private static ApiInterface apiService;


    public static void initApiService() {

        //这是Android四种线程池分类中的一种，是一种线程数量不定的线程池，只有非核心线程
        Executor executor = Executors.newCachedThreadPool();

        //1.新建默认的Gson对象
        //Gson gson = new Gson();

        //2.可以自定义Gson的一些功能
        //Gson gson = new GsonBuilder().create();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(ApiInterface.HOST)
                .callbackExecutor(executor)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        //apiService的命名是Retrofit的惯例
        apiService = retrofit.create(ApiInterface.class);

    }

    public static ApiInterface getApiService() {
        if (apiService == null) {
            initApiService();
        }

        Log.d("RetrofitSingleton", "getApiService-----------------" + apiService.toString());
        return apiService;
    }

    public static void disposeFailure(Throwable t, Context context, View v) {
        if (t.toString().contains("GaiException")
                || t.toString().contains("SocketTimeoutException")) {
            Snackbar.make(v, "网络似乎有点儿问题", Snackbar.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
        }
    }


}
