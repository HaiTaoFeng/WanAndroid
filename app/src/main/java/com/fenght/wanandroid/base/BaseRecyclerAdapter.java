package com.fenght.wanandroid.base;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


/**
 * Created by qibin on 2015/11/5.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private List<T> mDatas;
    private View mHeaderView;
    private OnItemClickListener mListener;
    private OnLongClickListener mLongListener;

    public BaseRecyclerAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    public void setOnLongListener(OnLongClickListener mLongListener) {
        this.mLongListener = mLongListener;
    }

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;

    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }


    public View getHeaderView() {
        return mHeaderView;
    }

    public boolean hasHeaderView() {
        return mHeaderView != null;
    }


    public void addDatas(List<T> datas) {//刷新数据
        mDatas.addAll(datas);
        notifyDataSetChanged();

    }

    //刷新列表
    public void refresh(List<T> mDatas){
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    //移除指定位置数据
    public void removeItem(int position){
        if (mHeaderView != null) {
            notifyItemRemoved(position + 1); //移除该item
            if (position != mDatas.size()) {
                if (position == 0) {
                    position += 1;
                }
                notifyItemRangeChanged(position, mDatas.size() - position);
            }
        }else{
            notifyItemRemoved(position); //移除该item
            if (position != mDatas.size()) {
                notifyItemRangeChanged(position, mDatas.size() - position);
            }
        }

    }

    //插入数据
    public void addItem(int position){
        notifyItemInserted(position);
        if (position != mDatas.size()) {
            notifyItemRangeChanged(position, mDatas.size() - position);
        }
    }


    @Override

    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;

    }


    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        return onCreate(parent, viewType);
    }


    @Override

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(viewHolder);
        final T data = mDatas.get(pos);
        onBind(viewHolder, pos, data);
        if (mListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos, data);
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongListener.onLongClick(pos,data);
                    return true;
                }
            });
        }
    }


    @Override

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }


    @Override

    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }


    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    public abstract RecyclerView.ViewHolder onCreate(ViewGroup parent, final int viewType);
    public abstract void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, T data);
    public class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int position, T data);
    }
    public interface OnLongClickListener<T> {
        void onLongClick(int position, T data);
    }
}

