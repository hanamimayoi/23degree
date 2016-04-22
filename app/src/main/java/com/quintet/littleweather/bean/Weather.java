package com.quintet.littleweather.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hanami on 2016/4/19.
 */
public class Weather {

    @Override
    public String toString() {
        return "Weather{" +
                "aqi=" + aqi +
                ", basic=" + basic +
                ", dailyForecast=" + dailyForecast +
                ", hourlyForecast=" + hourlyForecast +
                ", now=" + now +
                ", status='" + status + '\'' +
                ", suggestion=" + suggestion +
                '}';
    }

    /**
     * 空气质量指数
     */
    @SerializedName("aqi")
    @Expose
    public AqiEntity aqi;

    /**
     * 城市基本信息
     */
    @SerializedName("basic")
    @Expose
    public BasicEntity basic;

    /**
     * 天气预报
     */
    @SerializedName("daily_forecast")
    @Expose
    public List<DailyForecastEntity> dailyForecast;

    /**
     * 每小时天气预报
     */
    @SerializedName("hourly_forecast")
    @Expose
    public List<HourlyForecastEntity> hourlyForecast;

    /**
     * 实况天气
     */
    @SerializedName("now")
    @Expose
    public NowEntity now;

    /**
     * 返回状态，错误代码等等
     */
    @SerializedName("status")
    @Expose
    public String status;

    /**
     * 生活指数
     */
    @SerializedName("suggestion")
    @Expose
    public SuggestionEntity suggestion;


    /**
     * 空气质量指数实体类
     */
    public class AqiEntity {

        @Override
        public String toString() {
            return "AqiEntity{" +
                    "city=" + city +
                    '}';
        }

        /**
         * 城市数据
         */
        @SerializedName("city")
        @Expose
        public CityEntity city;

        /**
         * 城市数据实体类
         */
        public class CityEntity {

            @Override
            public String toString() {
                return "CityEntity{" +
                        "aqi='" + aqi + '\'' +
                        ", co='" + co + '\'' +
                        ", no2='" + no2 + '\'' +
                        ", o3='" + o3 + '\'' +
                        ", pm10='" + pm10 + '\'' +
                        ", pm25='" + pm25 + '\'' +
                        ", qlty='" + qlty + '\'' +
                        ", so2='" + so2 + '\'' +
                        '}';
            }

            /**
             * 空气质量指数
             */
            @SerializedName("aqi")
            @Expose
            public String aqi;

            /**
             * 一氧化碳一小时平均值
             */
            @SerializedName("co")
            @Expose
            public String co;

            /**
             * 二氧化氮一小时平均值
             */
            @SerializedName("no2")
            @Expose
            public String no2;

            /**
             * 臭氧一小时平均值
             */
            @SerializedName("o3")
            @Expose
            public String o3;

            /**
             * pm10一小时平均值
             */
            @SerializedName("pm10")
            @Expose
            public String pm10;

            /**
             * pm2.5一小时平均值
             */
            @SerializedName("pm25")
            @Expose
            public String pm25;

            /**
             * 空气质量类别
             */
            @SerializedName("qlty")
            @Expose
            public String qlty;

            /**
             * 二氧化硫一小时平均值
             */
            @SerializedName("so2")
            @Expose
            public String so2;
        }
    }

    /**
     * 城市基础信息实体类
     */
    public class BasicEntity {

        @Override
        public String toString() {
            return "BasicEntity{" +
                    "city='" + city + '\'' +
                    ", cnty='" + cnty + '\'' +
                    ", id='" + id + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lon='" + lon + '\'' +
                    ", update=" + update +
                    '}';
        }

        /**
         * 城市名称
         */
        @SerializedName("city")
        @Expose
        public String city;

        /**
         * 国家名称
         */
        @SerializedName("cnty")
        @Expose
        public String cnty;

        /**
         * 城市id
         */
        @SerializedName("id")
        @Expose
        public String id;

        /**
         * 纬度
         */
        @SerializedName("lat")
        @Expose
        public String lat;

        /**
         * 经度
         */
        @SerializedName("lon")
        @Expose
        public String lon;

        /**
         * 数据更新时间，24小时制
         */
        @SerializedName("update")
        @Expose
        public UpdateEntity update;

