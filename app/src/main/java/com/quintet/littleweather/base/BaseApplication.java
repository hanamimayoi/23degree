package com.quintet.littleweather.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.quintet.littleweather.https.RetrofitSingleton;

/**
 * Created by hanami on 2016/4/19.
 */
public class BaseApplication extends Application {

    public static String cacheDir;

    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();

        //获得cacheDir的路径
        if (existSDCard()) {
            //如果存在SD卡，保存在SD卡上的私有缓存目录
            //toSting()的效果和getAbsolutePath的效果一样
            cacheDir = getExternalCacheDir().toString();
        } else {
            //如果不存在SD卡，保存在/data/data/<packageName>下的缓存目录
            cacheDir = getCacheDir().toString();
        }

        //初始化RetrofitSingleton
        RetrofitSingleton.init(getApplicationContext());

    }

    /**
     * 判断是否存在SD卡，用来确定缓存的位置
     *
     * @return
     */

    private boolean existSDCard() {
        //如果SD卡挂载正常
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;//正常挂载
        } else {
            return false;//不正常挂载或者无SD卡
        }
    }


}
