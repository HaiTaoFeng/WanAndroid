package com.fenght.wanandroid.contract;

import com.fenght.wanandroid.base.IBasePresenter;
import com.fenght.wanandroid.base.IBaseView;
import com.fenght.wanandroid.bean.SystemLableBean;

import okhttp3.Call;

public interface SystemContract {
    interface ISystemModel {
        Call getLableData();
        Call getArticleData(int page,int cid);
        Call getArticleByAuthor(int page, String author);
    }

    interface ISystemView extends IBaseView {

    }

    interface ISystemPresenter extends IBasePresenter {
        void getLableData(); //获取标签数据
        void getArticleData(int page,int cid); //获取文章数据
        void getArticleByAuthor(int page,String author); //根据作者获取文章数据
    }
}