        /**
         * 数据更新时间实体类
         */
        public class UpdateEntity {

            @Override
            public String toString() {
                return "UpdateEntity{" +
                        "loc='" + loc + '\'' +
                        ", utc='" + utc + '\'' +
                        '}';
            }

            /**
             * 数据更新的当地时间
             */
            @SerializedName("loc")
            @Expose
            public String loc;

            /**
             * 数据更新的UTC时间
             */
            @SerializedName("utc")
            @Expose
            public String utc;
        }

    }

    /**
     * 实况天气的实体类
     */
    public class NowEntity {

        @Override
        public String toString() {
            return "NowEntity{" +
                    "cond=" + cond +
                    ", fl='" + fl + '\'' +
                    ", hum='" + hum + '\'' +
                    ", pcpn='" + pcpn + '\'' +
                    ", pres='" + pres + '\'' +
                    ", tmp='" + tmp + '\'' +
                    ", vis='" + vis + '\'' +
                    ", wind=" + wind +
                    '}';
        }

        /**
         * 天气状况
         */
        @SerializedName("cond")
        @Expose
        public CondEntity cond;

        /**
         * 体感温度
         */
        @SerializedName("fl")
        @Expose
        public String fl;

        /**
         * 湿度
         */
        @SerializedName("hum")
        @Expose
        public String hum;

        /**
         * 降雨量 (mm)
         */
        @SerializedName("pcpn")
        @Expose
        public String pcpn;

        /**
         * 气压
         */
        @SerializedName("pres")
        @Expose
        public String pres;

        /**
         * 当前温度 (摄氏度)
         */
        @SerializedName("tmp")
        @Expose
        public String tmp;

        /**
         * 能见度
         */
        @SerializedName("vis")
        @Expose
        public String vis;

        /**
         * 风力状况
         */
        @SerializedName("wind")
        @Expose
        public WindEntity wind;

        /**
         * 天气状况的实体类
         */
        public class CondEntity {


            @Override
            public String toString() {
                return "CondEntity{" +
                        "code='" + code + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * 天气代码
             */
            @SerializedName("code")
            @Expose
            public String code;

            /**
             * 天气描述
             */
            @SerializedName("txt")
            @Expose
            public String txt;
        }

        /**
         * 风力状况的实体类
         */
        public class WindEntity {

            @Override
            public String toString() {
                return "WindEntity{" +
                        "deg='" + deg + '\'' +
                        ", dir='" + dir + '\'' +
                        ", sc='" + sc + '\'' +
                        ", spd='" + spd + '\'' +
                        '}';
            }

            /**
             * 风向（角度）
             */
            @SerializedName("deg")
            @Expose
            public String deg;

            /**
             * 风向（方向）
             */
            @SerializedName("dir")
            @Expose
            public String dir;

            /**
             * 风力等级
             */
            @SerializedName("sc")
            @Expose
            public String sc;

            /**
             * 风速
             */
            @SerializedName("spd")
            @Expose
            public String spd;

        }
    }

    /**
     * 生活指数的实体类
     */
    public class SuggestionEntity {

        @Override
        public String toString() {
            return "SuggestionEntity{" +
                    "comf=" + comf +
                    ", cw=" + cw +
                    ", drsg=" + drsg +
                    ", flu=" + flu +
                    ", sport=" + sport +
                    ", trav=" + trav +
                    ", uv=" + uv +
                    '}';
        }

        /**
         * 舒适程度
         */
        @SerializedName("comf")
        @Expose
        public ComfEntity comf;

        /**
         * 洗车指数
         */
        @SerializedName("cw")
        @Expose
        public CwEntity cw;

        /**
         * 穿衣指数
         */
        @SerializedName("drsg")
        @Expose
        public DrsgEntity drsg;

        /**
         * 感冒指数
         */
        @SerializedName("flu")
        @Expose
        public FluEntity flu;


        /**
         * 运动指数
         */
        @SerializedName("sport")
        @Expose
        public SportEntity sport;

        /**
         * 旅游指数
         */
        @SerializedName("trav")
        @Expose
        public TravEntity trav;

        /**
         * 紫外线指数
         */
        @SerializedName("uv")
        @Expose
        public UvEntity uv;

