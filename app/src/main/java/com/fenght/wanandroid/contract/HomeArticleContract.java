package com.fenght.wanandroid.contract;

import com.fenght.wanandroid.base.IBasePresenter;
import com.fenght.wanandroid.base.IBaseView;
import com.fenght.wanandroid.bean.HomeArticleBean;

import okhttp3.Call;

public interface HomeArticleContract {
    interface IHomeArticleModel {
        Call requestData(int page);
        Call requestBinnerData();
    }

    interface IHomeArticleView extends IBaseView {
        <T> void Succeed(T t);
        void errorMsg(String s);
    }

    interface IHomeArticlePresenter extends IBasePresenter {
        void handleData(int page);
        void handleBinnerData();
    }
}
