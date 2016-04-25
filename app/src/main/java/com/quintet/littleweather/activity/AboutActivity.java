package com.quintet.littleweather.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.quintet.littleweather.R;
import com.quintet.littleweather.base.BaseActivity;
import com.quintet.littleweather.fragment.AboutFragment;

public class AboutActivity extends BaseActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setStatusBarColor(R.color.colorPrimary);
        getFragmentManager().beginTransaction().add(R.id.frame_layout, new AboutFragment()).commit();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
