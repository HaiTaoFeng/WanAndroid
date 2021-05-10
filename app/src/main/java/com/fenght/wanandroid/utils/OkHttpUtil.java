package com.fenght.wanandroid.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtil {

    private final static int DEFAULT_TIMEOUT = 20;
    public final static  OkHttpClient okHttpClient  = new OkHttpClient.Builder().
            connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(new LogInterceptor()) //添加日志拦截器
            .build();

    private static Request getRequst(String url){
        return new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();

    }

    public static Call getCall(String url){
        return okHttpClient.newCall(getRequst(url));
    }
}
