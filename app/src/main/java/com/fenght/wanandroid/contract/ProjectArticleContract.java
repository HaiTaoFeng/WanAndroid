package com.fenght.wanandroid.contract;

import com.fenght.wanandroid.base.IBasePresenter;
import com.fenght.wanandroid.base.IBaseView;

import okhttp3.Call;

public interface ProjectArticleContract {
    interface IProjectArticleModel {
        Call getData();
    }

    interface IProjectArticleView extends IBaseView {

    }

    interface IProjectArticlePresenter extends IBasePresenter {
        void getData(); //获取数据
    }
}
