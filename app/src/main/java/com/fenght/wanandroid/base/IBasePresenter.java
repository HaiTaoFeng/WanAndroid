package com.fenght.wanandroid.base;

public interface IBasePresenter<V extends IBaseView> {
    void attach(V v);
    void detach();
}
