package com.fenght.wanandroid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.github.moduth.blockcanary.BlockCanary;

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        BlockCanary.install(this,new BlockContext()).start();
    }

    public static Context getContext() {
        return context;
    }
}
