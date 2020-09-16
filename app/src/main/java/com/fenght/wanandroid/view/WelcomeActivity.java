package com.fenght.wanandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.weight.launcher.LauncherView;

import androidx.appcompat.app.AppCompatActivity;
import me.jessyan.autosize.internal.CancelAdapt;

public class WelcomeActivity extends AppCompatActivity implements CancelAdapt {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        final LauncherView launcherView = findViewById(R.id.load_view);
        launcherView.start();

        launcherView.setAnimListener(new LauncherView.AnimListener() {
            @Override
            public void finish() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                    }
                },1000);
            }
        });
    }
}
