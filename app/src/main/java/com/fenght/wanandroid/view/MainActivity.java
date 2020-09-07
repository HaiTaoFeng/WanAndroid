package com.fenght.wanandroid.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.adapter.ViewPagerAdapter;
import com.fenght.wanandroid.base.BaseActivity;
import com.fenght.wanandroid.contract.MainContract;
import com.fenght.wanandroid.fragment.HomeArticleFragment;
import com.fenght.wanandroid.fragment.NavigationFragment;
import com.fenght.wanandroid.fragment.SecondFragment;
import com.fenght.wanandroid.fragment.SystemFragment;
import com.fenght.wanandroid.inject.InjectPresenter;
import com.fenght.wanandroid.presenter.MainPresenter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends BaseActivity implements MainContract.IMainView, View.OnClickListener {
    @InjectPresenter
    private MainPresenter mainPresenter; //通过注解进行实例化
    private TabLayout mTl_tabLayout;
    private ViewPager mVp_viewPager;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected void initLayout(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initViews() {
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mTl_tabLayout = $(R.id.tl_tabLayout);
        mVp_viewPager = $(R.id.vp_viewPager);
        drawer = $(R.id.drawer_layout);
        navigationView = $(R.id.nav_view);

        titles.add("首页");
        titles.add("体系");
        titles.add("导航");
        titles.add("项目");
        titles.add("公众号");
        titles.add("体系");
        titles.add("公众号");
        titles.add("体系");
        fragmentList.add(new HomeArticleFragment());
        fragmentList.add(new SystemFragment());
        fragmentList.add(new NavigationFragment());
        fragmentList.add(new SecondFragment());
        for (int i = 0;i<titles.size();i++) {
            mTl_tabLayout.addTab(mTl_tabLayout.newTab());
            mTl_tabLayout.getTabAt(i).setText(titles.get(i));
            if (i > 3) {
                fragmentList.add(new SecondFragment());
            }
        }
        if (titles.size() <= 6) {
            mTl_tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,titles);
        mVp_viewPager.setAdapter(adapter);
        mVp_viewPager.setOffscreenPageLimit(3);
        mTl_tabLayout.setupWithViewPager(mVp_viewPager);
    }

    @Override
    protected void initData() {
        mainPresenter.handleData();
    }


    @Override
    public void showDialog() {
        Toast.makeText(MainActivity.this,"开始获取数据",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void succesMsg() {
//        tv_text.setText("请求成功！");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public <T> void succeed(T t) {

    }

    @Override
    public void error(String s) {

    }
}
