package com.fenght.wanandroid.base;

import android.view.View;

/**
 * 防止多次点击
 * @author fenghaitao
 *
 */
public abstract class BaseOnClickListener implements View.OnClickListener {
    private static long TIME_INTERVAL = 1000; //间隔时间
    private static long LAST_TIME = 0;

    @Override
    public void onClick(View v) {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - LAST_TIME) >= TIME_INTERVAL) {
            LAST_TIME = TIME_INTERVAL;
            onClickListenerq(v);
        }
    }

    protected void onClickListenerq(View v){}
}
