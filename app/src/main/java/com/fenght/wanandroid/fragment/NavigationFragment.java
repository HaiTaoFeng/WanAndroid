package com.fenght.wanandroid.fragment;

import android.os.Bundle;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.adapter.NavigationAdapter;
import com.fenght.wanandroid.base.BaseFragment;
import com.fenght.wanandroid.bean.NavigationBean;
import com.fenght.wanandroid.contract.NavigationContract;
import com.fenght.wanandroid.inject.InjectPresenter;
import com.fenght.wanandroid.mvp.presenter.NavigationPresenter;
import com.fenght.wanandroid.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NavigationFragment extends BaseFragment implements NavigationContract.INavigationView {
    @InjectPresenter
    private NavigationPresenter navigationPresenter;

    private RecyclerView rv_recyclerView;
    private NavigationAdapter adapter; //适配器
    private GridLayoutManager gridLayoutManager; //列表布局

    private List<NavigationBean.DataBean.ArticlesBean> articlesBeanList = new ArrayList<>();

    @Override
    protected int setLayout() {
        return R.layout.fragment_navigation;
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

    @Override
    public <T> void succeed(T t) {
        if (t instanceof NavigationBean) {
            NavigationBean bean = (NavigationBean)t;
            articlesBeanList = navigationPresenter.dealData(bean.getData());
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
    }

    @Override
    public void error(String s) {
        ToastUtil.toastShort(s);
    }
}
