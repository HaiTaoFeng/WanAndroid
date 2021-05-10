package com.fenght.wanandroid.mvp.model;

import com.fenght.wanandroid.base.BaseModel;
import com.fenght.wanandroid.constants.API;
import com.fenght.wanandroid.contract.HomeArticleContract;
import com.fenght.wanandroid.utils.LogUtil;
import com.fenght.wanandroid.utils.OkHttpUtil;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HomeArticleModel extends BaseModel implements HomeArticleContract.IHomeArticleModel {

    private static final String TAG = "HomeArticleModel";

    @Override
    public Call requestData(int page) {
        String url = API.HOME_ARTICLE + page + "/json";
        LogUtil.e("fht",url);
        return OkHttpUtil.getCall(url);
    }


    @Override
    public Call requestBinnerData() {
        String url = API.HOME_BINNER;
        return OkHttpUtil.getCall(url);
    }

}
