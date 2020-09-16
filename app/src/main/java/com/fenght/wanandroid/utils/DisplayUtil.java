package com.fenght.wanandroid.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.fenght.wanandroid.App;

public class DisplayUtil {

    /**
     * 获取屏幕参数
     * @return dm
     */
    public static DisplayMetrics getDisplayMetrics(){
        WindowManager manager = (WindowManager) App.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * 获取屏幕的像素宽度(单位：px)
     * @return
     */
    public static int getWidthPixels(){
        return getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕的像素高度(单位：px)
     * @return
     */
    public static int getHeightPixels(){
        return getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕密度
     * @return
     */
    public static float getDensity(){
        return getDisplayMetrics().density;
    }

    /**
     * 获取屏幕密度dpi
     * @return
     */
    public static int getDensityDpi(){
        return getDisplayMetrics().densityDpi;
    }

    /**
     * 获取屏幕的宽度(单位：dp)
     * @return
     */
    public static int getScreenWidth(){
        return (int)(getWidthPixels() / getDensity());
    }

    /**
     * 获取屏幕的高度(单位：dp)
     * @return
     */
    public static int getScreenHeight(){
        return (int)(getHeightPixels() / getDensity());
    }

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
