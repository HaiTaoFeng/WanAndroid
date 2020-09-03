package com.fenght.wanandroid.fragment;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.fenght.wanandroid.R;
import com.fenght.wanandroid.adapter.ProjectArticleAdapter;
import com.fenght.wanandroid.base.BaseFragment;
import com.fenght.wanandroid.bean.ProjcetArticleBean;
import com.fenght.wanandroid.contract.SecondContract;
import com.fenght.wanandroid.inject.InjectPresenter;
import com.fenght.wanandroid.presenter.SecondPresenter;
import com.fenght.wanandroid.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SecondFragment extends BaseFragment implements SecondContract.ISecondView {
    @InjectPresenter
    private SecondPresenter mSecondPresenter;

    private String title;
    private RecyclerView rv_recyclerView;
    private ProjectArticleAdapter adapter;

    private boolean sIsScrolling = false;

    List<ProjcetArticleBean.DataBean.DatasBean> projectList = new ArrayList<>();


    @Override
    protected int setLayout() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        rv_recyclerView = $(R.id.rv_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rv_recyclerView.setLayoutManager(manager);

        //设置滑动监听
        rv_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    sIsScrolling = true;
                    Glide.with(getActivity()).pauseRequests();
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (sIsScrolling == true) {
                        Glide.with(getActivity()).resumeRequests();
                    }
                    sIsScrolling = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void initData() {
        mSecondPresenter.handleData();
    }

    @Override
    public void showMsg(String msg) {

    }


    @Override
    public <T> void succeed(T t) {
        if(t instanceof ProjcetArticleBean){
            ProjcetArticleBean projcetArticleBean = (ProjcetArticleBean)t;
            projectList = projcetArticleBean.getData().getDatas();
            setProjectArticleAdapter();
        }
    }

    @Override
    public void error(String s) {
        ToastUtil.toastShort(s);
    }

    //加载项目文章
    private void setProjectArticleAdapter(){
        if (adapter == null) {
            adapter = new ProjectArticleAdapter(getContext(),projectList);
            rv_recyclerView.setAdapter(adapter);
        }else{
            adapter.refresh(projectList);
        }
    }
}
