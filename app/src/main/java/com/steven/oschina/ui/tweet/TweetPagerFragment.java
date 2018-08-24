package com.steven.oschina.ui.tweet;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseViewPagerFragment;
import com.steven.oschina.bean.sub.SubTab;
import com.steven.oschina.ui.synthetical.sub.SubFragment;
import com.steven.oschina.ui.tweet.topic.TopicFragment;

import java.util.ArrayList;
import java.util.List;


public class TweetPagerFragment extends BaseViewPagerFragment {

    public static TweetPagerFragment newInstance() {
        return new TweetPagerFragment();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tweet_pager;
    }

    @Override
    public List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TweetFragment.newInstance(TweetFragment.CATALOG_NEW));
        fragments.add(TweetFragment.newInstance(TweetFragment.CATALOG_HOT));
        fragments.add(TopicFragment.newInstance());
        fragments.add(getSubFragment());
        return fragments;
    }

    @Override
    public String[] getPageTitles() {
        return getResources().getStringArray(R.array.tweet_titles);
    }

    private SubFragment getSubFragment() {
        SubTab tab = new SubTab();
        tab.setType(3);
        tab.setFixed(false);
        tab.setName("每日乱弹");
        tab.setNeedLogin(false);
        tab.setHref("https://www.oschina.net/action/apiv2/sub_list?token=263ee86f538884e70ee1ee50aed759b6");
        tab.setSubtype(5);
        tab.setToken("263ee86f538884e70ee1ee50aed759b6");

        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", tab);
        return SubFragment.newInstance(tab);
    }
}
