package com.fenght.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.base.BaseRecyclerAdapter;
import com.fenght.wanandroid.bean.HomeArticleBean;
import com.fenght.wanandroid.mvp.view.ArticleDetailActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeArticleAdapter extends BaseRecyclerAdapter<HomeArticleBean.DataBean.DatasBean> {
    private Context context;
    private List<HomeArticleBean.DataBean.DatasBean> list;

    public HomeArticleAdapter(Context context, List<HomeArticleBean.DataBean.DatasBean> list) {
        super(list);
        this.context = context;
        this.list = list;
    }

    //刷新数据
    @Override
    public void refresh(List<HomeArticleBean.DataBean.DatasBean> mDatas) {
        list = mDatas;
        super.refresh(mDatas);
    }

    @Override
    public void setHeaderView(View headerView) {
        super.setHeaderView(headerView);
    }

    @Override
    public View getHeaderView() {
        return super.getHeaderView();
    }


    @Override
    public void setOnLongListener(OnLongClickListener mLongListener) {
        super.setOnLongListener(mLongListener);
    }

    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.adapter_home_article,parent,false);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, final int position, HomeArticleBean.DataBean.DatasBean data) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder)viewHolder;
            holder.tv_title.setText(list.get(position).getTitle());
            if (list.get(position).getAuthor() == null || "".equals(list.get(position).getAuthor())) {
                holder.tv_author.setText(list.get(position).getShareUser());
            }else{
                holder.tv_author.setText(list.get(position).getAuthor());
            }
            holder.tv_type.setText(list.get(position).getSuperChapterName());
            holder.tv_time.setText(list.get(position).getNiceDate());
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ArticleDetailActivity.class);
                    intent.putExtra("url",list.get(position).getLink());
                    context.startActivity(intent);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    list.remove(position);
                    removeItem(position);
                    return true;
                }
            });
        }

    }




     class ViewHolder extends BaseRecyclerAdapter.Holder {
        private TextView tv_title,tv_author,tv_type,tv_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title); //标题
            tv_author = itemView.findViewById(R.id.tv_author); //作者
            tv_type = itemView.findViewById(R.id.tv_type); //类型
            tv_time = itemView.findViewById(R.id.tv_time); //时间
        }
    }



}
