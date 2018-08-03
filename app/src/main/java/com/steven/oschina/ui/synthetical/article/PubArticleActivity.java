package com.steven.oschina.ui.synthetical.article;

import android.content.Context;
import android.content.Intent;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;

public class PubArticleActivity extends BaseActivity {

    public static void show(Context context, String url) {
        Intent intent = new Intent(context, PubArticleActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pub_article;
    }

    @Override
    protected void initData() {



    }
}
