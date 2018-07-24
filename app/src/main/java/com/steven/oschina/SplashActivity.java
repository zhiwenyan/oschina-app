package com.steven.oschina;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.steven.oschina.base.BaseActivity;

/**
 * 有的是两个页面 有广告的时候显示广告页
 * 有的是广告嵌在闪屏页里面 点击消失 广告就没了
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> {
            startActivity(MainActivity.class);
            finish();
        }, 888);
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
