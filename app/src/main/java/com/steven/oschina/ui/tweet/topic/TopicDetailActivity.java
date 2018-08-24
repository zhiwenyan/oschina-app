package com.steven.oschina.ui.tweet.topic;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.bean.tweet.Topic;
import com.steven.oschina.share.ShareDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TopicDetailActivity extends BaseActivity {

    @BindView(R.id.tv_topic_title)
    TextView mTopicTitle;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.iv_topic)
    ImageView mTopicIv;
    //  @BindView(R.id.tv_count)
//  TextView mJoinCount;
//  @BindView(R.id.tv_topic_desc)
//  TextView mTopicDesc;
    private Topic mTopic;
    private ShareDialog mShareDialog;

    private static final int UP_TYPE = 1;
    private static final int HOT_TYPE = 2;

    private static final int[] images = {
            R.mipmap.bg_topic_1, R.mipmap.bg_topic_2, R.mipmap.bg_topic_3,
            R.mipmap.bg_topic_4, R.mipmap.bg_topic_5
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topic_detail;
    }

    @Override
    protected void initData() {
        mTopic = ( Topic ) getIntent().getSerializableExtra("topic");
        int position = getIntent().getIntExtra("position", 0);
        mTopicIv.setImageResource(images[position % 5]);
        mTopicTitle.setText("#" + mTopic.getTitle() + "#");
//      mJoinCount.setText(String.format("共有%s人参与", mTopic.getJoinCount()));
//      mTopicDesc.setText(mTopic.getContent());
        String[] pageTitle = getResources().getStringArray(R.array.topic);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HotTopicFragment.newInstance(mTopic, HOT_TYPE));
        fragments.add(HotTopicFragment.newInstance(mTopic, UP_TYPE));
        TopicPagerAdapter adapter = new TopicPagerAdapter(getSupportFragmentManager(), pageTitle, fragments);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void setUpActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        View decorView = this.getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }


    @OnClick(R.id.iv_share)
    public void onViewClicked() {
        if (mShareDialog == null)
            mShareDialog = new ShareDialog(this);
        mShareDialog.show();
    }

    private static class TopicPagerAdapter extends FragmentPagerAdapter {
        private String[] mPageTitle;
        private List<Fragment> mFragments;

        public TopicPagerAdapter(FragmentManager fm, String[] pageTitle, List<Fragment> fragments) {
            super(fm);
            this.mPageTitle = pageTitle;
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mPageTitle[position];
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
