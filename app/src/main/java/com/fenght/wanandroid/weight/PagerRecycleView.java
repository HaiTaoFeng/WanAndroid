package com.fenght.wanandroid.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.fenght.wanandroid.utils.ToastUtil;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class PagerRecycleView extends RecyclerView {
    private boolean Intercept = true;  //是否要拦截点击事件
    public PagerRecycleView(@NonNull Context context) {
        super(context);
    }

    public PagerRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //解决recyclerView和viewPager的滑动影响
        //当滑动recyclerView时，告知父控件不要拦截事件，交给子view处理
        getParent().requestDisallowInterceptTouchEvent(Intercept);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void addOnScrollListener(@NonNull OnScrollListener listener) {
        //得到当前显示的最后一个item的view
        View lastChildView = Objects.requireNonNull(getLayoutManager()).getChildAt(getLayoutManager().getChildCount()-1);
        //得到lastChildView的bottom坐标值
        int lastChildBottom = lastChildView.getBottom();
        //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
        int recyclerBottom =  getBottom()-getPaddingBottom();
        //通过这个lastChildView得到这个view当前的position值
        int lastPosition  = getLayoutManager().getPosition(lastChildView);

        //判断lastChildView的bottom值跟recyclerBottom
        //判断lastPosition是不是最后一个position
        //如果两个条件都满足则说明是真正的滑动到了底部
        if(lastChildBottom == recyclerBottom && lastPosition == getLayoutManager().getItemCount()-1 ){
            ToastUtil.toastShort("滑动到底了");
            Intercept = false;
        }
        super.addOnScrollListener(listener);
    }
}
