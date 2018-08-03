package com.steven.oschina.ui.synthetical.article;

import android.content.Context;
import android.content.Intent;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;

public class WriteBlogActivity extends BaseActivity {
    public static void show(Context context) {
        context.startActivity(new Intent(context, WriteBlogActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_blog;
    }

    @Override
    protected void initData() {
    }
}
