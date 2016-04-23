package com.quintet.littleweather.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quintet.littleweather.R;
import com.quintet.littleweather.bean.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-4-19.
 */
   public class RecycleView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static private Context context;
    static private Weather weather;

    //建立枚举 4个item 类型
    public enum ITEM_TYPE {
        ITEM1,
        ITEM2,
        ITEM3,
        ITEM4
    }

    public RecycleView(Weather weather, Context context) {
        this.weather = weather;
        this.context = context;
    }

    //设置ITEM类型，可以自由发挥，这里设置item position单数显示item1 偶数显示item2
    @Override
    public int getItemViewType(int position) {
        //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM1.ordinal()代表0， ITEM_TYPE.ITEM2.ordinal()代表1
        int type = 0;
        switch (position) {
            case 0:
                type = ITEM_TYPE.ITEM1.ordinal();
                break;
            case 1:
                type = ITEM_TYPE.ITEM2.ordinal();
                break;
            case 2:
                type = ITEM_TYPE.ITEM3.ordinal();
                break;
            case 3:
                type = ITEM_TYPE.ITEM4.ordinal();
                break;
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    //在此处找到并加载RecycleView内不同item的布局。
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = null;
        //加载Item View的时候根据不同TYPE加载不同的布局,因为建立的是4个的枚举类型，所以只能加载四个item的布局。
        if (i == ITEM_TYPE.ITEM1.ordinal()) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleviewitemcontent1, viewGroup, false);
            holder = new itemView1(view);
        } else if (i == ITEM_TYPE.ITEM2.ordinal()) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleviewitemcontent2, viewGroup, false);
            holder = new itemView2(view);
        } else if (i == ITEM_TYPE.ITEM3.ordinal()) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleviewitemcontent3, viewGroup, false);
            holder = new itemView3(view);
        } else if (i == ITEM_TYPE.ITEM4.ordinal()) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleviewitemcontent4, viewGroup, false);
            holder = new itemView4(view);
        }
        return holder;
    }

    //在此处将布局控件中需要设置的项目都抽取出来。
    //item1的ViewHolder
    public static class itemView1 extends RecyclerView.ViewHolder {
        ImageView item1_weather_image;
        TextView item1_temp_flu_text;
        TextView item1_temp_max_text;
        TextView item1_temp_min_text;
        TextView item1_temp_pm;
        TextView item1_temp_quality;

        public itemView1(View itemView) {
            super(itemView);
            View view=View.inflate(context,R.layout.itemcontent1,null);
            //item1天气图标
            item1_weather_image = (ImageView) view.findViewById(R.id.iv_weather);
            //item1温度
            item1_temp_flu_text = (TextView) view.findViewById(R.id.tv_temp_flu);
            //item1最高温
            item1_temp_max_text = (TextView) view.findViewById(R.id.tv_temp_max);
            //item1最低温
            item1_temp_min_text = (TextView) view.findViewById(R.id.tv_temp_min);
            //item1 PM2.5值
            item1_temp_pm = (TextView) view.findViewById(R.id.tv_temp_pm);
            //item1空气质量
            item1_temp_quality = (TextView) view.findViewById(R.id.tv_temp_quality);
            View rl=itemView.findViewById(R.id.item1_RL);
            ((CardView)itemView).removeView(rl);
            ((CardView)itemView).addView(view);
        }
    }

    //item2的ViewHolder
    public static class itemView2 extends RecyclerView.ViewHolder {
        List<ImageView> item2_tvtime_image_list;
        List<TextView> item2_tvtime_text_list;
        List<ImageView> item2_tvtemp_image_list;
        List<TextView> item2_tvtemp_text_list;
        List<ImageView> item2_tvhumidity_image_list;
        List<TextView> item2_tvhumidity_text_list;
        List<ImageView> item2_tvwindspeed_image_list;
        List<TextView> item2_tvwindspeed_text_list;

        public itemView2(View itemView) {
            super(itemView);
            LinearLayout item2list = (LinearLayout) itemView.findViewById(R.id.item2_LL_theouterfirst);
            item2_tvtime_image_list = new ArrayList<ImageView>();
            item2_tvtime_text_list = new ArrayList<TextView>();
            item2_tvtemp_image_list = new ArrayList<ImageView>();
            item2_tvtemp_text_list = new ArrayList<TextView>();
            item2_tvhumidity_image_list = new ArrayList<ImageView>();
            item2_tvhumidity_text_list = new ArrayList<TextView>();
            item2_tvwindspeed_image_list = new ArrayList<ImageView>();
            item2_tvwindspeed_text_list = new ArrayList<TextView>();
            for (int i = 0; i < weather.hourlyForecast.size(); i++) {
                View viewitem = View.inflate(context, R.layout.itemcontent2item, null);
                //item2时钟图片
                item2_tvtime_image_list.add((ImageView) (viewitem.findViewById(R.id.item2_tvtime_image)));
                //item2时间点text
                item2_tvtime_text_list.add((TextView) (viewitem.findViewById(R.id.item2_tvtime_text)));
                //item2温度图片
                item2_tvtemp_image_list.add((ImageView) (viewitem.findViewById(R.id.item2_tvtemp_image)));
                //item2温度text
                item2_tvtemp_text_list.add((TextView) (viewitem.findViewById(R.id.item2_tvtemp_text)));
                //item2湿度图片
                item2_tvhumidity_image_list.add((ImageView) (viewitem.findViewById(R.id.item2_tvhumidity_image)));
                //item2湿度text
                item2_tvhumidity_text_list.add((TextView) (viewitem.findViewById(R.id.item2_tvhumidity_text)));
                //item2风速图片
                item2_tvwindspeed_image_list.add((ImageView) (viewitem.findViewById(R.id.item2_tvwindspeed_image)));
                //item2风速text
                item2_tvwindspeed_text_list.add((TextView) (viewitem.findViewById(R.id.item2_tvwindspeed_text)));
                item2list.addView(viewitem);
            }
        }
    }

    //item3的ViewHolder
    public static class itemView3 extends RecyclerView.ViewHolder {
        TextView item3_cloth_brief;
        TextView item3_sport_brief;
        TextView item3_travel_brief;
        TextView item3_flu_brief;
        ImageView item3_cloth_image;
        ImageView item3_sport_image;
        ImageView item3_travel_image;
        ImageView item3_flu_image;
        TextView item3_cloth_txt;
        TextView item3_sport_txt;
        TextView item3_travel_txt;
        TextView item3_flu_txt;

        public itemView3(View itemView) {
            super(itemView);
            //item3的穿衣图片
            item3_cloth_image = (ImageView) itemView.findViewById(R.id.item3_cloth_image);
            //item3的穿衣指数值
            item3_cloth_brief = (TextView) itemView.findViewById(R.id.cloth_brief);
            //item3的穿衣介绍
            item3_cloth_txt = (TextView) itemView.findViewById(R.id.cloth_txt);

            //item3的运动图片
            item3_sport_image = (ImageView) itemView.findViewById(R.id.item3_sport_image);
            //item3的运动指数值
            item3_sport_brief = (TextView) itemView.findViewById(R.id.sport_brief);
            //item3的运动介绍
            item3_sport_txt = (TextView) itemView.findViewById(R.id.sport_txt);

            //item3的旅游图片
            item3_travel_image = (ImageView) itemView.findViewById(R.id.item3_travel_image);
            //item3的旅游指数值
            item3_travel_brief = (TextView) itemView.findViewById(R.id.travel_brief);
            //item3的旅游介绍
            item3_travel_txt = (TextView) itemView.findViewById(R.id.travel_txt);

            //item3的感冒图片
            item3_flu_image = (ImageView) itemView.findViewById(R.id.item3_flu_image);
            //item3的感冒指数值
            item3_flu_brief = (TextView) itemView.findViewById(R.id.flu_brief);
            //item3的感冒介绍
            item3_flu_txt = (TextView) itemView.findViewById(R.id.flu_txt);
        }
    }

    //item4的ViewHolder
    public static class itemView4 extends RecyclerView.ViewHolder {
        List<ImageView> item4_left_image_list;
        List<TextView> item4_date_text_list;
        List<TextView> item4_temp_text_list;
        List<TextView> item4_advice_text_list;

        public itemView4(View itemView) {
            super(itemView);
            LinearLayout item4list = (LinearLayout) itemView.findViewById(R.id.item4_LL_theouterfirst);
            LinearLayout item4listinner= (LinearLayout) item4list.findViewById(R.id.item4_LL_theoutersecond);
            item4list.removeView(item4listinner);
            item4_left_image_list = new ArrayList<ImageView>();
            item4_date_text_list = new ArrayList<TextView>();
            item4_temp_text_list = new ArrayList<TextView>();
            item4_advice_text_list = new ArrayList<TextView>();
            for (int i = 0; i < weather.dailyForecast.size(); i++) {
                View viewitem4 = View.inflate(context, R.layout.itemcontent4item, null);
                item4_left_image_list.add((ImageView) (viewitem4.findViewById(R.id.item4_left_image)));
                item4_date_text_list.add((TextView) (viewitem4.findViewById(R.id.item4_date_text)));
                item4_temp_text_list.add((TextView) (viewitem4.findViewById(R.id.item4_temp_text)));
                item4_advice_text_list.add((TextView) (viewitem4.findViewById(R.id.item4_advice_text)));
                item4list.addView(viewitem4);
            }
        }
    }

    //在此向每项布局控件中抽取出来的项目填充你想要的东西，填充来源就是各个list。
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof itemView1) {
            //填充item1天气图标
            switch (weather.now.cond.txt) {
                case "未知":
                    (((itemView1) holder).item1_weather_image).setImageResource(R.mipmap.icon_none);
                    break;
                case "晴":
                    (((itemView1) holder).item1_weather_image).setImageResource(R.mipmap.type_one_sunny);
                    break;
                case "阴":
                    (((itemView1) holder).item1_weather_image).setImageResource(R.mipmap.type_one_cloudy);
                    break;
                case "多云":
                    (((itemView1) holder).item1_weather_image).setImageResource(R.mipmap.type_one_cloudy);
                    break;
                case "少云":
                    (((itemView1) holder).item1_weather_image).setImageResource(R.mipmap.type_one_cloudy);
                    break;
                case "晴间多云":
                    (((itemView1) holder).item1_weather_image).setImageResource(R.mipmap.type_one_cloudytosunny);
                    break;
                case "小雨":
                    (((itemView1) holder).item1_weather_image).setImageResource(R.mipmap.type_one_light_rain);
                    break;
                case "中雨":
                    (((itemView1) holder).item1_weather_image).setImageResource(R.mipmap.type_one_heavy_rain);
                    break;
                case "大雨":
                    (((itemView1) holder).item1_weather_image).setImageResource(R.mipmap.type_one_heavy_rain);
                    break;
                case "阵雨":
                    (((itemView1) holder).item1_weather_image).setImageResource(R.mipmap.type_one_thunderstorm);
                    break;
                case "雷阵雨":
                    (((itemView1) holder).item1_weather_image).setImageResource(R.mipmap.type_one_thunderstorm);
                    break;
                case "霾":
                    (((itemView1) holder).item1_weather_image).setImageResource(R.mipmap.type_one_fog);
                    break;
                case "雾":
                    (((itemView1) holder).item1_weather_image).setImageResource(R.mipmap.type_one_fog);
                    break;
            }
            //填充item1气温字符串
            String tempu=weather.now.tmp+"°C";
            (((itemView1) holder).item1_temp_flu_text).setText(tempu);
            //填充item1最高气温字符串
            String maxtemp="↑"+weather.dailyForecast.get(0).tmp.max+"°";
            (((itemView1) holder).item1_temp_max_text).setText(maxtemp);
            //填充item1最低气温字符串
            String mintemp="↓"+weather.dailyForecast.get(0).tmp.min+"°";
            (((itemView1) holder).item1_temp_min_text).setText(mintemp);
            //填充item1 PM2.5的字符串
            (((itemView1) holder).item1_temp_pm).setText("PM2.5值:"+weather.aqi.city.pm25);
            //填充item1空气质量字符串
            (((itemView1) holder).item1_temp_quality).setText("空气质量:"+weather.aqi.city.qlty);
        } else if (holder instanceof itemView2) {
            for (int i = 0; i < weather.hourlyForecast.size(); i++) {
                //填充item2的时刻表图片
                (((itemView2) holder).item2_tvtime_image_list).get(i).setImageResource(R.mipmap.icon_clock);
                //填充item2的时刻字符串
                String[] split = weather.hourlyForecast.get(i).date.split(" ");
                (((itemView2) holder).item2_tvtime_text_list).get(i).setText(split[1]);
                //填充item2的温度图片
                (((itemView2) holder).item2_tvtemp_image_list).get(i).setImageResource(R.mipmap.icon_temp);
                //填充item2的温度字符串
                (((itemView2) holder).item2_tvtemp_text_list).get(i).setText(weather.hourlyForecast.get(i).tmp);
                //填充item2的湿度图片
                (((itemView2) holder).item2_tvhumidity_image_list).get(i).setImageResource(R.mipmap.icon_shidu);
                //填充item2的湿度字符串
                (((itemView2) holder).item2_tvhumidity_text_list).get(i).setText(weather.hourlyForecast.get(i).hum);
                //填充item2的风速图片
                (((itemView2) holder).item2_tvwindspeed_image_list).get(i).setImageResource(R.mipmap.icon_windspeed);
                //填充item2的风速字符串
                (((itemView2) holder).item2_tvwindspeed_text_list).get(i).setText(weather.hourlyForecast.get(i).wind.spd);
            }
        } else if (holder instanceof itemView3) {
            //填充item3的衣服图片
            (((itemView3) holder).item3_cloth_image).setImageResource(R.mipmap.icon_cloth);
            //填充item3的穿衣指数字符串
            (((itemView3) holder).item3_cloth_brief).setText(weather.suggestion.drsg.brf);
            //填充item3的穿衣介绍
            (((itemView3) holder).item3_cloth_txt).setText(weather.suggestion.drsg.txt);

            //填充item3的运动图片
            (((itemView3) holder).item3_sport_image).setImageResource(R.mipmap.icon_sport);
            //填充item3的运动指数字符串
            (((itemView3) holder).item3_sport_brief).setText(weather.suggestion.sport.brf);
            //填充item3的运动介绍
            (((itemView3) holder).item3_sport_txt).setText(weather.suggestion.sport.txt);

            //填充item3的旅游图片
            (((itemView3) holder).item3_travel_image).setImageResource(R.mipmap.icon_travel);
            //填充item3的旅游指数字符串
            (((itemView3) holder).item3_travel_brief).setText(weather.suggestion.trav.brf);
            //填充item3的旅游介绍
            (((itemView3) holder).item3_travel_txt).setText(weather.suggestion.trav.txt);

            //填充item3的感冒图片
            (((itemView3) holder).item3_flu_image).setImageResource(R.mipmap.icon_flu);
            //填充item3的感冒指数字符串
            (((itemView3) holder).item3_flu_brief).setText(weather.suggestion.flu.brf);
            //填充item3的感冒介绍
            (((itemView3) holder).item3_flu_txt).setText(weather.suggestion.flu.txt);
        } else if (holder instanceof itemView4) {
            for (int i = 0; i < weather.dailyForecast.size(); i++) {
                   //填充天气图片
                    switch (weather.dailyForecast.get(i).cond.txtD)
                    {
                        case "未知":
                            (((itemView4) holder).item4_left_image_list).get(i).setImageResource(R.mipmap.icon_none);
                            break;
                        case "晴":
                            (((itemView4) holder).item4_left_image_list).get(i).setImageResource(R.mipmap.type_one_sunny);
                            break;
                        case "阴":
                            (((itemView4) holder).item4_left_image_list).get(i).setImageResource(R.mipmap.type_one_cloudy);
                            break;
                        case "多云":
                            (((itemView4) holder).item4_left_image_list).get(i).setImageResource(R.mipmap.type_one_cloudy);
                            break;
                        case "少云":
                            (((itemView4) holder).item4_left_image_list).get(i).setImageResource(R.mipmap.type_one_cloudy);
                            break;
                        case "晴间多云":
                            (((itemView4) holder).item4_left_image_list).get(i).setImageResource(R.mipmap.type_one_cloudytosunny);
                            break;
                        case "小雨":
                            (((itemView4) holder).item4_left_image_list).get(i).setImageResource(R.mipmap.type_one_light_rain);
                            break;
                        case "中雨":
                            (((itemView4) holder).item4_left_image_list).get(i).setImageResource(R.mipmap.type_one_heavy_rain);
                            break;
                        case "大雨":
                            (((itemView4) holder).item4_left_image_list).get(i).setImageResource(R.mipmap.type_one_heavy_rain);
                            break;
                        case "阵雨":
                            (((itemView4) holder).item4_left_image_list).get(i).setImageResource(R.mipmap.type_one_thunderstorm);
                            break;
                        case "雷阵雨":
                            (((itemView4) holder).item4_left_image_list).get(i).setImageResource(R.mipmap.type_one_thunderstorm);
                            break;
                        case "霾":
                            (((itemView4) holder).item4_left_image_list).get(i).setImageResource(R.mipmap.type_one_fog);
                            break;
                        case "雾":
                            (((itemView4) holder).item4_left_image_list).get(i).setImageResource(R.mipmap.type_one_fog);
                            break;
                    }
                    //填充日期字符串
                    if(i==0)
                    {
                        (((itemView4) holder).item4_date_text_list).get(i).setText("今天");
                    }
                    else
                    if(i==1)
                    {
                        (((itemView4) holder).item4_date_text_list).get(i).setText("明天");
                    }
                    else
                    if(i==2)
                    {
                        (((itemView4) holder).item4_date_text_list).get(i).setText("后天");
                    }
                    else
                    {
                        char[] date=weather.dailyForecast.get(i).date.toCharArray();
                        String DATE=String.valueOf(date[5])+String.valueOf(date[6])+"月"+String.valueOf(date[8])+String.valueOf(date[9])+"日";
                        (((itemView4) holder).item4_date_text_list).get(i).setText(DATE);
                    }
                    //填充最高温度最低温度字符串
                    char[] splitmax = weather.dailyForecast.get(i).tmp.max.toCharArray();
                    char[] splitmin = weather.dailyForecast.get(i).tmp.min.toCharArray();
                    String str1 = String.valueOf(splitmax[0]);
                    String str2 = String.valueOf(splitmax[1]);
                    String str3 = String.valueOf(splitmin[0]);
                    String str4 = String.valueOf(splitmin[1]);
                    (((itemView4) holder).item4_temp_text_list).get(i).setText(str3 + str4 + "°~" + str1 + str2 + "°");
                    //填充详细建议字符串(包括风力，风向，风速，降水概率)
                    (((itemView4) holder).item4_advice_text_list).get(i).setText("天气:"+weather.dailyForecast.get(i).cond.txtD+"/最高温度:"+str1+str2+"°C"+"/风力:"+weather.dailyForecast.get(i).wind.sc +"/风向:"+weather.dailyForecast.get(i).wind.dir +"/风速:"+weather.dailyForecast.get(i).wind.spd +"/降水概率:"+weather.dailyForecast.get(i).pop+"%");
            }
        }
    }
}
