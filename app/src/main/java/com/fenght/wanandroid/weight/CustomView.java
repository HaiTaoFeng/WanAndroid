package com.fenght.wanandroid.weight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

public class CustomView extends View {
    private Paint paint,circle_paint,back_paint;
    private int width,height;
    private int startX,startY,moveX,moveY,endX,endY;
    private int circleX,circleY;
    private double t = 0.5;
    int[] mCircleColors ={Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.LTGRAY};
    private int mCircleRadius = 8;
    private float circle_angle = 0f;
    private float mCurrentRotationRadius = 30;
    private float backCircleRadius = 0; //背景圆半径

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        //准备画布
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        circle_paint = new Paint();
        circle_paint.setColor(Color.YELLOW);
        circle_paint.setStrokeWidth(3);
        back_paint = new Paint();

        back_paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        back_paint.setAntiAlias(true);
        back_paint.setColor(Color.WHITE);
        back_paint.setAlpha(0x40);
//        back_paint.setStrokeWidth(3);
        getAnimtion();
    }

    private void draw1(Canvas canvas){
//        bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
//        Paint paint = new Paint();
//        paint.setAlpha(0x40); //设置透明程度
//        canvas.drawBitmap(bitmap, 0, 0,paint);
        canvas.drawColor(Color.WHITE);
        Path path= new Path();
        path.moveTo(startX,startY);
//        path.quadTo(moveX,moveY,width,0);
        path.rQuadTo(moveX,moveY,width,0);
        canvas.drawPath(path,paint);
        canvas.drawCircle(circleX,circleY-20,20,circle_paint);
//        canvas.drawCircle(width/2,height/2,5,circle_paint);
        drawCircles(canvas);
    }

    private ValueAnimator mAnimator;
    private ValueAnimator scaleAnimator;
    private void getAnimtion(){
        mAnimator = ValueAnimator.ofFloat(0f,(float) Math.PI*2);
        mAnimator.setDuration(1200);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //计算某个时刻当前的大圆旋转了的角度是多少？
                circle_angle = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);//重复
        mAnimator.start();
    }

    public void setEndAnimtion(){
        mAnimator.cancel();
        scaleAnimator = ValueAnimator.ofFloat(0f,mCurrentRotationRadius);
        scaleAnimator.setDuration(1200);
        scaleAnimator.setInterpolator(new OvershootInterpolator(10f));
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentRotationRadius = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        scaleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setBackAnimtion();
            }
        });
        scaleAnimator.reverse();
    }

    private void setBackAnimtion(){
        ValueAnimator backAnimator = ValueAnimator.ofFloat(0f,height/2);
        backAnimator.setDuration(1200);
        backAnimator.setInterpolator(new LinearInterpolator());
        backAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                backCircleRadius = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        backAnimator.start();
    }


    private void drawCircles(Canvas canvas) {
        //每个小圆之间的间隔角度 = 2π/小圆的个数
        float rotationAngle = (float) (2 * Math.PI / mCircleColors.length);
        Log.i("barry", "length------:" + mCircleColors.length);
        for (int i = 0; i < mCircleColors.length; i++) {
            /**
             * x = r*cos(a) +centerX
             * y=  r*sin(a) + centerY
             * 每个小圆i*间隔角度 + 旋转的角度 = 当前小圆的真是角
             */
            double angle = i * rotationAngle + circle_angle;
            float cx = (float) (mCurrentRotationRadius * Math.cos(angle) + moveX);
            float cy = (float) (mCurrentRotationRadius * Math.sin(angle) + moveY);
            Log.e("fht","正在画圆，mCurrentRotationAngle>>>>>" + circle_angle);
            circle_paint.setColor(mCircleColors[i]);
            canvas.drawCircle(cx, cy, mCircleRadius, circle_paint);
        }
        if (backCircleRadius > 0) {
            canvas.drawColor(Color.TRANSPARENT);
            Log.e("fht","正在画圆，backCircleRadius>>>>>" + backCircleRadius);
            canvas.drawCircle(moveX,moveY,backCircleRadius,back_paint);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        moveX = width/2;
        moveY = height/2;
        endX = width;
        endY = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        draw1(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_MOVE:
                Log.e("fht","手指正在移动");
//                path.quadTo(event.getX(),event.getY(),width,0);
//                canvas.drawPath(path,paint);
                moveX = (int)event.getX();
                moveY = (int)event.getY();
                circleX = (int)(t * t * startX + 2 * t * (1-t) * moveX + t * t * endX);
                circleY = (int)(t * t * startY + 2 * t * (1-t) * moveY + t * t * endY);
                mCurrentRotationRadius++;
                circle_angle++;
                invalidate();
                break;
        }

        return true;
    }
}
