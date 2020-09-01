package com.fenght.wanandroid.fragment;

import android.os.Bundle;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.adapter.NavigationAdapter;
import com.fenght.wanandroid.base.BaseFragment;
import com.fenght.wanandroid.bean.NavigationBean;
import com.fenght.wanandroid.contract.NavigationContract;
import com.fenght.wanandroid.inject.InjectPresenter;
import com.fenght.wanandroid.presenter.NavigationPresenter;
import com.fenght.wanandroid.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class NavigationFragment extends BaseFragment implements NavigationContract.INavigationView {
    @InjectPresenter
    private NavigationPresenter navigationPresenter;

    private RecyclerView rv_recyclerView;
    private NavigationAdapter adapter; //适配器
    private GridLayoutManager gridLayoutManager; //列表布局

    private List<NavigationBean.DataBean.ArticlesBean> articlesBeanList = new ArrayList<>();

    @Override
    protected int setLayout() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        rv_recyclerView = $(R.id.rv_recyclerView);
    }

    @Override
    protected void initData() {
        if (articlesBeanList.size() == 0) {
            //获取数据
            navigationPresenter.getData();
        }
    }

    //处理数据，将数据展平
    private void dealData(List<NavigationBean.DataBean> list){
        articlesBeanList = new ArrayList<>();
        for (int i = 0;i < list.size();i++) {
            NavigationBean.DataBean.ArticlesBean articlesBean = new NavigationBean.DataBean.ArticlesBean();
            articlesBean.setChapterName(list.get(i).getArticles().get(0).getChapterName());
            articlesBeanList.add(articlesBean);
            articlesBeanList.addAll(list.get(i).getArticles());
        }
    }

    @Override
    public <T> void loadSucceed(T t) {
        NavigationBean bean = (NavigationBean)t;
        dealData(bean.getData());
        adapter = new NavigationAdapter(getContext(),articlesBeanList);
        //瀑布流布局:4行、水平分布
        gridLayoutManager = new GridLayoutManager(getContext(),3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //标题占据三列，该值必须小于上面设置的spanCount数值
                return adapter.isTitle(position) ? gridLayoutManager.getSpanCount() : 1;
            }
        });
        rv_recyclerView.setLayoutManager(gridLayoutManager);
        rv_recyclerView.setAdapter(adapter);
    }

    @Override
    public void errorMsg(String s) {
        ToastUtil.toastShort(s);
    }
}
