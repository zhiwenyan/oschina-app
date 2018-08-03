package com.steven.oschina.ui.synthetical.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.greenfarm.client.base_library.utils.FragmentManagerHelper;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.bean.sub.Article;

public class EnglishArticleDetailActivity extends BaseActivity {
    private Article mArticle;

    public static void show(Context context, Article article) {
        if (article == null)
            return;
        Intent intent = new Intent(context, EnglishArticleDetailActivity.class);
        intent.putExtra("article", article);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_english_article_detail;
    }


    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mArticle = ( Article ) getIntent().getSerializableExtra("article");
    }

    @Override
    protected void initData() {
        addFragment(R.id.fl_content, EnglishArticleDetailFragment.newInstance(mArticle));
    }

    private void addFragment(int layoutId, Fragment fragment) {
        FragmentManagerHelper fragmentManagerHelper = new FragmentManagerHelper(getSupportFragmentManager(), layoutId);
        fragmentManagerHelper.add(fragment);
    }
}