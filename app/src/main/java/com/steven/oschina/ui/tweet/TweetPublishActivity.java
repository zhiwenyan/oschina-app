package com.steven.oschina.ui.tweet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Size;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.greenfarm.client.base_library.utils.StatusBarUtil;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.bean.simple.About;

public class TweetPublishActivity extends BaseActivity {

    public static void show(Context context) {
        show(context, null);
    }

    public static void show(Context context, View view) {
        show(context, view, null);
    }

    public static void show(Context context, View view, String defaultContent) {
        show(context, view, defaultContent, null);
    }

    public static void show(Context context, View view, String defaultContent, About.Share share) {
        int[] location = new int[]{0, 0};
        int[] size = new int[]{0, 0};

        if (view != null) {
            view.getLocationOnScreen(location);
            size[0] = view.getWidth();
            size[1] = view.getHeight();
        }

        show(context, location, size, defaultContent, share, null);
    }

    @SuppressWarnings("unused")
    public static void show(Context context, boolean isLocalShare, String localImageUrl) {
        show(context, null, null, null, null, localImageUrl);
    }


    public static void show(Context context, @Size(2) int[] viewLocationOnScreen,
                            @Size(2) int[] viewSize, String defaultContent, About.Share share, String localImageUrl) {
        // Check login before show
//        if (!AccountHelper.isLogin()) {
//            UIHelper.showLoginActivity(context);
//            return;
//        }

        Intent intent = new Intent(context, TweetPublishActivity.class);

        if (viewLocationOnScreen != null) {
            intent.putExtra("location", viewLocationOnScreen);
        }
        if (viewSize != null) {
            intent.putExtra("size", viewSize);
        }
        if (defaultContent != null) {
            intent.putExtra("defaultContent", defaultContent);
        }
        if (share != null) {
            intent.putExtra("aboutShare", share);
        }
        if (!TextUtils.isEmpty(localImageUrl)) {
            intent.putExtra("imageUrl", localImageUrl);
        }
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tweet_publish;
    }

    @Override
    protected void initData() {
        StatusBarUtil.setStatusBarTrans(this, true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle == null) bundle = new Bundle();
        // Read other data  todo
        //readFastShareByOther(bundle, intent);

        TweetPublishFragment fragment = new TweetPublishFragment();
        // init the args bounds
        fragment.setArguments(bundle);
        FragmentTransaction trans = getSupportFragmentManager()
                .beginTransaction();
        trans.replace(R.id.activity_tweet_publish, fragment);
        trans.commit();
    }
}
