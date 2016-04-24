package com.quintet.littleweather.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quintet.littleweather.R;
import com.quintet.littleweather.adapter.RecycleView;
import com.quintet.littleweather.base.BaseActivity;
import com.quintet.littleweather.bean.Favorites;
import com.quintet.littleweather.bean.Weather;
import com.quintet.littleweather.bean.WeatherAPI;
import com.quintet.littleweather.behavior.ScrollAwareFABBehavior;
import com.quintet.littleweather.config.Setting;
import com.quintet.littleweather.config.SpacesItemDecoration;
import com.quintet.littleweather.https.RetrofitSingleton;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, AMapLocationListener {
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private RecycleView adapter;
    private FloatingActionButton fab;
    //判断是否使用过高德定位；
    private boolean isLocation;
    //高德定位客服端；
    private AMapLocationClient mLocationClient = null;
    //高德定位参数设置
    private AMapLocationClientOption mLocationOption = null;

    private Observer<Weather> mObserver;
    private SearchView mSearchView;

    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private CoordinatorLayout mCoord;
    private CollapsingToolbarLayout mCollapsingLayout;

    /**
     * 都是用来完成收藏城市功能的
     */
    private List<Favorites.City> mCities;
    private Favorites.City mCurrentCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置沉浸式状态栏：在此选择变透明的方式
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder().create();
        //如果没有收藏城市的话，默认给一个size为0的List，避免 favorites.cities 这个List为空指针
        String favoJson = mSetting.getString(Setting.FAVORITES, "{\"cities\":[]}");
        Favorites favorites = gson.fromJson(favoJson, Favorites.class);

        mCities = favorites.cities;

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mCoord = (CoordinatorLayout) findViewById(R.id.coord);

        mCollapsingLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        //设置工具栏
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //监听抽屉的打开和关闭
        //ActionBarDrawerToggle实现了DrawerLayout.DrawerListener
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //setDrawerListener()方法已经过时，改用addDrawerListener()
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        //初始化fab，并设置监听事件
        initFab();

        //设置NavigationView的监听事件
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        //找到swiperefresh控件和recyleview控件
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swiprefresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        InitRecycleView();
        //加载SwipeRefreshLayout控件
        InitSwipeRefresh();
        new RefreshHandler().sendEmptyMessage(1);

        fetchData();
    }

    private void initFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        lp.setBehavior(new ScrollAwareFABBehavior(this, null));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String city = mSetting.getString(Setting.CITY_NAME, "上海");
                saveCollectCity(city);
            }
        });
    }

    private void saveCollectCity(String city) {
        //判断城市是否收藏过
        if (isCitySaved(city)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("取消收藏")
                    .setMessage("城市已经收藏过了，要取消收藏吗？")
                    .setPositiveButton("取消收藏", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mCities.remove(mCurrentCity);
                            Snackbar.make(mCoord, "已经取消收藏", Snackbar.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("放弃", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        } else {
            mCities.add(mCurrentCity);
            Snackbar.make(mCoord, "收藏成功", Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断城市是否收藏过
     *
     * @param city
     * @return
     */
    private boolean isCitySaved(String city) {
        for (Favorites.City c : mCities) {

            //如果已经收藏过就返回true，并且记录当前城市实例为List中的那个收藏过的城市实例，方便取消收藏的时候直接remove
            if (city.equals(c.name)) {
                mCurrentCity = c;
                return true;
            }
        }

        //如果没收藏过，就返回false，并且记录当前城市实例等于这个将要收藏的城市实例，方便添加实例
        mCurrentCity = new Favorites().new City(city);

        return false;
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
                fetchDataByNetwork(mSetting.getString(Setting.CITY_NAME, "上海"));
            }
        });
    }

    //设置RecycleView列表,瀑布流!
    public void InitRecycleView() {
        //设置layoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);
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

        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setQueryHint("请输入您想查询的城市");

        //刚进去就是SearchView获得焦点的状态（软键盘弹出），关闭SearchView之后又会缩小成放大镜图标
        //searchView.setIconified(false);

        //Toolbar上不是单个放大镜图标，而是一直是SearchView展开的状态，但是不会抢占焦点，需要手动点击才有焦点，并且不会缩小成放大镜图标
        //searchView.setIconifiedByDefault(false);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.setIconified(true);//第一次调用清除输入框的文字，失去焦点
                mSearchView.setIconified(true);//第二次调用搜索框缩小为放大镜

                query = query.replace("市", "")
                        .replace("县", "")
                        .replace("省", "")
                        .replace("自治区", "")
                        .replace("特别行政区", "")
                        .replace("地区", "")
                        .replace("盟", "")
                        .replace("藏族自治州", "")
                        .replace("藏族羌族自治州", "")
                        .replace("彝族自治州", "");

                new RefreshHandler().sendEmptyMessage(1);
                fetchDataByNetwork(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

        });

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

            if (mCities == null || mCities.size() == 0) {
                Snackbar.make(mCoord, "还没有收藏的城市", Snackbar.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.ThemeOverlay_Material_Dialog);

                /**
                 * 设置Dialog进去的时候的默认选项，应该与界面上的城市保持一致
                 * 如果界面上的城市不在收藏列表中，就默认选中第一个
                 */
                int checkedItem = 0;

                final String[] items = new String[mCities.size()];
                for (int i = 0; i < mCities.size(); i++) {
                    items[i] = mCities.get(i).name;
                    if (mCities.get(i).name.equals(mSetting.getString(Setting.CITY_NAME, "上海"))) {
                        checkedItem = i;
                    }
                }

                builder.setTitle("选择收藏的城市")
                        .setSingleChoiceItems(items, checkedItem,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new RefreshHandler().sendEmptyMessage(1);
                                        fetchDataByNetwork(items[which]);
                                        dialog.dismiss();
                                    }
                                }).show();
            }
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

            //进入选择城市activity
            startActivityForResult(new Intent(MainActivity.this, SelectCityActivity.class), 1);

        } else if (id == R.id.nav_set) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fetchData() {

        mObserver = new Observer<Weather>() {

            /**
             * onCompleted和onError总有一个会被调用的，即使在一次订阅过程中
             * 发送的事件序列为空，onCompleted方法也还是会被调用的
             * onError在发生异常导致事件中断的时候触发
             */
            @Override
            public void onCompleted() {
                //即使因为城市名错误，事件序列被过滤掉了，onCompleted方法还是会被调用的
                Log.d("MainActivity", "onCompleted is called");
                new RefreshHandler().sendEmptyMessage(2);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("MainActivity", "onError is called");
                RetrofitSingleton.disposeFailure(e, MainActivity.this, mCoord);
                //发生错误的时候也发送2，告诉SwipeRefresh组件停止刷新状态
                new RefreshHandler().sendEmptyMessage(2);
            }

            @Override
            public void onNext(Weather weather) {

                Log.d("MainActivity", "onNext is called");

                new RefreshHandler().sendEmptyMessage(2);

                /**
                 * OnNext()被调用就说明确实拿到了符合要求的数据
                 * 在这里的城市名是肯定没有错误的，那么在这里把城市名保存起来是肯定不会有风险的
                 */
                mCollapsingLayout.setTitle(weather.basic.city);
                mSetting.putString(Setting.CITY_NAME, weather.basic.city);

                //设置adapter
                adapter = new RecycleView(weather, MainActivity.this);
                mRecyclerView.setAdapter(adapter);

            }
        };

        fetchDataByCache();

    }

    public void fetchDataByCache() {
        Log.d("MainActivity", "fetchDataByCache is called");

        String weatherJson = mACache.getAsString("weather");

        //weatherJson不是null或者空串，有缓存就从缓存里面读取JSON
        if (!TextUtils.isEmpty(weatherJson)) {
            Gson gson = new GsonBuilder().create();
            Weather weather = gson.fromJson(weatherJson, Weather.class);
            Observable.just(weather).subscribe(mObserver);
        } else {
            //没有缓存就根据SharedPreference保存的城市名去请求数据，默认是上海
            fetchDataByNetwork(mSetting.getString(Setting.CITY_NAME, "上海"));
        }
    }

    public void fetchDataByNetwork(String cityName) {

        //请求数据，发送事件，在订阅的时候触发网络请求，获取数据，然后发送事件，感觉跟defer()操作符类似
        RetrofitSingleton.getApiService()
                .getWeatherAPI(cityName, Setting.KEY)
                .subscribeOn(Schedulers.io())
                .filter(new Func1<WeatherAPI, Boolean>() {
                    @Override
                    public Boolean call(WeatherAPI weatherAPI) {
                        Log.d("MainActivity", "filter is called");
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
                        //获取设置的缓存时间，默认是不缓存
                        int time = Integer.parseInt(mSetting.getString("cache_time", "0")) * Setting.ONE_HOUR;
                        //缓存Json数据
                        mACache.put("weather", weatherJson, time);

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    //更新Refresh组件的更新状态，因为是更新UI，所以需要一个Handler来处理
    class RefreshHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //开始刷新
                case 1:
                    mSwipeRefreshWidget.setRefreshing(true);
                    break;
                //怎么判断我这一次是成功拿到数据，还是出错了呢？
                case 2:
                    //如果显示正在刷新，就取消刷新状态
                    if (mSwipeRefreshWidget.isRefreshing()) {
                        mSwipeRefreshWidget.setRefreshing(false);
                        Snackbar.make(mCoord, "加载完毕", Snackbar.LENGTH_SHORT).show();
                    }


                    break;
            }
        }
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

        /**
         * 将收藏的城市重新保存起来
         */
        Favorites favorites = new Favorites();
        favorites.cities = mCities;
        Gson gson = new GsonBuilder().create();
        String favoJson = gson.toJson(favorites);
        mSetting.putString(Setting.FAVORITES, favoJson);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 2) {
            String city = data.getStringExtra(Setting.CITY_NAME)
                    .replace("市", "")
                    .replace("县", "")
                    .replace("省", "")
                    .replace("自治区", "")
                    .replace("特别行政区", "")
                    .replace("地区", "")
                    .replace("盟", "")
                    .replace("藏族自治州", "")
                    .replace("藏族羌族自治州", "")
                    .replace("彝族自治州", "");
            new RefreshHandler().sendEmptyMessage(1);
            fetchDataByNetwork(city);
        }
    }


}
