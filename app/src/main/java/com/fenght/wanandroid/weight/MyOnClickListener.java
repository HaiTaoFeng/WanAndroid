package com.fenght.wanandroid.weight;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;

public  abstract class MyOnClickListener implements View.OnClickListener {
    private long[] mHits = new long[2];
    private int TIME_INTERVAL = 500; //时间间隔

    public MyOnClickListener(){}

    public MyOnClickListener(int TIME_INTERVAL) {
        this.TIME_INTERVAL = TIME_INTERVAL;
    }

    @Override
    public void onClick(View v) {
        //每点击一次 实现左移一格数据
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //给数组的最后赋当前时钟值
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        //当0出的值大于当前时间-500时  证明在500毫秒内点击了2次
        if(mHits[0] < SystemClock.uptimeMillis() - TIME_INTERVAL){
            Log.e("fht","500毫秒内点击了2次");
            IonClick(v);
        }
    }

    public abstract void IonClick(View v);

}
