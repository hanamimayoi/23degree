package com.quintet.littleweather.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quintet.littleweather.bean.City;
import com.quintet.littleweather.bean.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/4/20.
 */
public class DBUtils {

    public static List<Province> getAllProvince(SQLiteDatabase db) {

        List<Province> provinceList = new ArrayList<>();

        Cursor cursor = db.rawQuery("select ProName, ProSort from T_Province", null);


        while (cursor.moveToNext()) {

            String proName = cursor.getString(cursor.getColumnIndex("ProName"));
            int proSort = cursor.getInt(cursor.getColumnIndex("ProSort"));


            Province province = new Province();
            province.ProName = proName;
            province.ProSort = proSort;

            provinceList.add(province);
        }

        cursor.close();

        return provinceList;

    }


    public static List<City> getAllCity(SQLiteDatabase db, int ProID) {
        List<City> cityList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select CityName from T_City where ProID = ?", new String[]{ProID + ""});
        while (cursor.moveToNext()) {
            String cityName = cursor.getString(cursor.getColumnIndex("CityName"));

            City city = new City();
            city.CityName = cityName;
            city.ProID = ProID;

            cityList.add(city);
        }
        cursor.close();
        return cityList;
    }

}
