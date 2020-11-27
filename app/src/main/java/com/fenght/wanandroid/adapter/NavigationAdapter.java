package com.fenght.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.bean.NavigationBean;
import com.fenght.wanandroid.mvp.view.ArticleDetailActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NavigationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<NavigationBean.DataBean.ArticlesBean> list;

    private int TYPE_TITLE = 0; //标题
    private int TYPE_LABLE = 1; //标签

    public NavigationAdapter(Context context, List<NavigationBean.DataBean.ArticlesBean> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<NavigationBean.DataBean.ArticlesBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isTitle(position)) {
            return TYPE_TITLE;
        }else{
            return TYPE_LABLE;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_TITLE) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_navigation_title,parent,false);
            return new TitleHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_navigation_lable,parent,false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            return new LableHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TitleHolder) {
            TitleHolder titleHolder = (TitleHolder) holder;
            titleHolder.tv_title.setText(list.get(position).getChapterName());
        }
        if (holder instanceof LableHolder) {
            LableHolder lableHolder = (LableHolder) holder;
            lableHolder.tv_lable.setText(list.get(position).getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ArticleDetailActivity.class);
                    intent.putExtra("url",list.get(position).getLink());
                    context.startActivity(intent);
                }
            });
        }

    }

    public boolean isTitle(int position){
        if (list.get(position).getLink() == null || "".equals(list.get(position).getLink())) {
            return true;
        }else{
            return false;
        }
    }

    public static class TitleHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        public TitleHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title); //标题
        }
    }

    public static class LableHolder extends RecyclerView.ViewHolder {
        private TextView tv_lable;
        public LableHolder(@NonNull View itemView) {
            super(itemView);
            tv_lable = itemView.findViewById(R.id.tv_lable); //标签
        }
    }


}
