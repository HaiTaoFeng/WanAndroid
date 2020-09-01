package com.fenght.wanandroid.weight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.fenght.wanandroid.R;

import androidx.appcompat.app.AlertDialog;

public class ProgressDialog {
    private static AlertDialog.Builder builder;
    private static AlertDialog dialog;

    public static void showDialog(Context context){
        if (dialog == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
            builder = new AlertDialog.Builder(context).setView(view);
            dialog = builder.create();
            dialog.show();
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = 200;
            params.height = 180 ;
            dialog.getWindow().setAttributes(params);
        }
    }

    public static void cancleDialog(){
        if (dialog != null){
            dialog.dismiss();
        }
    }
}
