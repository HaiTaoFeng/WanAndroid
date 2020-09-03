package com.fenght.wanandroid.presenter;

import com.fenght.wanandroid.base.BasePresenter;
import com.fenght.wanandroid.bean.NavigationBean;
import com.fenght.wanandroid.contract.NavigationContract;
import com.fenght.wanandroid.model.NavigationModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NavigationPresenter extends BasePresenter<NavigationContract.INavigationView, NavigationModel> implements NavigationContract.INavigationPresenter{

    private static final String TAG ="NavigationPresenter" ;

    @Override
    public void getData() {
        showDialog();
        Call call = getModel().getData();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                pushData(e.getMessage(),0);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Gson gson = new Gson();
                NavigationBean bean = gson.fromJson(s,NavigationBean.class);
                pushData(bean,1);
            }
        });
    }

    //处理数据，将数据展平
    public List<NavigationBean.DataBean.ArticlesBean> dealData(List<NavigationBean.DataBean> list){
        List<NavigationBean.DataBean.ArticlesBean> articlesBeanList = new ArrayList<>();
        for (int i = 0;i < list.size();i++) {
            NavigationBean.DataBean.ArticlesBean articlesBean = new NavigationBean.DataBean.ArticlesBean();
            articlesBean.setChapterName(list.get(i).getArticles().get(0).getChapterName());
            articlesBeanList.add(articlesBean);
            articlesBeanList.addAll(list.get(i).getArticles());
        }
        return articlesBeanList;
    }
}
