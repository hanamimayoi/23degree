package com.quintet.littleweather.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.quintet.littleweather.R;
import com.quintet.littleweather.adapter.SelectCityAdapter;
import com.quintet.littleweather.base.BaseActivity;
import com.quintet.littleweather.bean.City;
import com.quintet.littleweather.bean.Province;
import com.quintet.littleweather.config.Setting;
import com.quintet.littleweather.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class SelectCityActivity extends BaseActivity {


    private RecyclerView mRecyclerView;
    private SelectCityAdapter mSelectCityAdapter;
    ArrayList<String> dataList = new ArrayList<>();
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    public static final int LEVEL_PROVINCE = 1;
    public static final int LEVEL_CITY = 2;
    private int currentLevel;
    private Province selectedProvince;

    private List<Province> provincesList;
    private List<City> cityList;

    private City selectedCity;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initView();
        initRecyclerView();

        queryProvinces();


    }

    private void initView() {
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        //设置沉浸栏
        setStatusBarColor(R.color.choiceCity);
    }

    private void initRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mSelectCityAdapter = new SelectCityAdapter(this, dataList);

        mRecyclerView.setAdapter(mSelectCityAdapter);

        mSelectCityAdapter.setOnItemClickListener(new SelectCityAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provincesList.get(pos);
                    mRecyclerView.scrollToPosition(0);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(pos);

                    Intent intent = new Intent();
                    String cityName = selectedCity.CityName;
                    intent.putExtra(Setting.CITY_NAME, cityName);
                    setResult(2, intent);
                    finish();
                }
            }
        });

    }


    private void queryProvinces() {
        mCollapsingToolbarLayout.setTitle("选择省份");

        Observer<Province> observer = new Observer<Province>() {
            @Override
            public void onCompleted() {
                currentLevel = LEVEL_PROVINCE;
                //mCityAdapter.notifyDataSetChanged();
                mSelectCityAdapter.notifyDataSetChanged();

                //    mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Province province) {
                dataList.add(province.ProName);
            }
        };

        Observable
                .defer(new Func0<Observable<Province>>() {
                    @Override
                    public Observable<Province> call() {
                        // provincesList = mWeatherDB.loadProvinces(mDBManager.getDatabase());
                        db = SQLiteDatabase.openOrCreateDatabase("data/data/com.quintet.littleweather/china_city.db", null);
                        provincesList = DBUtils.getAllProvince(db);
                        dataList.clear();
                        return Observable.from(provincesList);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }


    private void queryCities() {
        dataList.clear();
        mCollapsingToolbarLayout.setTitle(selectedProvince.ProName);
        Observer<City> observer = new Observer<City>() {
            @Override
            public void onCompleted() {
                currentLevel = LEVEL_CITY;

                //mCityAdapter.notifyDataSetChanged();
                mSelectCityAdapter.notifyDataSetChanged();

                //定位到第一个item
                mRecyclerView.smoothScrollToPosition(0);
            }


            @Override
            public void onError(Throwable e) {

            }


            @Override
            public void onNext(City city) {
                dataList.add(city.CityName);
            }
        };


        Observable.defer(new Func0<Observable<City>>() {
            @Override
            public Observable<City> call() {
                // cityList = mWeatherDB.loadCities(mDBManager.getDatabase(), selectedProvince.ProSort);

                db = SQLiteDatabase.openOrCreateDatabase("data/data/com.quintet.littleweather/china_city.db", null);
                cityList = DBUtils.getAllCity(db, selectedProvince.ProSort);
                return Observable.from(cityList);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentLevel == LEVEL_PROVINCE) {
                finish();
            } else {
                queryProvinces();
                // mRecyclerView.smoothScrollToPosition(0);
            }
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
