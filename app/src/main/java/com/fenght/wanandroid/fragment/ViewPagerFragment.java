package com.fenght.wanandroid.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fenght.wanandroid.R;
import com.fenght.wanandroid.adapter.CommonRAdapter;
import com.fenght.wanandroid.adapter.ProjectArticleAdapter;
import com.fenght.wanandroid.base.BaseFragment;
import com.fenght.wanandroid.bean.ProjcetArticleBean;
import com.fenght.wanandroid.contract.ProjectArticleContract;
import com.fenght.wanandroid.inject.InjectPresenter;
import com.fenght.wanandroid.presenter.ProjectArticlePresenter;
import com.fenght.wanandroid.utils.ToastUtil;
import com.fenght.wanandroid.weight.PopupDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPagerFragment extends BaseFragment implements ProjectArticleContract.IProjectArticleView {
    @InjectPresenter
    private ProjectArticlePresenter articlePresenter;
    private TextView textView;



    @Override
    protected int setLayout() {
        return R.layout.fragment_viewpager;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        textView = $(R.id.tv_title);
    }

    @Override
    protected void initData() {
        articlePresenter.getData(0,120);
    }



    @Override
    public <T> void succeed(T t) {

    }

    @Override
    public void error(String s) {
        ToastUtil.toastShort(s);
    }

    //加载项目文章
    private void setProjectArticleAdapter(){

    }
}
