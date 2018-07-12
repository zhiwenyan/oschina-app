package com.steven.oschina;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.greenfarm.client.base_library.utils.FragmentManagerHelper;
import com.greenfarm.client.base_library.utils.StatusBarUtil;
import com.steven.oschina.base.BaseActivity;
import com.steven.oschina.ui.PubActivity;
import com.steven.oschina.ui.explore.ExploreFragment;
import com.steven.oschina.ui.synthetical.SyntheticalPagerFragment;
import com.steven.oschina.ui.tweet.TweetPagerFragment;
import com.steven.oschina.ui.user.UserInfoFragment;
import com.steven.oschina.widget.NavigationButton;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.nav_item_news)
    NavigationButton mNavItemNews;
    @BindView(R.id.nav_item_tweet)
    NavigationButton mNavItemTweet;
    @BindView(R.id.nav_item_tweet_pub)
    ImageView mNavItemTweetPub;
    @BindView(R.id.nav_item_explore)
    NavigationButton mNavItemExplore;
    @BindView(R.id.nav_item_me)
    NavigationButton mNavItemMe;
    private NavigationButton mCurrentNavigationButton;
    private FragmentManagerHelper mFragmentManagerHelper;
    private SyntheticalPagerFragment mSyntheticalFragment;
    private TweetPagerFragment mTweetFragment;
    private ExploreFragment mExploreFragment;
    private UserInfoFragment mUserInfoFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarTrans(this, true);
        mNavItemNews.init(R.drawable.tab_icon_new, "综合");
        mNavItemTweet.init(R.drawable.tab_icon_tweet, "动弹");
        mNavItemExplore.init(R.drawable.tab_icon_explore, "发现");
        mNavItemMe.init(R.drawable.tab_icon_me, "我的");
        mNavItemNews.setSelected(true);
        mCurrentNavigationButton = mNavItemNews;
        mFragmentManagerHelper = new FragmentManagerHelper(getSupportFragmentManager(), R.id.container);
        mSyntheticalFragment = SyntheticalPagerFragment.newInstance();
        mFragmentManagerHelper.add(mSyntheticalFragment);

    }


    @OnClick({R.id.nav_item_news, R.id.nav_item_tweet, R.id.nav_item_tweet_pub, R.id.nav_item_explore, R.id.nav_item_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_item_news:
                setSelected(( NavigationButton ) view);
                if (mSyntheticalFragment == null) {
                    mSyntheticalFragment = SyntheticalPagerFragment.newInstance();
                }
                mFragmentManagerHelper.switchFragment(mSyntheticalFragment);
                break;
            case R.id.nav_item_tweet:
                setSelected(( NavigationButton ) view);
                if (mTweetFragment == null) {
                    mTweetFragment = TweetPagerFragment.newInstance();
                }
                mFragmentManagerHelper.switchFragment(mTweetFragment);
                break;
            case R.id.nav_item_explore:
                setSelected(( NavigationButton ) view);
                if (mExploreFragment == null) {
                    mExploreFragment = ExploreFragment.newInstance();
                }
                mFragmentManagerHelper.switchFragment(mExploreFragment);
                break;
            case R.id.nav_item_me:
                setSelected(( NavigationButton ) view);
                if (mUserInfoFragment == null) {
                    mUserInfoFragment = UserInfoFragment.newInstance();
                }
                mFragmentManagerHelper.switchFragment(mUserInfoFragment);
                break;
            case R.id.nav_item_tweet_pub:
                startActivity(PubActivity.class);
                break;
        }
    }

    private void setSelected(NavigationButton nav) {
        NavigationButton oldNav = mCurrentNavigationButton;
        if (nav == oldNav) {
            return;
        }
        oldNav.setSelected(false);
        nav.setSelected(true);
        mCurrentNavigationButton = nav;
    }
}
