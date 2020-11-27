package com.fenght.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenght.wanandroid.R;
import com.fenght.wanandroid.base.BaseRecyclerAdapter;
import com.fenght.wanandroid.bean.ProjcetArticleBean;
import com.fenght.wanandroid.mvp.view.ArticleDetailActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectArticleAdapter extends BaseRecyclerAdapter<ProjcetArticleBean.DataBean.DatasBean> implements View.OnClickListener {
    private Context context;
    private List<ProjcetArticleBean.DataBean.DatasBean> list;
    private Drawable drawable;

    public ProjectArticleAdapter(Context context, List<ProjcetArticleBean.DataBean.DatasBean> list) {
        super(list);
        this.context = context;
        this.list = list;
    }

    //刷新数据
    @Override
    public void refresh(List<ProjcetArticleBean.DataBean.DatasBean> mDatas) {
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
        final View view = LayoutInflater.from(context).inflate(R.layout.adapter_project_article_new,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        drawable = context.getDrawable(R.drawable.ic_love);
        DrawableCompat.setTint(drawable,context.getResources().getColor(R.color.red));
        return viewHolder;
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, final int position, ProjcetArticleBean.DataBean.DatasBean data) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder)viewHolder;
            holder.tv_title.setText(list.get(position).getTitle());
            if (list.get(position).getAuthor() == null || "".equals(list.get(position).getAuthor())) {
                holder.tv_author.setText(list.get(position).getShareUser());
            }else{
                holder.tv_author.setText(list.get(position).getAuthor());
            }
            holder.tv_desc.setText(list.get(position).getDesc().trim());
            holder.tv_time.setText(list.get(position).getNiceDate());
            String picture_path = list.get(position).getEnvelopePic();
            if (!"".equals(picture_path)) {
                //加载图片
                Glide.with(context).load(picture_path).into(holder.iv_picture);
            }
            holder.tv_zan.setBackground(drawable);
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

    @Override
    public void onClick(View v) {

    }


    class ViewHolder extends Holder {
        private TextView tv_title,tv_author,tv_desc,tv_time,tv_zan;
        private ImageView iv_picture;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title); //标题
            tv_author = itemView.findViewById(R.id.tv_author); //作者
            tv_desc = itemView.findViewById(R.id.tv_desc); //描述
            tv_time = itemView.findViewById(R.id.tv_time); //时间
            iv_picture = itemView.findViewById(R.id.iv_picture); //图片
            tv_zan = itemView.findViewById(R.id.tv_zan); //赞
        }
    }



}
