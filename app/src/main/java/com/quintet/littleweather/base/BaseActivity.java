package com.quintet.littleweather.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.quintet.littleweather.config.Setting;

/**
 * Created by hanami on 2016/4/19.
 */
public class BaseActivity extends AppCompatActivity {

    public Setting mSetting;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        mSetting = Setting.getInstance();
    }
}
