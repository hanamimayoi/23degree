package com.quintet.littleweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 该工具类使用SharedPreference来保存一些常用的信息；
 */
public class SpTool {

    public static void putString(Context context,String key,String value){
        SharedPreferences sp=context.getSharedPreferences(MyConstant.CONFIG,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
    public static String getString(Context context,String key,String defauValue){
        SharedPreferences sp=context.getSharedPreferences(MyConstant.CONFIG,Context.MODE_PRIVATE);
        return sp.getString(key,defauValue);
    }
    public static void putInt(Context context,String key,int value){
        SharedPreferences sp=context.getSharedPreferences(MyConstant.CONFIG,Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }
    public static int getInt(Context context,String key,int defaultValue){
        SharedPreferences sp=context.getSharedPreferences(MyConstant.CONFIG,Context.MODE_PRIVATE);
        return sp.getInt(key,defaultValue);
    }
}
