package com.fenght.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fenght.wanandroid.R;
import com.fenght.wanandroid.bean.BinnerBean;
import com.fenght.wanandroid.mvp.view.ArticleDetailActivity;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public class BinnerAdapter extends PagerAdapter {

    private Context context;
    private List<BinnerBean.DataBean> list;

    public BinnerAdapter(Context context,List<BinnerBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    // 3、指定复用的判断逻辑
    @Override
    public boolean isViewFromObject(View view, Object object) {
        // 当滑到新的条目，又返回来，view是否可以被复用
        return view == object;
    }

    // 1、返回要显示的条目内容，创建条目
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // container：容器：ViewPager
        // position：当前要显示的条目的位置
        final ImageView imageView = new ImageView(context);
        Glide.with(context).load(list.get(position).getImagePath())
                .error(R.drawable.icon_type).placeholder(R.mipmap.ic_launcher).into(imageView);
        // a、把view对象添加到container中
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleDetailActivity.class);
                intent.putExtra("url",list.get(position).getUrl());
                context.startActivity(intent);
            }
        });
        container.addView(imageView);
        // b、把view对象返回给框架，适配器
        return imageView; // 必须要重写，否则抛异常
    }

    // 2、销毁条目
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // object 要销毁的对象
        container.removeView((View) object);
    }

}
