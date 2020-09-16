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
import com.fenght.wanandroid.contract.SecondContract;
import com.fenght.wanandroid.inject.InjectPresenter;
import com.fenght.wanandroid.presenter.ProjectArticlePresenter;
import com.fenght.wanandroid.presenter.SecondPresenter;
import com.fenght.wanandroid.utils.ToastUtil;
import com.fenght.wanandroid.weight.PopupDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectArticleFragment extends BaseFragment implements ProjectArticleContract.IProjectArticleView {
    @InjectPresenter
    private ProjectArticlePresenter articlePresenter;

    private RecyclerView rv_recyclerView;
    private TextView tv_title;
    private ProjectArticleAdapter adapter;
    private FloatingActionButton fab;

    private boolean sIsScrolling = false;
    private String title;
    private int page = 0;
    private int cid;

    private List<ProjcetArticleBean.DataBean.DatasBean> projectList = new ArrayList<>();

    public ProjectArticleFragment(String title, int cid) {
        this.title = title;
        this.cid = cid;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_project_article;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        fab = $(R.id.fab);
        tv_title = $(R.id.tv_title);
        tv_title.setText(title);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.popuwindow_default_list,null);
                RecyclerView rv_list = view.findViewById(R.id.rv_list);
                rv_list.setLayoutManager(new LinearLayoutManager(getContext()));
                final List<String> list = new ArrayList<>();
                list.add("条目1");
                list.add("条目2");
                list.add("条目3");
                list.add("条目4");
                list.add("条目5");
                list.add("条目6");
//                ProjectSortAdapter adapter = new ProjectSortAdapter(getContext(),list,R.layout.adapter_popup_dialog);
//                rv_list.setAdapter(adapter);
                final PopupDialog popupDialog = new PopupDialog(getContext(),list);
                popupDialog.setWidthHeight(200,ViewGroup.LayoutParams.WRAP_CONTENT);
                popupDialog.setAdapterListener(new CommonRAdapter.OnItemClickListener() {
                    @Override
                    public void itemClick(int postion) {
                        Toast.makeText(getActivity(),"点击" + list.get(postion),Toast.LENGTH_LONG).show();
                        popupDialog.dismiss();
                    }
                });
                popupDialog.showPopupDialog(fab, Gravity.LEFT);
            }
        });
    }

    @Override
    protected void initData() {
        if (projectList.isEmpty()) {
            articlePresenter.getData(page,cid);
        }
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
