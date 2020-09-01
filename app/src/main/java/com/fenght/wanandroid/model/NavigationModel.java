package com.fenght.wanandroid.model;

import com.fenght.wanandroid.base.BaseModel;
import com.fenght.wanandroid.constants.API;
import com.fenght.wanandroid.contract.NavigationContract;
import com.fenght.wanandroid.utils.LogUtil;
import com.fenght.wanandroid.utils.OkHttpUtil;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NavigationModel extends BaseModel implements NavigationContract.INavigationModel {

    private static final String TAG = "NavigationModel";

    @Override
    public Call getData() {
        String url = API.NAVIGATION_DATA;
        LogUtil.e("fht",url);
        OkHttpClient okHttpClient = OkHttpUtil.okHttpClient;
        final Request request = OkHttpUtil.getRequst(url);
        return okHttpClient.newCall(request);
    }
}
