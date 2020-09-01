package com.fenght.wanandroid.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by whstywh on 2017/7/31.
 * GitHub：https://github.com/whstywh
 * email：whstywh@gmail.com
 * description：通用Adapter
 */

public abstract class CommonRAdapter<T> extends RecyclerView.Adapter<CommonRAdapter.CommonViewHolder> {

    protected Context mContext;
    protected List<T> mList;
    protected int mLayoutId;
    protected LayoutInflater mLayoutInflater;
    public OnItemClickListener mItemClickListener;
    public OnLongClickListener mLongClickListener;

    public CommonRAdapter(Context context, List<T> list, int layoutId) {
        this.mContext = context;
        this.mList = list;
        this.mLayoutId = layoutId;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(mLayoutId, parent, false);
        CommonViewHolder holder = new CommonViewHolder(view, mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommonRAdapter.CommonViewHolder holder, int position) {
        convert(holder, mList.get(position));
    }

    public abstract void convert(CommonViewHolder holder, T itemData);

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //刷新列表
    public void refresh(){
        notifyDataSetChanged();
    }

    public void re(){
    }

    public interface OnItemClickListener {
        void itemClick();
    }

    public interface OnLongClickListener {
        boolean longClick();
    }


    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener longClickListener) {
        this.mLongClickListener = longClickListener;
    }

    /***
     * ViewHolder
     */
    public class CommonViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> mSparseArray;

        public CommonViewHolder(View itemView, Context context) {
            super(itemView);
            mSparseArray = new SparseArray<>();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.itemClick();
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mLongClickListener == null ? false : mLongClickListener.longClick();
                }
            });
        }

        public CommonViewHolder setText(int viewId, CharSequence charSequence) {
            TextView textView = getView(viewId);
            textView.setText(charSequence);
            return this;
        }

        public CommonViewHolder setImage(int viewId, CharSequence charSequence) {
            ImageView imageView = getView(viewId);
            Glide.with(mContext).load(charSequence).into(imageView);
            return this;
        }


        private <T extends View> T getView(int viewId) {
            View view = mSparseArray.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mSparseArray.put(viewId, view);
            }
            return (T) view;
        }
    }
}
