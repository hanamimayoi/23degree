package com.quintet.littleweather.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.quintet.littleweather.R;
import com.quintet.littleweather.base.BaseActivity;
import com.quintet.littleweather.fragment.SettingFragment;

public class SettingActivity extends BaseActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setStatusBarColor(R.color.colorPrimary);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        getFragmentManager().beginTransaction().add(R.id.frame_layout, new SettingFragment()).commit();
    }
}
