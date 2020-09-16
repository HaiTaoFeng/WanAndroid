package com.fenght.wanandroid.contract;

import com.fenght.wanandroid.base.IBasePresenter;
import com.fenght.wanandroid.base.IBaseView;

import okhttp3.Call;

public interface ProjectArticleContract {
    interface IProjectArticleModel {
        Call getData(int page,int cid);
    }

    interface IProjectArticleView extends IBaseView {

    }

    interface IProjectArticlePresenter extends IBasePresenter {
        void getData(int page,int cid); //获取数据
    }
}
