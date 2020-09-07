package com.fenght.wanandroid.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.adapter.CommonRAdapter;
import com.fenght.wanandroid.adapter.ProjectSortAdapter;
import com.fenght.wanandroid.adapter.ViewPagerAdapter;
import com.fenght.wanandroid.base.BaseFragment;
import com.fenght.wanandroid.bean.ProjcetArticleBean;
import com.fenght.wanandroid.bean.ProjectSortBean;
import com.fenght.wanandroid.contract.SecondContract;
import com.fenght.wanandroid.inject.InjectPresenter;
import com.fenght.wanandroid.presenter.SecondPresenter;
import com.fenght.wanandroid.utils.ToastUtil;
import com.fenght.wanandroid.weight.PopupDialog;
import com.fenght.wanandroid.weight.VerticalViewPager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class SecondFragment extends BaseFragment implements SecondContract.ISecondView {
    @InjectPresenter
    private SecondPresenter mSecondPresenter;

    private RecyclerView rv_recyclerView;
    private VerticalViewPager vp_viewPager;
    private ProjectSortAdapter adapter;
    private FloatingActionButton fab;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private boolean sIsScrolling = false;

    private List<ProjectSortBean.DataBean> sortList = new ArrayList<>();


    @Override
    protected int setLayout() {
        return R.layout.fragment_second;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        fab = $(R.id.fab);
        rv_recyclerView = $(R.id.rv_recyclerView);
        vp_viewPager = $(R.id.vp_viewPager);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rv_recyclerView.setLayoutManager(manager);

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
        mSecondPresenter.handleData();
    }

    @Override
    public void showMsg(String msg) {

    }


    @Override
    public <T> void succeed(T t) {
        if(t instanceof ProjectSortBean){
            ProjectSortBean projectSortBean = (ProjectSortBean)t;
            sortList = projectSortBean.getData();
            setProjectSortAdapter();
            for (int i=1;i<sortList.size();i++) {
                fragmentList.add(new ProjectArticleFragment(sortList.get(i).getName()));
                titleList.add(sortList.get(i).getName());
            }
            fragmentList.add(new ViewPagerFragment());
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),fragmentList,titleList);
            vp_viewPager.setAdapter(viewPagerAdapter);
            vp_viewPager.setOffscreenPageLimit(1);
        }
    }

    @Override
    public void error(String s) {
        ToastUtil.toastShort(s);
    }

    //加载项目文章
    private void setProjectSortAdapter(){
        if (adapter == null) {
            adapter = new ProjectSortAdapter(getContext(),sortList,R.layout.adapter_popup_dialog);
            rv_recyclerView.setAdapter(adapter);
            adapter.setOnItemClick(new ProjectSortAdapter.OnItemClick() {
                @Override
                public void setOnItemClick(int postion) {
                    vp_viewPager.setCurrentItem(postion,true);
                }
            });
        }else{
            adapter.refresh(sortList);
        }
    }
}
