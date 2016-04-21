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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            /**
             * 状态栏透明，其实在5.0以上是设置为半透明，在4.4以上是全透明，这样布局就会延伸到状态栏下层，被状态栏覆盖
             * 通过设置fitsSystemWindow="true"可以恢复布局
             * 但是4.4无法设置状态栏的颜色，而5.0可以设置状态栏的颜色
             */

            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void setStatusBarColor(int color) {
        /**
         * Android4.4以上
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);
        }
    }
}
