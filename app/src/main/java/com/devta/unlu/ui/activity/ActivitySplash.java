package com.devta.unlu.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.devta.unlu.R;
import com.devta.unlu.databinding.ActivityFeedBinding;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ActivitySplash.this, ActivityFeed.class));
                ActivityCompat.finishAffinity(ActivitySplash.this);
            }
        }, 2000L);
    }

    
}
