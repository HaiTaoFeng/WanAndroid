package com.fenght.wanandroid.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.weight.launcher.LauncherView;

import androidx.appcompat.app.AppCompatActivity;
import me.jessyan.autosize.internal.CancelAdapt;

public class WelcomeActivity extends AppCompatActivity implements CancelAdapt {

    private LauncherView launcherView;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        launcherView = findViewById(R.id.load_view);

        launcherView.setAnimListener(new LauncherView.AnimListener() {
            @Override
            public void finish() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
//                        finish();
                    }
                },1000);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && flag) {
            flag = false;
            launcherView.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
