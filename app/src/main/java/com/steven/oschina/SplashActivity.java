package com.steven.oschina;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.steven.oschina.base.BaseActivity;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            startActivity(MainActivity.class);
            finish();
        }, 1888);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onBackPressed() {
    }
}
