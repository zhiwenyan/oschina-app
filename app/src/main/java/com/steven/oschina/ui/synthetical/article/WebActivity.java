package com.steven.oschina.ui.synthetical.article;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.utils.TDevice;
import com.steven.oschina.widget.OSCWebView;

import butterknife.BindView;

/**
 * Description:
 * Data：7/26/2018-4:23 PM
 *
 * @author yanzhiwen
 */
public class WebActivity extends BaseActivity implements OSCWebView.OnFinishListener {
    protected OSCWebView mWebView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.ll_root)
    LinearLayout mLinearRoot;
    private String mTitle;
    //    protected ShareDialog mShareDialog;
    private String mUrl;
    private boolean isShowAd;
    private boolean isWebViewFinish;

    public static void show(Context context, String url) {
        if (!TDevice.hasWebView(context))
            return;
        if (TextUtils.isEmpty(url))
            return;
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void show(Context context, String url, boolean isShowAd) {
        if (TextUtils.isEmpty(url))
            return;
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("isShowAd", isShowAd);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initData() {

        mWebView = new OSCWebView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        mWebView.setVisibility(View.INVISIBLE);
        mWebView.setLayoutParams(params);
        mLinearRoot.addView(mWebView);
        mWebView.setOnFinishFinish(this);
        mUrl = getIntent().getStringExtra("url");
        isShowAd = getIntent().getBooleanExtra("isShowAd", false);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                if (!TextUtils.isEmpty(mTitle) && !TextUtils.isEmpty(mUrl)) {
//
//                    mShareDialog.show();  mShareDialog.init(this, mWebView.getTitle(),
////                            " ",
////                            mWebView.getUrl());
                }
                break;
        }
        return false;
    }

    @Override
    public void onReceivedTitle(String title) {
        if (isDestroyed())
            return;
//        mShareDialog.setTitle(title);
        mTitle = title;
//        mShareDialog.init(this, title, "", mWebView.getUrl());
//        mToolBar.setTitle("返回");
    }

    @Override
    public void onProgressChange(int progress) {
        if (isDestroyed())
            return;
        mProgressBar.setProgress(progress);
        if (!mWebView.hasRule()) {
            mWebView.setVisibility(View.VISIBLE);
            return;
        }
        if (progress >= 60 && !isWebViewFinish) {
            isWebViewFinish = true;
            mWebView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isDestroyed())
                        return;
                    mWebView.setVisibility(View.VISIBLE);
                }
            }, 800);
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {
        if (isDestroyed())
            return;
        mProgressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void finish() {
        super.finish();
        if (isShowAd) {
//            MainActivity.show(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
//            if (!MainActivity.IS_SHOW) {
//                MainActivity.show(this);
//            }
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.onDestroy();
        }
//        if (!MainActivity.IS_SHOW) {
//            MainActivity.show(this);
//        }
    }
}