        /**
         * 舒适程度的实体类
         */
        public class ComfEntity {

            @Override
            public String toString() {
                return "ComfEntity{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * 简介
             */
            @SerializedName("brf")
            @Expose
            public String brf;

            /**
             * 详情
             */
            @SerializedName("txt")
            @Expose
            public String txt;
        }

        /**
         * 洗车指数实体类
         */
        public class CwEntity {

            @Override
            public String toString() {
                return "CwEntity{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * 简介
             */
            @SerializedName("brf")
            @Expose
            public String brf;

            /**
             * 详情
             */
            @SerializedName("txt")
            @Expose
            public String txt;
        }

        /**
         * 穿衣指数实体类
         */
        public class DrsgEntity {

            @Override
            public String toString() {
                return "DrsgEntity{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * 简介
             */
            @SerializedName("brf")
            @Expose
            public String brf;

            /**
             * 详情
             */
            @SerializedName("txt")
            @Expose
            public String txt;
        }

        /**
         * 感冒指数实体类
         */
        public class FluEntity {

            @Override
            public String toString() {
                return "FluEntity{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * 简介
             */
            @SerializedName("brf")
            @Expose
            public String brf;

            /**
             * 详情
             */
            @SerializedName("txt")
            @Expose
            public String txt;
        }

        /**
         * 运动指数实体类
         */
        public class SportEntity {

            @Override
            public String toString() {
                return "SportEntity{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * 简介
             */
            @SerializedName("brf")
            @Expose
            public String brf;

            /**
             * 详情
             */
            @SerializedName("txt")
            @Expose
            public String txt;
        }

        /**
         * 旅游指数实体类
         */
        public class TravEntity {

            @Override
            public String toString() {
                return "TravEntity{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * 简介
             */
            @SerializedName("brf")
            @Expose
            public String brf;

            /**
             * 详情
             */
            @SerializedName("txt")
            @Expose
            public String txt;
        }

        /**
         * 紫外线指数的实体类
         */
        public class UvEntity {

            @Override
            public String toString() {
                return "UvEntity{" +
                        "brf='" + brf + '\'' +
                        ", txt='" + txt + '\'' +
                        '}';
            }

            /**
             * 简介
             */
            @SerializedName("brf")
            @Expose
            public String brf;

            /**
             * 详情
             */
            @SerializedName("txt")
            @Expose
            public String txt;
        }
    }

    /**
     * 天气预报的实体类
     */
    public class DailyForecastEntity {

        @Override
        public String toString() {
            return "DailyForecastEntity{" +
                    "astro=" + astro +
                    ", cond=" + cond +
                    ", tmp=" + tmp +
                    ", wind=" + wind +
                    ", date='" + date + '\'' +
                    ", hum='" + hum + '\'' +
                    ", pcpn='" + pcpn + '\'' +
                    ", pop='" + pop + '\'' +
                    ", pres='" + pres + '\'' +
                    ", vis='" + vis + '\'' +
                    '}';
        }

        /**
         * 天文数值
         */
        @SerializedName("astro")
        @Expose
        public AstroEntity astro;

        /**
         * 天气状况
         */
        @SerializedName("cond")
        @Expose
        public CondEntity cond;


        /**
         * 温度
         */
        @SerializedName("tmp")
        @Expose
        public TmpEntity tmp;

        /**
         * 风力状况
         */
        @SerializedName("wind")
        @Expose
        public WindEntity wind;

        /**
         * 当地日期
         */
        @SerializedName("date")
        @Expose
        public String date;

        /**
         * 湿度
         */
        @SerializedName("hum")
        @Expose
        public String hum;

        /**
         * 降雨量 (mm)
         */
        @SerializedName("pcpn")
        @Expose
        public String pcpn;

        /**
         * 降水概率
         */
        @SerializedName("pop")
        @Expose
        public String pop;

        /**
         * 气压
         */
        @SerializedName("pres")
        @Expose
        public String pres;

        /**
         * 能见度
         */
        @SerializedName("vis")
        @Expose
        public String vis;

        /**
         * 天文数值的实体类
         */
        public class AstroEntity {

