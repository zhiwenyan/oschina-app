package com.steven.oschina.ui.synthetical.detail;

import android.content.Context;
import android.content.Intent;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.bean.sub.Article;

public class EnglishArticleDetailActivity extends BaseActivity {


    public static void show(Context context, Article article) {
        Intent intent = new Intent(context, EnglishArticleDetailActivity.class);
        intent.putExtra("article", article);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_english_article_detail;
    }

    @Override
    protected void initData() {
    }

}