package com.fenght.wanandroid.presenter;

import com.fenght.wanandroid.base.BasePresenter;
import com.fenght.wanandroid.bean.ProjcetArticleBean;
import com.fenght.wanandroid.bean.ProjectSortBean;
import com.fenght.wanandroid.contract.SecondContract;
import com.fenght.wanandroid.model.SecondModel;
import com.fenght.wanandroid.utils.AssetsUtil;
import com.google.gson.Gson;

public class SecondPresenter extends BasePresenter<SecondContract.ISecondView, SecondModel> implements SecondContract.ISecondPresenter{

    @Override
    public void handleData() {
        showDialog();
        String a = AssetsUtil.getAssetsFile(getView().getContext(),"project_sort.json");
        Gson gson = new Gson();
        ProjectSortBean bean = gson.fromJson(a,ProjectSortBean.class);
        pushData(bean,1);
    }

}
