package com.steven.oschina.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.media.Util;
import com.steven.oschina.ui.synthetical.article.PubArticleActivity;
import com.steven.oschina.ui.synthetical.article.WriteBlogActivity;
import com.steven.oschina.ui.tweet.TweetPublishActivity;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;


public class PubActivity extends BaseActivity {

    @BindView(R.id.btn_pub)
    ImageView mBtnPub;
    @BindView(R.id.rl_main)
    RelativeLayout mRlMain;
    //绑定多个View使用BindViews
    @BindViews({R.id.ll_pub_article, R.id.ll_pub_blog, R.id.ll_pub_tweet})
    LinearLayout[] mLays;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pub;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mBtnPub.animate()
                .rotation(135.0f)
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                }).start();
        show(0);
        show(1);
        show(2);
    }


    private void dismiss() {
        close();
        close(0);
        close(1);
        close(2);

    }

    private void close() {
        mBtnPub.clearAnimation();
        mBtnPub.animate()
                .rotation(0f)
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (mBtnPub != null)
                            mBtnPub.setVisibility(View.GONE);
                        finish();
                    }
                })
                .start();
    }

    private void show(int position) {
        int angle = 30 + position * 60;
        float x = ( float ) Math.cos(angle * (Math.PI / 180)) * Util.dipTopx(this, 100);
        float y = ( float ) -Math.sin(angle * (Math.PI / 180)) * Util.dipTopx(this, position != 1 ? 160 : 100);
        // position=0x=259.80762,y=-240.0
        // position=1x=1.8369703E-14,y=-300.0
        // position=2x=-259.80762,y=-240.0
        System.out.println("position=" + position + "x=" + x + ",y=" + y);
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(mLays[position], "translationX", 0, x);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(mLays[position], "translationY", 0, y);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(180);
        animatorSet.play(objectAnimatorX).with(objectAnimatorY);
        animatorSet.start();
    }

    private void close(final int position) {
        int angle = 30 + position * 60;
        float x = ( float ) Math.cos(angle * (Math.PI / 180)) * Util.dipTopx(this, 100);
        float y = ( float ) -Math.sin(angle * (Math.PI / 180)) * Util.dipTopx(this, position != 1 ? 160 : 100);
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(mLays[position], "translationX", x, 0);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(mLays[position], "translationY", y, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(180);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(objectAnimatorX).with(objectAnimatorY);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mLays != null)
                    mLays[position].setVisibility(View.GONE);
            }
        });
        animatorSet.start();
    }

    @OnClick({R.id.ll_pub_tweet, R.id.ll_pub_article, R.id.ll_pub_blog, R.id.rl_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_main:
                dismiss();
                break;
            case R.id.ll_pub_tweet:
                TweetPublishActivity.show(this, this.findViewById(R.id.nav_item_tweet_pub));
                break;
            case R.id.ll_pub_article:
                PubArticleActivity.show(this, "");
                break;
            case R.id.ll_pub_blog:
                WriteBlogActivity.show(this);
                break;
        }
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
