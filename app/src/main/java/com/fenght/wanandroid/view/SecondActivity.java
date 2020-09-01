package com.fenght.wanandroid.view;

import android.os.Bundle;

import com.fenght.wanandroid.R;
import com.fenght.wanandroid.fragment.SecondFragment;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment,new SecondFragment("无关紧要")).commit();

    }
}
