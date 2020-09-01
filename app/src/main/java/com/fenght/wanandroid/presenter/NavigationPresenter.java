package com.fenght.wanandroid.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.fenght.wanandroid.base.BasePresenter;
import com.fenght.wanandroid.bean.HomeArticleBean;
import com.fenght.wanandroid.bean.NavigationBean;
import com.fenght.wanandroid.bean.SystemLableBean;
import com.fenght.wanandroid.contract.NavigationContract;
import com.fenght.wanandroid.contract.SystemContract;
import com.fenght.wanandroid.model.NavigationModel;
import com.fenght.wanandroid.model.SystemModel;
import com.fenght.wanandroid.utils.LogUtil;
import com.google.gson.Gson;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NavigationPresenter extends BasePresenter<NavigationContract.INavigationView, NavigationModel> implements NavigationContract.INavigationPresenter{


    private static final String TAG ="NavigationPresenter" ;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            dissDialog();
            switch (msg.what){
                case 0: //接口调用失败
                    String s = (String)msg.obj;
                    getView().errorMsg(s);
                    break;
                case 1: //导航
                    Gson gson = new Gson();
                    NavigationBean lableBean = gson.fromJson((String)msg.obj,NavigationBean.class);
                    getView().loadSucceed(lableBean);
                    break;
            }
            return false;
        }
    });

    @Override
    public void getData() {
        showDialog();
        Call call = getModel().getData();
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
                msg.what = 1;
                handler.sendMessage(msg);
            }
        });
    }
}
