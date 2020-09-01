package com.fenght.wanandroid.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.widget.Toast;

import com.fenght.wanandroid.App;

public class ToastUtil {

    private static Toast toast;

    @SuppressLint("ShowToast")
    public static void toastLong(String s){
        cancleToast();
        if (toast == null) {
            toast = Toast.makeText(App.getContext(),s,Toast.LENGTH_LONG);
        }
        toast.setText(s);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }


    @SuppressLint("ShowToast")
    public static void toastShort(String s){
        cancleToast();
        if (toast == null) {
            toast = Toast.makeText(App.getContext(),s,Toast.LENGTH_SHORT);
        }
        toast.setText(s);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private static void cancleToast(){
        if (toast != null) {
            toast.cancel();
        }
    }
}
