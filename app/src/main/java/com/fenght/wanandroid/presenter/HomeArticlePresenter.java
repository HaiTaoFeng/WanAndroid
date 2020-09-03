package com.fenght.wanandroid.presenter;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;

import com.fenght.wanandroid.base.BasePresenter;
import com.fenght.wanandroid.bean.BinnerBean;
import com.fenght.wanandroid.bean.HomeArticleBean;
import com.fenght.wanandroid.contract.HomeArticleContract;
import com.fenght.wanandroid.model.HomeArticleModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeArticlePresenter extends BasePresenter<HomeArticleContract.IHomeArticleView, HomeArticleModel> implements HomeArticleContract.IHomeArticlePresenter{

    private static final String TAG ="HomeArticlePresenter" ;

    @Override
    public void handleData(int page) {
        showDialog();
        Call call = getModel().requestData(page);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pushData(e.getMessage(),0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                HomeArticleBean bean = gson.fromJson(s,HomeArticleBean.class);
                pushData(bean,1);
            }
        });
    }

    @Override
    public void handleBinnerData() {
        showDialog();
        Call call = getModel().requestBinnerData();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pushData(e.getMessage(),0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                BinnerBean bean = gson.fromJson(s,BinnerBean.class);
                pushData(bean,1);
            }
        });
    }

    //获取Binner适配器
    public List<BinnerBean.DataBean> getBinnerList(){
        String s = getJson(getView().getContext(),"b.json");
        BinnerBean binnerBean = new Gson().fromJson(s,BinnerBean.class);
        return binnerBean.getData();
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