            @Override
            public String toString() {
                return "AstroEntity{" +
                        "sr='" + sr + '\'' +
                        ", ss='" + ss + '\'' +
                        '}';
            }

            /**
             * 日出时间
             */
            @SerializedName("sr")
            @Expose
            public String sr;

            /**
             * 日落时间
             */
            @SerializedName("ss")
            @Expose
            public String ss;

        }

        /**
         * 天气状况的实体类
         */
        public class CondEntity {

            @Override
            public String toString() {
                return "CondEntity{" +
                        "codeD='" + codeD + '\'' +
                        ", codeN='" + codeN + '\'' +
                        ", txtD='" + txtD + '\'' +
                        ", txtN='" + txtN + '\'' +
                        '}';
            }

            /**
             * 白天天气代码
             */
            @SerializedName("code_d")
            @Expose
            public String codeD;

            /**
             * 夜晚天气代码
             */
            @SerializedName("code_n")
            @Expose
            public String codeN;

            /**
             * 白天天气描述
             */
            @SerializedName("txt_d")
            @Expose
            public String txtD;

            /**
             * 夜晚天气描述
             */
            @SerializedName("txt_n")
            @Expose
            public String txtN;

        }

        /**
         * 温度的实体类
         */
        public class TmpEntity {

            @Override
            public String toString() {
                return "TmpEntity{" +
                        "max='" + max + '\'' +
                        ", min='" + min + '\'' +
                        '}';
            }

            /**
             * 最高温度
             */
            @SerializedName("max")
            @Expose
            public String max;

            /**
             * 最低温度
             */
            @SerializedName("min")
            @Expose
            public String min;

        }

        /**
         * 风力状况的实体类
         */
        public class WindEntity {

            @Override
            public String toString() {
                return "WindEntity{" +
                        "deg='" + deg + '\'' +
                        ", dir='" + dir + '\'' +
                        ", sc='" + sc + '\'' +
                        ", spd='" + spd + '\'' +
                        '}';
            }

            /**
             * 风向（角度）
             */
            @SerializedName("deg")
            @Expose
            public String deg;

            /**
             * 风向（方向）
             */
            @SerializedName("dir")
            @Expose
            public String dir;

            /**
             * 风力等级
             */
            @SerializedName("sc")
            @Expose
            public String sc;

            /**
             * 风速
             */
            @SerializedName("spd")
            @Expose
            public String spd;
        }

    }

    /**
     * 每小时天气预报的实体类
     */
    public class HourlyForecastEntity {

        @Override
        public String toString() {
            return "HourlyForecastEntity{" +
                    "date='" + date + '\'' +
                    ", hum='" + hum + '\'' +
                    ", pop='" + pop + '\'' +
                    ", pres='" + pres + '\'' +
                    ", tmp='" + tmp + '\'' +
                    ", wind=" + wind +
                    '}';
        }

        /**
         * 当地日期
         */
        @SerializedName("date")
        @Expose
        public String date;


        /**
         * 湿度
         */
        @SerializedName("hum")
        @Expose
        public String hum;


        /**
         * 降水概率
         */
        @SerializedName("pop")
        @Expose
        public String pop;

        /**
         * 气压
         */
        @SerializedName("pres")
        @Expose
        public String pres;

        /**
         * 当前温度 (摄氏度)
         */
        @SerializedName("tmp")
        @Expose
        public String tmp;

        /**
         * 风力状况
         */
        @SerializedName("wind")
        @Expose
        public WindEntity wind;

        /**
         * 风力状况的实体类
         */
        public class WindEntity {

            @Override
            public String toString() {
                return "WindEntity{" +
                        "deg='" + deg + '\'' +
                        ", dir='" + dir + '\'' +
                        ", sc='" + sc + '\'' +
                        ", spd='" + spd + '\'' +
                        '}';
            }

            /**
             * 风向（角度）
             */
            @SerializedName("deg")
            @Expose
            public String deg;

            /**
             * 风向（方向）
             */
            @SerializedName("dir")
            @Expose
            public String dir;

            /**
             * 风力等级
             */
            @SerializedName("sc")
            @Expose
            public String sc;

            /**
             * 风速
             */
            @SerializedName("spd")
            @Expose
            public String spd;

        }
    }
}
