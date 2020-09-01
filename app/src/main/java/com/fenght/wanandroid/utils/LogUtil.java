package com.fenght.wanandroid.utils;

import android.util.Log;

public class LogUtil {
    private static String TAG = "fht";

    public static void v(String s){
        Log.v(TAG,s);
    }

    public static void v(String tag,String s){
        Log.v(tag,s);
    }

    public static void d(String s){
        Log.d(TAG,s);
    }

    public void d(String tag,String s){
        Log.d(tag,s);
    }
    public static void e(String s){
        Log.e(TAG,s);
    }

    public static void e(String tag, String s){
        Log.e(tag,s);
    }


    public static void i(String s){
        Log.i(TAG,s);
    }

    public static void i(String tag,String s){
        Log.i(tag,s);
    }
}
