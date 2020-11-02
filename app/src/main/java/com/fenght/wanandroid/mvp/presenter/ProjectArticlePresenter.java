package com.fenght.wanandroid.mvp.presenter;

import com.fenght.wanandroid.base.BasePresenter;
import com.fenght.wanandroid.bean.ProjcetArticleBean;
import com.fenght.wanandroid.contract.ProjectArticleContract;
import com.fenght.wanandroid.mvp.model.ProjectArticleModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProjectArticlePresenter extends BasePresenter<ProjectArticleContract.IProjectArticleView,
        ProjectArticleModel> implements ProjectArticleContract.IProjectArticlePresenter{

    @Override
    public void getData(int page,int cid) {
        showDialog();
//        String a = AssetsUtil.getAssetsFile(getView().getContext(),"a.json");
//        Gson gson = new Gson();
//        ProjcetArticleBean bean = gson.fromJson(a,ProjcetArticleBean.class);
//        pushData(bean,1);
        Call call = getModel().getData(page, cid);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                pushData(e.getMessage(),0);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                ProjcetArticleBean bean = gson.fromJson(s,ProjcetArticleBean.class);
                pushData(bean,1);
            }
        });
    }
}
