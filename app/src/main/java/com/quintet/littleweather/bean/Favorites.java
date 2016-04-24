package com.quintet.littleweather.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hanami on 2016/4/24.
 */
public class Favorites {
    @SerializedName("cities")
    public List<City> cities;

    public class City {
        public City(String name) {
            this.name = name;
        }

        @SerializedName("city")
        public String name;
    }

}
