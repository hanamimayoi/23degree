package com.quintet.littleweather.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.quintet.littleweather.base.BaseApplication;

/**
 * 设置
 * <p>
 * 单例
 * <p>
 * Created by hanami on 2016/4/19.
 */
public class Setting {

    public static final String CHANGE_ICONS = "change_icons";//更换图标，图标1，图标2

    public static final String CLEAR_CACHE = "clear_cache";//清理缓存

    public static final String AUTO_UPDATE = "change_update_time";//自动更新时长.0,1,3,6

    public static final String CITY_NAME = "城市"; //在setting里保存的城市名字

    public static final String HOUR = "小时"; //当前时间的小时

    public static final String HOUR_SELECT = "hour_select";//更新频率，和AUTO_UPDATE的区别？


    public static final String KEY = "e7de0d6bf92744a5aeb829bb3cf6c826";//和风天气的KEY

    public static final int ONE_HOUR = 3600;//一小时


    private SharedPreferences mPrefs;

    private static Setting settingInstance;

    /**
     * 获取Setting的实例
     *
     * @return
     */
    public static Setting getInstance() {

        if (settingInstance == null) {
            settingInstance = new Setting(BaseApplication.applicationContext);
        }

        return settingInstance;
    }

    /**
     * 单例模式，私有化构造器
     */
    private Setting(Context context) {
        mPrefs = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        mPrefs.edit().putString(key, value).commit();
    }

    public String getString(String key, String defaultValue) {
        return mPrefs.getString(key, defaultValue);
    }

    public void putInt(String key, int value) {
        mPrefs.edit().putInt(key, value).commit();
    }

    public int getInt(String key, int defaultValue) {
        return mPrefs.getInt(key, defaultValue);
    }

}
