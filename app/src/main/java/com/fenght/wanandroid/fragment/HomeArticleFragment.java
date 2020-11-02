package com.fenght.wanandroid.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fenght.wanandroid.R;
import com.fenght.wanandroid.adapter.BinnerAdapter;
import com.fenght.wanandroid.adapter.HomeArticleAdapter;
import com.fenght.wanandroid.base.BaseFragment;
import com.fenght.wanandroid.bean.BinnerBean;
import com.fenght.wanandroid.bean.HomeArticleBean;
import com.fenght.wanandroid.contract.HomeArticleContract;
import com.fenght.wanandroid.inject.InjectPresenter;
import com.fenght.wanandroid.mvp.presenter.HomeArticlePresenter;
import com.fenght.wanandroid.utils.LogUtil;
import com.fenght.wanandroid.utils.ToastUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class HomeArticleFragment extends BaseFragment implements HomeArticleContract.IHomeArticleView {
    @InjectPresenter
    private HomeArticlePresenter presenter;
    private SmartRefreshLayout srl_refreshLayout;
    private ImageView iv_loading;
    private RecyclerView rv_home_article;
    private HomeArticleAdapter adapter;
    private View viewPager;
    private ViewPager vp_binner;

    private ViewPagerThread viewPagerThread; //轮播线程

    private int page = 0; //获取文章的页数
    private List<HomeArticleBean.DataBean.DatasBean> list = new ArrayList<>();  //首页文章
    private List<BinnerBean.DataBean> beanArrayList = new ArrayList<>();  //首页binner

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            vp_binner.setCurrentItem((vp_binner.getCurrentItem() + 1) % beanArrayList.size(),true);
            return false;
        }
    });

    @Override
    protected int setLayout() {
        return R.layout.fragment_home_article;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        //首页binner轮播
        viewPager = LayoutInflater.from(getContext()).inflate(R.layout.adapter_article_head,null,false);
        vp_binner = viewPager.findViewById(R.id.vp_binner);

        srl_refreshLayout = $(R.id.srl_refreshLayout); //下拉刷新布局
        iv_loading = $(R.id.iv_loading); //刷新动画
        rv_home_article = $(R.id.rv_home_article); //文章列表

        srl_refreshLayout.setEnableRefresh(true);
        srl_refreshLayout.setEnableLoadMore(true);
        //设置固定大小
        rv_home_article.setHasFixedSize(true);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        //垂直方向
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //给RecyclerView设置布局管理器
        rv_home_article.setLayoutManager(mLayoutManager);
        //加载动态图片
        Glide.with(getContext()).load(R.mipmap.loading).into(iv_loading);
        srl_refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                presenter.handleData(page);
                list.clear();
                refreshLayout.finishRefresh(2000);
            }
        });

        srl_refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.handleData(page);
                refreshLayout.finishLoadMore(2000);
            }
        });
    }

    @Override
    protected void initData() {
        if (list.size() == 0) {
            viewPagerThread = new ViewPagerThread();
            presenter.handleData(page);
        }
    }

    //设置文章列表数据
    private void setAdapter(){
        //创建适配器，并且设置
        if (adapter == null) {
            adapter = new HomeArticleAdapter(getContext(),list);
            //设置适配器
            rv_home_article.setAdapter(adapter);
            //获取binner数据
            presenter.handleBinnerData();
        }else{
            adapter.refresh(list);
        }
    }

    @Override
    public <T> void succeed(T t) {
        if (t instanceof HomeArticleBean) {
            HomeArticleBean homeArticleBean = (HomeArticleBean) t;
            page += 1;
            list.addAll(homeArticleBean.getData().getDatas());
            ToastUtil.toastShort("接口消息：" + homeArticleBean.getErrorMsg());
            if (homeArticleBean.getErrorCode() != 0) {
                return;
            }
            setAdapter();
        }

        if (t instanceof BinnerBean) {
            BinnerBean binnerBean = (BinnerBean) t;
            //获取binner数据
            beanArrayList = binnerBean.getData();
            //初始化binner适配器
            BinnerAdapter binnerAdapter = new BinnerAdapter(getContext(),beanArrayList);
            //添加适配器
            vp_binner.setAdapter(binnerAdapter);
            //添加文章适配器的头部
            adapter.setHeaderView(viewPager);
            //添加binner之后回滚到顶部
            rv_home_article.scrollToPosition(0);
            //开始轮播binner
            viewPagerThread.start();
        }
    }

    @Override
    public void error(String s) {
        ToastUtil.toastShort(s);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (viewPagerThread != null) {
            LogUtil.e("中断后onStop线程状态" + viewPagerThread.isAlive());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewPagerThread != null && viewPagerThread.isAlive()) {
//           notify();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (viewPagerThread != null) {
            viewPagerThread.interrupt();
        }
//        handler = null;
    }

    private class ViewPagerThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (true){
                try {
                    Thread.sleep(3000);
                    Message msg = new Message();
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
