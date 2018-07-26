package com.steven.oschina.ui.synthetical.article;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.greenfarm.client.base_library.utils.FragmentManagerHelper;
import com.greenfarm.client.base_library.utils.StatusBarUtil;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.bean.sub.Article;
import com.steven.oschina.widget.CommentShareView;

import butterknife.BindView;

public class ArticleDetailActivity extends BaseActivity {
    @BindView(R.id.shareView)
    CommentShareView mShareView;
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    @BindView(R.id.ll_comment)
    LinearLayout mLlComment;
//    @BindView(R.id.lay_error)
//    EmptyLayout mLayError;

    private Article mArticle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_detail;
    }


    public static void show(Context context, Article article) {
        if (article == null)
            return;
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra("article", article);
        context.startActivity(intent);
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mArticle = ( Article ) getIntent().getSerializableExtra("article");
    }

    @Override
    protected void initData() {
        StatusBarUtil.setStatusBarTrans(this, true);
        setDarkToolBar();
        addFragment(R.id.fl_content, ArticleDetailFragment.newInstance(mArticle));
    }


    private void addFragment(int layoutId, Fragment fragment) {
        FragmentManagerHelper fragmentManagerHelper = new FragmentManagerHelper(getSupportFragmentManager(), layoutId);
        fragmentManagerHelper.add(fragment);
    }
}
