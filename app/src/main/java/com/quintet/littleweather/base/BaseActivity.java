package com.quintet.littleweather.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.quintet.littleweather.config.Setting;
import com.quintet.littleweather.utils.ACache;

import com.readystatesoftware.systembartint.SystemBarTintManager;

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

    //设置沉浸式状态栏的方法
    public void setstatusbar()
    {
       //在BaseActivity.java里：我们通过判断当前sdk_int大于4.4(kitkat),则通过代码的形式设置status bar为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置状态栏颜色
     * 也就是所谓沉浸式状态栏
     */
    public void setStatusBarColor(int color) {
        /**
         * Android4.4以上  但是抽屉有点冲突，目前就重写一个方法暂时解决4.4的问题
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);
        }
    }

    public void setStatusBarColorForKitkat(int color) {
        /**
         * Android4.4
         */
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);
        }
    }
}
