package com.fenght.wanandroid.contract;

import com.fenght.wanandroid.base.IBasePresenter;
import com.fenght.wanandroid.base.IBaseView;

import okhttp3.Call;

public interface NavigationContract {
    interface INavigationModel {
        Call getData();
    }

    interface INavigationView extends IBaseView {

    }

    interface INavigationPresenter extends IBasePresenter {
        void getData(); //获取数据
    }
}
