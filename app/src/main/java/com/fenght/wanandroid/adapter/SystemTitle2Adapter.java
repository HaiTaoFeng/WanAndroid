package com.fenght.wanandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.bean.SystemLableBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SystemTitle2Adapter extends RecyclerView.Adapter<SystemTitle2Adapter.ViewHolder> {
    private Context context;
    private List<SystemLableBean.DataBean.ChildrenBean> list;
    private OnRecyclerItemClickListener recyclerItemClickListener;
    private int index = -1; //标志被选中的标签
    private boolean flag = true; //默认加载第一个标签的文职

    public SystemTitle2Adapter(Context context, List<SystemLableBean.DataBean.ChildrenBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.adapter_system_title_one,parent,false);
        ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerItemClickListener != null) {
                    recyclerItemClickListener.onItemClick((int)view.getTag());
                    index = (int)view.getTag(); //记录选中的位置
                    notifyDataSetChanged();
                }
            }
        });
        return viewHolder;
    }

    //刷新数据
    public void refresh(List<SystemLableBean.DataBean.ChildrenBean> list){
        this.list = list;
        index = -1;
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (index == position) {
            //选中时背景为蓝色
            holder.ll_linearlayout.setBackground(context.getResources().getDrawable(R.drawable.shape_corner_blue));
        }else{
            if (flag) {
                flag = false;
                holder.ll_linearlayout.setBackground(context.getResources().getDrawable(R.drawable.shape_corner_blue));
            }else{
                holder.ll_linearlayout.setBackground(context.getResources().getDrawable(R.drawable.shape_corner));
            }
        }
        holder.tv_title_one.setText(list.get(position).getName());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setRecyclerItemClickListener(OnRecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_linearlayout;
        private TextView tv_title_one;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_linearlayout = itemView.findViewById(R.id.ll_linearlayout);
            tv_title_one = itemView.findViewById(R.id.tv_title_one); //标题
        }
    }

    /**
     * 自定义RecyclerView 中item view点击回调方法
     */
    public interface OnRecyclerItemClickListener{
        /**
         * item view 回调方法
         * @param position 点击索引
         */
        void onItemClick(int position);
    }
}
