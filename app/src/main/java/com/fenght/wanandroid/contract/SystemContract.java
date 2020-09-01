package com.fenght.wanandroid.contract;

import com.fenght.wanandroid.base.IBasePresenter;
import com.fenght.wanandroid.base.IBaseView;
import com.fenght.wanandroid.bean.SystemLableBean;

import okhttp3.Call;

public interface SystemContract {
    interface ISystemModel {
        Call getLableData();
        Call getArticleData(int page,int cid);
    }

    interface ISystemView extends IBaseView {
        void lableSucceed(SystemLableBean bean);
        <T> void articleSucceed(T t);
        void errorMsg(String s);
    }

    interface ISystemPresenter extends IBasePresenter {
        void getLableData(); //获取标签数据
        void getArticleData(int page,int cid); //获取文章数据
    }
}
