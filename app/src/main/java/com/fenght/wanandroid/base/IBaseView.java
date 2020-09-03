package com.fenght.wanandroid.base;

import android.content.Context;

public interface IBaseView {
    Context getContext();

    void showDialog(String s);
    void dissDialog();

    //成功时调用
    <T> void succeed(T t);
    //失败时调用
    void error(String s);
}
