package com.fenght.wanandroid.presenter;

import com.fenght.wanandroid.base.BasePresenter;
import com.fenght.wanandroid.bean.ProjcetArticleBean;
import com.fenght.wanandroid.contract.ProjectArticleContract;
import com.fenght.wanandroid.model.ProjectArticleModel;
import com.fenght.wanandroid.utils.AssetsUtil;
import com.google.gson.Gson;

public class ProjectArticlePresenter extends BasePresenter
        <ProjectArticleContract.IProjectArticleView, ProjectArticleModel> implements ProjectArticleContract.IProjectArticlePresenter{

    @Override
    public void getData() {
        showDialog();
        String a = AssetsUtil.getAssetsFile(getView().getContext(),"a.json");
        Gson gson = new Gson();
        ProjcetArticleBean bean = gson.fromJson(a,ProjcetArticleBean.class);
        pushData(bean,1);
    }
}
