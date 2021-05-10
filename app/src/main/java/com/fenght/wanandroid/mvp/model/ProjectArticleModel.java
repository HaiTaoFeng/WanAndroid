package com.fenght.wanandroid.mvp.model;

import com.fenght.wanandroid.base.BaseModel;
import com.fenght.wanandroid.constants.API;
import com.fenght.wanandroid.contract.ProjectArticleContract;
import com.fenght.wanandroid.utils.LogUtil;
import com.fenght.wanandroid.utils.OkHttpUtil;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ProjectArticleModel extends BaseModel implements ProjectArticleContract.IProjectArticleModel {

    private static final String TAG = "ProjectArticleModel";

    @Override
    public Call getData(int page, int cid) {
        String url = API.PROJECT_LIST + page + "/json?cid=" + cid;
        LogUtil.e("fht",url);
        return OkHttpUtil.getCall(url);
    }
}
