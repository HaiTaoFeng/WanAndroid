package com.fenght.wanandroid.weight;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class RoundView extends View {
    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);

    }

    private static final float RADIUS = 70f;
    private Point currnetPoint;
    private Paint paint;
    private RoundViewListener roundViewListener;

    @Override
    protected void onDraw(Canvas canvas) {
        //我们这里完全通过Point类的数据来画圆，因此如果他是Null的话
        //就是第一次画圆，我们做第一次画圆的初始化
        if (currnetPoint == null) {
            currnetPoint = new Point(RADIUS, RADIUS);
            float x = currnetPoint.getX();
            float y = currnetPoint.getY();
            canvas.drawCircle(x, y, RADIUS, paint);
            //拿到将要进行变化的Point的始态和末态
            Point startPoint = new Point(RADIUS, RADIUS);
            Point endPoint = new Point(700, 1000);
            //用ofObject来创建ValueAnimator对象
            ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
            //用animator对象设置各种参数
            animator.setDuration(2000);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            //用animator对象设置更新监听器,在onAnimationUpdate回调中对数据实体类进行更新，
            //然后调用invalidate()。最后开启动画调用start()。
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    currnetPoint = (Point) animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.start();
        } else {
            float x = currnetPoint.getX();
            float y = currnetPoint.getY();
            canvas.drawCircle(x, y, RADIUS, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                float roundx = currnetPoint.getX();
                float roundy = currnetPoint.getY();
                if (x > (roundx - RADIUS) && x < (roundx + RADIUS) && y > (roundy - RADIUS) && y < (roundy + RADIUS)) {
                    roundViewListener.OnClick();
                    return true;
                }
        }
        return super.onTouchEvent(event);
    }

    public void setRoundViewListener(RoundViewListener roundViewListener) {
        this.roundViewListener = roundViewListener;
    }

    public interface RoundViewListener{
        void OnClick();
    }

    class PointEvaluator implements TypeEvaluator<Point> {

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            float x=startValue.getX()+fraction*(endValue.getX()-startValue.getX());
            float y=startValue.getY()+fraction*(endValue.getY()-startValue.getY());
            Point point=new Point(x,y);
            return point;
        }
    }

    class Point{
        float x,y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }
}
