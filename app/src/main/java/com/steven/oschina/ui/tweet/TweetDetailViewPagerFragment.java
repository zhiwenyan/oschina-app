package com.steven.oschina.ui.tweet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseFragment;
import com.steven.oschina.bean.tweet.Tweet;

import butterknife.BindView;

/**
 * Description:
 * Data：7/18/2018-3:04 PM
 *
 * @author yanzhiwen
 */
public class TweetDetailViewPagerFragment extends BaseFragment {
    private Tweet mTweet;
    @BindView(R.id.tab_nav)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    FragmentStatePagerAdapter mAdapter;

    public static TweetDetailViewPagerFragment newInstance(Tweet tweet) {
        Bundle args = new Bundle();
        args.putSerializable("tweet", tweet);
        TweetDetailViewPagerFragment fragment = new TweetDetailViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tweet_view_pager;
    }
    @Override
    public void initData() {
    }

    @Override
    public void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mTweet = ( Tweet ) bundle.getSerializable("tweet");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mAdapter == null) {
            mViewPager.setAdapter(mAdapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    switch (position) {
                        case 0:
                            return ListTweetLikeUsersFragment.newInstance(mTweet);
                        case 1:
                            return TweetCommentFragment.newInstance(mTweet);
                    }
                    return null;
                }

                @Override
                public int getCount() {
                    return 2;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    switch (position) {
                        case 0:
                            return String.format("赞 (%s)", mTweet.getLikeCount());
                        case 1:
                            return String.format("评论 (%s)", mTweet.getCommentCount());
                    }
                    return null;
                }
            });
            mTabLayout.setupWithViewPager(mViewPager);
            mViewPager.setCurrentItem(1);
        } else {
            mViewPager.setAdapter(mAdapter);
        }

    }
}
