package com.fenght.wanandroid.presenter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.base.BasePresenter;
import com.fenght.wanandroid.bean.HomeArticleBean;
import com.fenght.wanandroid.bean.SystemLableBean;
import com.fenght.wanandroid.contract.SystemContract;
import com.fenght.wanandroid.model.SystemModel;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SystemPresenter extends BasePresenter<SystemContract.ISystemView, SystemModel> implements SystemContract.ISystemPresenter{

    private static final String TAG ="SystemPresenter" ;

    @Override
    public void getLableData() {
        showDialog();
        Call call = getModel().getLableData();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pushData(e.getMessage(),0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                SystemLableBean bean = gson.fromJson(s,SystemLableBean.class);
                pushData(bean,1);
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
                pushData(s,0);
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
    public void getArticleByAuthor(int page, String author) {
        showDialog();
        Call call = getModel().getArticleByAuthor(page,author);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String s = e.getMessage();
                pushData(s,0);
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


    /**
     * 获取PopupWindow对象
     * @param contentView 布局
     */
    public PopupWindow getPopupWindow(View contentView){
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        PopupWindow window =new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置动画
        window.setAnimationStyle(R.style.pop_Anim);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        return window;
    }
}
