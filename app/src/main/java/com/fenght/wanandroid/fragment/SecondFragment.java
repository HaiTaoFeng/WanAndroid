package com.fenght.wanandroid.fragment;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.adapter.CommonRAdapter;
import com.fenght.wanandroid.adapter.NavigationAdapter;
import com.fenght.wanandroid.base.BaseFragment;
import com.fenght.wanandroid.bean.NavigationBean;
import com.fenght.wanandroid.contract.SecondContract;
import com.fenght.wanandroid.inject.InjectPresenter;
import com.fenght.wanandroid.presenter.SecondPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class SecondFragment extends BaseFragment implements SecondContract.ISecondView {
    @InjectPresenter
    private SecondPresenter mSecondPresenter;

    private String title;
    private RecyclerView rv_recyclerView;
    private NavigationAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private WebView wb_webView;

    List<NavigationBean.DataBean.ArticlesBean> articlesBeanList = new ArrayList<>();

    public SecondFragment(String title) {
        this.title = title;
    }




    @Override
    protected int setLayout() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        rv_recyclerView = $(R.id.rv_recyclerView);
//        wb_webView = $(R.id.wb_webView);
//        wb_webView.loadUrl("https://www.wanandroid.com/index");
//        //设置自适应屏幕，两者合用
//        WebSettings webSettings = wb_webView.getSettings();
//        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
    }

    @Override
    protected void initData() {
        mSecondPresenter.handleData();
    }

    @Override
    public void showDailog() {
        Toast.makeText(getActivity(),"开始" + title + "请求",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMsg(String msg) {
        NavigationBean bean = new Gson().fromJson(msg,NavigationBean.class);
        dealData(bean.getData());
        adapter = new NavigationAdapter(getContext(),articlesBeanList);
        //瀑布流布局:4行、水平分布
        gridLayoutManager = new GridLayoutManager(getContext(),3);
        //动态设置所占列数
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.isTitle(position)) {
                    return 3;
                }else{
                    return 1;
                }
            }
        });
        rv_recyclerView.setLayoutManager(gridLayoutManager);
        rv_recyclerView.setAdapter(adapter);
    }

    private void dealData(List<NavigationBean.DataBean> list){
        articlesBeanList = new ArrayList<>();
        for (int i = 0;i < list.size();i++) {
            NavigationBean.DataBean.ArticlesBean articlesBean = new NavigationBean.DataBean.ArticlesBean();
            articlesBean.setChapterName(list.get(i).getArticles().get(0).getChapterName());
            articlesBeanList.add(articlesBean);
            articlesBeanList.addAll(list.get(i).getArticles());
        }
    }

}
