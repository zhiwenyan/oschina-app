package com.steven.oschina.ui.synthetical.article;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.steven.oschina.R;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.bean.sub.Report;
import com.steven.oschina.dialog.DialogHelper;
import com.steven.oschina.utils.TDevice;
import com.steven.oschina.utils.TypeFormat;

public class ArticleWebActivity extends WebActivity {
    private Article mArticle;

    public static void show(Context context, Article article) {
        if (!TDevice.hasWebView(context))
            return;
        if (article == null)
            return;
        Intent intent = new Intent(context, ArticleWebActivity.class);
        intent.putExtra("article", article);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_web;
    }

    @Override
    protected void initData() {
        super.initData();
        mArticle = ( Article ) getIntent().getSerializableExtra("article");
        mShareDialog.setTitle(mArticle.getTitle());
        mShareDialog.init(this, mArticle.getTitle(), mArticle.getDesc(), mArticle.getUrl());
        mWebView.loadUrl(TypeFormat.formatUrl(mArticle));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_web_artivle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                mShareDialog.show();
                break;
            case R.id.menu_report:
//                if (!AccountHelper.isLogin()) {
//                    LoginActivity.show(this);
//                    return false;
//                }
                if (mArticle != null) {
                    DialogHelper.ReportDialog.create(this, 0, TypeFormat.formatUrl(mArticle), Report.TYPE_ARTICLE, mArticle.getKey()).show();
                }
                break;
        }
        return false;
    }
}
