package com.fenght.wanandroid.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.bean.ProjectSortBean;

import java.util.List;

public class ProjectSortAdapter extends CommonRAdapter<ProjectSortBean.DataBean>{
    private Context context;
    private List<ProjectSortBean.DataBean> list;
    private OnItemClick onItemClick;
    private int flag = 0; //标记文字颜色

    public ProjectSortAdapter(Context context, List<ProjectSortBean.DataBean> list, int layoutId) {
        super(context, list, layoutId);
        this.context = context;
        this.list = list;
    }

    @Override
    public void convert(final CommonViewHolder holder, ProjectSortBean.DataBean itemData, final int postion) {
        holder.setText(R.id.tv_textview,itemData.getName());
        final TextView textView = holder.getView(R.id.tv_textview);
        if (flag == postion) {
            textView.setTextColor(context.getResources().getColor(R.color.red));
        }else{
            textView.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    flag = postion;
                    onItemClick.setOnItemClick(postion);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void refresh(List<ProjectSortBean.DataBean> list) {
        super.refresh(list);
    }

    /**
     * 改变该栏底色，变为选中状态
     * @param pos  选中位置
     */
    public void refreshItem(int pos) {
        flag = pos;
        notifyDataSetChanged(); //刷新list
    }



    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick{
        void setOnItemClick(int postion);
    }
}
