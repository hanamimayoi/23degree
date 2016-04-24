package com.quintet.littleweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.quintet.littleweather.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SplashActivity extends Activity {

    public static final int MSG_TIME_OUT = 1;
    public static final String DB_NAME = "china_city.db"; //数据库的名字
    public static final String PACKAGE_NAME = "com.quintet.littleweather"; //包名
    public static final String DB_PATH = "data/data/" + PACKAGE_NAME + "/" + DB_NAME;//db存放的路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //在Splash界面等待三秒进入MainActivity,并将放在assets文件夹里面的db数据库文件，copy到data/data/包名目录下
        waitWhile();

    }

    private void waitWhile() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //copy db to data/data/com.quintet.littleweather
                copyDBToData();

                myHandler.sendEmptyMessageDelayed(MSG_TIME_OUT, 1000);

            }
        }).start();
    }

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case MSG_TIME_OUT:
                enterMainActivity();
            }
        }
    };


    private void copyDBToData() {
        File db = new File(DB_PATH);
        if (db.exists()) {
            return;
        } else {

            try {
                AssetManager assets = getAssets();

                InputStream is = assets.open(DB_NAME);

                FileOutputStream fos = new FileOutputStream(db);

                byte[] buf = new byte[1024];
                int len = 0;

                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }

                is.close();
                fos.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    //等待MainActivity 写好后，进行耦合。。。
    private void enterMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
        //SplashActivity跳转MainActivity的动画；
        //overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
