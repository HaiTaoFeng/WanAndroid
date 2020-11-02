package com.fenght.wanandroid.mvp.model;

import com.fenght.wanandroid.base.BaseModel;
import com.fenght.wanandroid.constants.API;
import com.fenght.wanandroid.contract.SystemContract;
import com.fenght.wanandroid.utils.LogUtil;
import com.fenght.wanandroid.utils.OkHttpUtil;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SystemModel extends BaseModel implements SystemContract.ISystemModel {

    private static final String TAG = "HomeArticleModel";

    @Override
    public Call getLableData() {
        String url = API.SYSTEM_LABEL;
        LogUtil.e("fht",url);
        OkHttpClient okHttpClient = OkHttpUtil.okHttpClient;
        final Request request = OkHttpUtil.getRequst(url);
        return okHttpClient.newCall(request);
    }

    @Override
    public Call getArticleData(int page,int cid) {
        String url = API.SYSTEM_LABEL_ARTICLE + page + "/json?cid=" + cid;
        LogUtil.e("fht",url);
        return OkHttpUtil.getCall(url);
    }

    @Override
    public Call getArticleByAuthor(int page,String author) {
        String url = API.SYSTEM_SEARCH_ARTICLE + page + "/json?author=" + author;
        LogUtil.e("fht",url);
        return OkHttpUtil.getCall(url);
    }

}
