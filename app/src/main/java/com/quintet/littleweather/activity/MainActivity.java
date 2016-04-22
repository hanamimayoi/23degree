package com.quintet.littleweather.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quintet.littleweather.R;
import com.quintet.littleweather.adapter.RecycleView;
import com.quintet.littleweather.base.BaseActivity;
import com.quintet.littleweather.bean.Weather;
import com.quintet.littleweather.bean.WeatherAPI;
import com.quintet.littleweather.bean.item;
import com.quintet.littleweather.config.Setting;
import com.quintet.littleweather.config.SpacesItemDecoration;
import com.quintet.littleweather.https.RetrofitSingleton;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, AMapLocationListener {
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private RecycleView adapter;
    //判断是否使用过高德定位；
    private boolean isLocation;
    //高德定位客服端；
    private AMapLocationClient mLocationClient = null;
    //高德定位参数设置
    private AMapLocationClientOption mLocationOption = null;
    private List<item> list1;
    private List<item> list2;
    private List<item> list3;
    private List<item> list4;

    //当前的城市名字
    private Subscriber<Weather> mSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置沉浸式状态栏：在此选择变透明的方式
        setContentView(R.layout.activity_weatherapplication);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //设置工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //监听抽屉的打开和关闭
        //ActionBarDrawerToggle实现了DrawerLayout.DrawerListener
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //setDrawerListener()方法已经过时，改用addDrawerListener()
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //设置NavigationView的监听事件
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //找到swiperefresh控件和recyleview控件
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swiprefresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        list1 = new ArrayList<item>();
        list2 = new ArrayList<item>();
        list3 = new ArrayList<item>();
        list4 = new ArrayList<item>();
        //加载RecyelerView控件
        InitRecycleView();
        //加载SwipeRefreshLayout控件
        InitSwipeRefresh();

        fetchData();


    }

    //设置下拉刷新
    public void InitSwipeRefresh() {
        //设置卷内的颜色
        mSwipeRefreshWidget.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //设置下拉刷新监听
        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        mSwipeRefreshWidget.setRefreshing(false);
                    }
                }, 6000);
            }
        });
    }

    //设置RecycleView列表,瀑布流!
    public void InitRecycleView() {
        //设置layoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置adapter
        initData();
        adapter = new RecycleView(list1, list2, list3, list4,this);
        mRecyclerView.setAdapter(adapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);
    }

    //在此处将想要填充的数据填入bean中，并且将填充好的bean装入list中，以便给RecycleView.adapter用。
    public void initData() {
        for (int i = 0; i < 4; i++) {
            list1.add(new item());
            list2.add(new item());
            list3.add(new item());
            list4.add(new item());
        }
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.weatherapplication, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_city) {

            // Handle the camera action
        } else if (id == R.id.nav_set) {

        } else if (id == R.id.nav_about) {

        }

      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }

    public void refresh() {
        fetchDataByNetwork();
    }


    public void fetchData() {

        mSubscriber = new Subscriber<Weather>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Weather weather) {
                Log.d("MainActivity", "weatherJson------------------" + new Gson().toJson(weather));
            }
        };

        fetchDataByCache();

    }

    public void fetchDataByCache() {
        Log.d("MainActivity", "fetchDataByCache is called");

        String weatherJson = mACache.getAsString("weather");

        //weatherJson不是null或者空串
        if (!TextUtils.isEmpty(weatherJson)) {
            Gson gson = new GsonBuilder().create();
            Weather weather = gson.fromJson(weatherJson, Weather.class);
            Observable.just(weather).subscribe(mSubscriber);
        } else {
            fetchDataByNetwork();
        }
    }

    public void fetchDataByNetwork() {
        Log.d("MainActivity", "fetchDataByNetwork is called");

        RetrofitSingleton.getApiService()
                .getWeatherAPI("上海", Setting.KEY)
                .subscribeOn(Schedulers.io())
                .filter(new Func1<WeatherAPI, Boolean>() {
                    @Override
                    public Boolean call(WeatherAPI weatherAPI) {
                        return weatherAPI.heWeatherDataService.get(0).status.equals("ok");
                    }
                })
                .map(new Func1<WeatherAPI, Weather>() {
                    @Override
                    public Weather call(WeatherAPI weatherAPI) {
                        return weatherAPI.heWeatherDataService.get(0);
                    }
                })
                .doOnNext(new Action1<Weather>() {
                    @Override
                    public void call(Weather weather) {

                        Gson gson = new GsonBuilder().create();

                        String weatherJson = gson.toJson(weather);

                        Log.d("MainActivity", "weatherJson------------------" + weatherJson);

                        //设置Json数据在缓存中的保存时间
                        mACache.put("weather", weatherJson,
                                mSetting.getInt(Setting.AUTO_UPDATE, 1) * Setting.ONE_HOUR);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mSubscriber);


    }

    /**
     * 使用高德定位功能
     */
    public void location() {
        //高德定位客户端
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //注册定位回调监听
        mLocationClient.setLocationListener(this);
        //设置定位参数；
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //只在进入程序的时候定位1次
        mLocationOption.setInterval(-1);
        //给定位客户端设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动高德定位
        mLocationClient.startLocation();
    }

    /**
     * @param aMapLocation 实现高德定位监听回调AMapLocationListener接口需要重写的方法；
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {//定位成功回调信息，设置相关消息
                //获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLocationType();
                //将定位的“城市名称”保存到Setting中
                mSetting.putString(Setting.CITY_NAME, aMapLocation.getCity());
                //表明已经启动过定位；
                isLocation = true;
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                // 将错误信息写到相应日志文档中
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            //销毁该界面时，停止高德定位功能
            mLocationClient.stopLocation();
            //并销毁高德定位客户端
            mLocationClient.onDestroy();
        }
    }
}
