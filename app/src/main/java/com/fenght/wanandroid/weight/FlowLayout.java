package com.fenght.wanandroid.weight;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式布局控件
 * @author fenghaitao
 * @time 2021年2月18日14:38:57
 */
public class FlowLayout extends ViewGroup {
    private int mHorizontalSpacing = dp2px(16); //每个item横向间距
    private int mVerticalSpacing = dp2px(8); //每个item横向间距
    private int margin;
    private List<List<View>> allLineViews;
    private List<Integer> allHeights;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    //初始化几个数组
    private void initMeasureData(){
        allLineViews = new ArrayList<>();
        allHeights = new ArrayList<>();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        initMeasureData();
        //获取其子View的个数
        int childCount = getChildCount();
        // 创建一个ListView用来存储每一行的子View
        List<View> lineViews = new ArrayList<>();
        //得到FLowLayout 自身的width 和 height
        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
        //定义两个局部变量 用来记录一行已使用的width 和height
        int lineUseWidth =0;
        int lineUseHeight = 0;
        //定义两个局部变量 空间本身已使用的width 和height
        int parentUseWidth = 0;
        int parentUseHeight = 0;
        // 通过遍历 去测量每个子View的大小
        for (int i = 0; i <childCount; i++) {
            //获取子View 并进行测量，通过查看源码得知，我们测量一个View大小是，
            // 是需要通过父View的SpecMode和layoutparams来进行测量的。
            // SpecMode 可以通过 onMeasure()的入参 widthMeasureSpec、heightMeasureSpec的高两位得知，
            // LayoutParamsk 直接view.getLayoutParams()就可以知道
            View childView = getChildAt(i);
            LayoutParams lp = childView.getLayoutParams();

            //通过getChildMeasureSpec(),得子View的Spec 然后进行测量
            int childViewWidthSpec = getChildMeasureSpec(widthMeasureSpec,getPaddingLeft()+getPaddingRight(),lp.width);
            int childViewHeightSpec = getChildMeasureSpec(heightMeasureSpec,getPaddingTop()+getPaddingBottom(),lp.height);
            childView.measure(childViewWidthSpec,childViewHeightSpec);

            //测量后将其加到容器中
            lineViews.add(childView);

            //测量之后才可以显示之前，可以通过getMeasuredWidth,getMeasuredHeight 得知 子View的真实大小。
            int childViewMeasureWidth =childView.getMeasuredWidth();
            int childViewMeasuredHeight = childView.getMeasuredHeight();
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            //有了子View的大小了，计算得出 已使用的 width 和Height
            lineUseHeight = Math.max(lineUseHeight,childViewMeasuredHeight);//取一行中最高的作为行高
            // 每一个空间及间距相加就是已使用的行宽
            lineUseWidth = childViewMeasureWidth+ mlp.leftMargin +lineUseWidth;

            //换行    当满一行时换行 并记录
            if (lineUseWidth+childViewMeasureWidth+ mlp.leftMargin >selfWidth){
                //把每一行的views存起来  在onlayout中使用
                allLineViews.add(lineViews);
                //把每一行的行高也存起来  在onlayout中使用
                allHeights.add(lineUseHeight);
                //计算 父View的已用的宽高
                parentUseWidth = Math.max(lineUseWidth+mlp.leftMargin,parentUseWidth);
                parentUseHeight = parentUseHeight+lineUseHeight+ mlp.topMargin;

                //已满一行，这几个状态值清零
                lineViews =new ArrayList<>();
                lineUseHeight =0;
                lineUseWidth =0;
            }

            //还有最后行要单独判断，防止不满一行时不显示
            if (i == childCount-1){
                allHeights.add(lineUseHeight);
                allLineViews.add(lineViews);
                parentUseWidth = Math.max(lineUseWidth+ mlp.leftMargin,parentUseWidth);
                parentUseHeight = parentUseHeight+lineUseHeight+ mlp.topMargin ;
            }

        }

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = widthMode== MeasureSpec.EXACTLY? selfWidth:parentUseWidth ;
        int measureHeight = heightMode ==MeasureSpec.EXACTLY? selfHeight: parentUseHeight;
        setMeasuredDimension(measureWidth,measureHeight);


    }
    //通过代码可以添加View
    public void setViews(List<View> views){
        for (int i = 0; i < views.size(); i++) {
            addView(views.get(i));
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //  重新布局
        // 将每一个view进行摆放即可，已经测量好， 所有的View的Left top right bottom
        //计算好了直接进行layout即可

        int childCount = allLineViews.size();
        int curL = getPaddingLeft();
        int curT= getPaddingTop();
        int tempT = 0;

        for (int i = 0; i < childCount; i++) {
            List<View> views = allLineViews.get(i);
            int height  = allHeights.get(i);

            for (int j = 0; j < views.size(); j++) {
                View view = views.get(j);
                int left = curL;
                int top = curT;
                int right = left + view.getMeasuredWidth();
                int bottom = top+ view.getMeasuredHeight();
                MarginLayoutParams mlp = (MarginLayoutParams) view.getLayoutParams();
                view.layout(left,top,right,bottom);
                //每一个view布局完后，curL 会变化，值为 当前view的right + 自定义的间距
                curL = right + mlp.leftMargin ;
                tempT = mlp.topMargin;
            }
            //每一行结束后  重置left 和top
            curL = getPaddingLeft();
            curT = height+curT + tempT;

        }

    }

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

}
