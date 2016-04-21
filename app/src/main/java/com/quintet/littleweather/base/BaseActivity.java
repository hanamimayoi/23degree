package com.quintet.littleweather.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.quintet.littleweather.config.Setting;
import com.quintet.littleweather.utils.ACache;


/**
 * Created by Administrator on 16-4-20.
 */
public class BaseActivity extends AppCompatActivity {

    public Setting mSetting;

    public ACache mACache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化setting单例
        mSetting = Setting.getInstance();

        //初始化cache工具
        mACache = ACache.get(BaseActivity.this);
    }
}
