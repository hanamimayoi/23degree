package com.quintet.littleweather.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;

/**
 * Created by hanami on 2016/4/21.
 */
public class Utils {

    public static Boolean isNetWorkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isAvailable()) {
            return true;
        }

        return false;
    }

    public static long getSize(File file) {

        long size = 0;

        if (file.isDirectory()) {
            size = getDirSize(file);
        } else {
            size = getFileSize(file);
        }

        return size;
    }

    private static long getDirSize(File file) {
        long size = 0;

        File[] files = file.listFiles();

        for (File f : files) {
            if (f.isDirectory()) {
                size += getDirSize(f);
            } else {
                size += getFileSize(f);
            }
        }

        return size;
    }

    private static long getFileSize(File file) {
        return file.length();
    }

}
