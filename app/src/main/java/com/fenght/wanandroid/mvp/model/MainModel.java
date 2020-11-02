package com.fenght.wanandroid.mvp.model;

import com.fenght.wanandroid.base.BaseModel;
import com.fenght.wanandroid.contract.MainContract;

public class MainModel extends BaseModel implements MainContract.IMainModel {


    @Override
    public void requestData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
