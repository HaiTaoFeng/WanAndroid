package com.fenght.wanandroid.contract;

import com.fenght.wanandroid.base.IBasePresenter;
import com.fenght.wanandroid.base.IBaseView;

/**
 * @author fht
 * 契约接口，可以很直观的看到 M、V、P 层接口中提供的方法
 */
public interface MainContract {
    interface IMainModel{
        void requestData();
    }

    interface IMainView extends IBaseView {
        void showDialog();
        void succesMsg();
    }

    interface IMainPresenter extends IBasePresenter {
        void handleData();
    }
}
