package com.fenght.wanandroid.presenter;

import android.content.Context;
import android.content.res.AssetManager;

import com.fenght.wanandroid.base.BasePresenter;
import com.fenght.wanandroid.contract.SecondContract;
import com.fenght.wanandroid.model.SecondModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SecondPresenter extends BasePresenter<SecondContract.ISecondView, SecondModel> implements SecondContract.ISecondPresenter{

    @Override
    public void handleData() {
        getView().showDailog();
        String a = getModel().requestData();
        a = getJson(getView().getContext(),"a.json");
        getView().showMsg(a);
    }

    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
