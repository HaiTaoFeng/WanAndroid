package com.fenght.wanandroid.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.fenght.wanandroid.base.BasePresenter;
import com.fenght.wanandroid.bean.HomeArticleBean;
import com.fenght.wanandroid.bean.SystemLableBean;
import com.fenght.wanandroid.contract.SystemContract;
import com.fenght.wanandroid.model.SystemModel;
import com.fenght.wanandroid.utils.LogUtil;
import com.google.gson.Gson;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SystemPresenter extends BasePresenter<SystemContract.ISystemView, SystemModel> implements SystemContract.ISystemPresenter{


    private static final String TAG ="SystemPresenter" ;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            dissDialog();
            switch (msg.what){
                case 0: //接口调用失败
                    String s = (String)msg.obj;
                    getView().errorMsg(s);
                    break;
                case 1: //体系标签
                    Gson gson = new Gson();
                    SystemLableBean lableBean = gson.fromJson((String)msg.obj,SystemLableBean.class);
                    getView().lableSucceed(lableBean);
                    break;
                case 2: //体系标签下的文章
                    Gson gson1 = new Gson();
                    HomeArticleBean bean = gson1.fromJson((String)msg.obj,HomeArticleBean.class);
                    getView().articleSucceed(bean);
                    break;
            }
            return false;
        }
    });

    @Override
    public void getLableData() {
        showDialog();
        Call call = getModel().getLableData();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.obj = e.getMessage();
                msg.what = 0;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Message msg = new Message();
                msg.obj = s;
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }

    @Override
    public void getArticleData(int page,int cid) {
        showDialog();
        Call call = getModel().getArticleData(page,cid);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String s = e.getMessage();
                Message msg = new Message();
                msg.obj = s;
                msg.what = 2;
                handler.sendMessage(msg);
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Message msg = new Message();
                msg.obj = s;
                msg.what = 2;
                handler.sendMessage(msg);
            }
        });
    }
}
