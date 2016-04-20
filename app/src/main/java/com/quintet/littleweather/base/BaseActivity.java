package com.quintet.littleweather.base;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by Administrator on 16-4-20.
 */
public class BaseActivity extends AppCompatActivity
{
    //设置沉浸式状态栏的方法
    public void setstatusbar()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
