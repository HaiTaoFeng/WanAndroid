package com.fenght.wanandroid.contract;

import com.fenght.wanandroid.base.IBasePresenter;
import com.fenght.wanandroid.base.IBaseView;

public interface SecondContract {
    interface ISecondModel {
        String requestData();
    }

    interface ISecondView extends IBaseView {
        void showDailog();
        void showMsg(String msg);
    }

    interface ISecondPresenter extends IBasePresenter {
        void handleData();
    }
}
