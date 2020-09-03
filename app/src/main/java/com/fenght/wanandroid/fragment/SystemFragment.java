package com.fenght.wanandroid.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.adapter.HomeArticleAdapter;
import com.fenght.wanandroid.adapter.SystemTitle2Adapter;
import com.fenght.wanandroid.adapter.SystemTitleAdapter;
import com.fenght.wanandroid.base.BaseFragment;
import com.fenght.wanandroid.bean.HomeArticleBean;
import com.fenght.wanandroid.bean.SystemLableBean;
import com.fenght.wanandroid.contract.SystemContract;
import com.fenght.wanandroid.inject.InjectPresenter;
import com.fenght.wanandroid.presenter.SystemPresenter;
import com.fenght.wanandroid.utils.ToastUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class SystemFragment extends BaseFragment implements SystemContract.ISystemView{
    @InjectPresenter
    private SystemPresenter systemPresenter;

    private List<SystemLableBean.DataBean> list = new ArrayList<>();
    private List<HomeArticleBean.DataBean.DatasBean> articleList = new ArrayList<>();

    private AppCompatEditText et_author;
    private AppCompatTextView tv_search;
    private FloatingActionButton fab;
    private RecyclerView rv_title_one,rv_title_two,rv_article;
    private PopupWindow window;
    private StaggeredGridLayoutManager staggeredGridLayoutManager,staggeredGridLayoutManager2;
    private SystemTitleAdapter adapter;
    private SystemTitle2Adapter adapter2;
    private HomeArticleAdapter articleAdapter;

    private int page = 0; //页数


    @Override
    protected int setLayout() {
        return R.layout.fragment_system;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        et_author = $(R.id.et_author); //作者名称
        tv_search = $(R.id.tv_search); //搜索
        fab = $(R.id.fab); //标签分类
        rv_article = $(R.id.rv_article); //标签下的文章列表
        //设置线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_article.setLayoutManager(linearLayoutManager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0) {
                    systemPresenter.getLableData();
                }
                // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//                window.showAsDropDown(fab, 0, 0);
                // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
                // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
                window.showAtLocation(fab,Gravity.BOTTOM, 0, 0);
                //设置半透明背景
                setBackgroundAlpha(0.5f);
            }
        });

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String author = et_author.getText().toString().trim();
                if ("".equals(author)) {
                    ToastUtil.toastShort("作者名称不能为空！");
                    return;
                }
                systemPresenter.getArticleByAuthor(page,author);
            }
        });
    }

    @Override
    protected void initData() {
        //判断是否第一次加载数据
        if (list.size() == 0) {
            systemPresenter.getLableData();
            //初始化弹窗
            showPopuWindow();
        }else{
            setTitleAdapter();
        }
    }

    //适配器加载一级标签数据
    private void setTitleAdapter(){
        if (adapter == null) {
            adapter = new SystemTitleAdapter(getActivity(),list);
            rv_title_one.setAdapter(adapter);
            setAdapter2(list.get(0).getChildren());
            adapter.setRecyclerItemClickListener(new SystemTitleAdapter.OnRecyclerItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    setAdapter2(list.get(position).getChildren());
                }
            });
        }else{
            adapter.refresh(list);
        }
    }

    //适配器加载二级标签数据
    private void setAdapter2(final List<SystemLableBean.DataBean.ChildrenBean> list2){
        int spanCount = 2;
        if (list2.size() <= 4) {
            spanCount = 1;
        }
        //瀑布流布局:2行、水平分布
        staggeredGridLayoutManager2 = new StaggeredGridLayoutManager(spanCount,StaggeredGridLayoutManager.HORIZONTAL);
        rv_title_two.setLayoutManager(staggeredGridLayoutManager2);
        if (adapter2 == null) {
            adapter2 = new SystemTitle2Adapter(getActivity(),list2);
            rv_title_two.setAdapter(adapter2);
        }else{
            adapter2.refresh(list2);
        }
        adapter2.setRecyclerItemClickListener(new SystemTitle2Adapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                window.dismiss();
                int id = list2.get(position).getId(); //标签id
                systemPresenter.getArticleData(page,id);
            }
        });
    }

    //加载文章数据
    private void setArticleAdapter(){
        if (articleAdapter == null) {
            articleAdapter = new HomeArticleAdapter(getContext(),articleList);
            rv_article.setAdapter(articleAdapter);
        }else{
            articleAdapter.refresh(articleList);
        }
    }

    //初始化弹窗
    private void showPopuWindow(){
        // 用于PopupWindow的View
        View contentView= LayoutInflater.from(getContext()).inflate(R.layout.popuwindow_lable, null, false);
        rv_title_one = contentView.findViewById(R.id.rv_title_one); //一级标签
        rv_title_two = contentView.findViewById(R.id.rv_title_two); //二级标签
        //瀑布流布局:4行、水平分布
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL);
        rv_title_one.setLayoutManager(staggeredGridLayoutManager);
        window = systemPresenter.getPopupWindow(contentView);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }

    /**
     * 设置背景透明度
     * @param alpha 透明值0~1。 当值为1时，背景透明
     */
    private void setBackgroundAlpha(float alpha){
        WindowManager.LayoutParams lp=getActivity().getWindow().getAttributes();
        lp.alpha = alpha;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public <T> void succeed(T t) {
        //处理标签数据
        if (t instanceof SystemLableBean) {
            SystemLableBean bean = (SystemLableBean)t;
            list = bean.getData();
            if (bean.getErrorCode() != 0 && list.size() == 0) {
                return;
            }
            //加载标签适配器
            setTitleAdapter();
            if (list.size() > 0) {
                systemPresenter.getArticleData(page,list.get(0).getChildren().get(0).getId());
            }
        }
        //处理文章数据
        if (t instanceof HomeArticleBean) { //文章类型的数据
            HomeArticleBean bean = (HomeArticleBean)t;
            articleList = bean.getData().getDatas();
            if (bean.getErrorCode() != 0) {
                ToastUtil.toastLong("数据获取失败：" + bean.getErrorMsg());
                return;
            }
            setArticleAdapter();
        }
    }

    @Override
    public void error(String s) {
        ToastUtil.toastShort(s);
    }
}
