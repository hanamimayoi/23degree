package com.quintet.littleweather.Utils;

/**
 *项目中经常要使用到的一些常量
 */
public interface MyConstant {
    String CITY_NAME = "cityName";//城市的名称；
    String CONFIG = "config";//SharedPreference中一个配置文件的名称，用于保存一些常用信息;
    String AUTO_UPDATE="autoChangeTime";//表示高德定位自动更新的时间间隔；
    int ONE_HOUR=3600;//一小时3600秒；
}