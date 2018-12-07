package com.steven.oschina.ui.tweet.topic;


import android.os.Bundle;
import android.text.TextUtils;

import com.steven.oschina.CacheManager;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseRecyclerFragment1;
import com.steven.oschina.bean.tweet.Topic;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.ui.adapter.TweetAdapter;
import com.steven.oschina.ui.tweet.TweetDetailActivity;
import com.steven.oschina.ui.tweet.viewmodel.TweetTopicViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetTopicFragment extends BaseRecyclerFragment1<Tweet, TweetTopicViewModel> {
    private Topic mTopic;
    private int mType;
    private List<Tweet> mTweets;
    //最热话题
    private static final String HOTTEST_CACHE_NAME = "hottest_topic";
    //最新话题
    private static final String UP_CACHE_NAME = "up_topic";

    public static TweetTopicFragment newInstance(Topic topic, int type) {
        Bundle args = new Bundle();
        args.putSerializable("topic", topic);
        args.putInt("type", type);
        TweetTopicFragment fragment = new TweetTopicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mTopic = ( Topic ) bundle.getSerializable("topic");
        mType = bundle.getInt("type");
    }

    @Override
    public void initData() {
        mTweets = new ArrayList<>();
        super.initData();
    }

    @Override
    public void readCacheData() {
        super.readCacheData();
        List<Tweet> tweets;
        if (mType == 1) {
            tweets = CacheManager.readListJson(mContext, UP_CACHE_NAME, Tweet.class);
        } else {
            tweets = CacheManager.readListJson(mContext, HOTTEST_CACHE_NAME, Tweet.class);
        }
        if (tweets != null) {
            showTweetTopicList(tweets);
        }
    }

    @Override
    public void onRequestData() {
        super.onRequestData();
        Map<String, Object> params = new HashMap<>();
        if (!TextUtils.isEmpty(mNextPageToken)) {
            params.put("pageToken", mNextPageToken);
        }
        params.put("topicId", mTopic.getId());
        params.put("type", mType);
        mViewModel.getTweetTopics(params).observe(this, tweet -> {
            assert tweet != null;
            mNextPageToken = tweet.getNextPageToken();
            if (tweet.getItems().size() == 0) {
                mSwipeRefreshRv.showLoadComplete();
                return;
            }
            showTweetTopicList(tweet.getItems());
            if (mType == 1) {
                CacheManager.saveToJson(mContext, UP_CACHE_NAME, tweet.getItems());
            } else {
                CacheManager.saveToJson(mContext, HOTTEST_CACHE_NAME, tweet.getItems());
            }
        });

    }


    private void showTweetTopicList(List<Tweet> tweets) {
        if (mSwipeRefreshRv.isRefreshing()) {
            mSwipeRefreshRv.setRefreshing(false);
            mTweets.clear();
        }
        mTweets.addAll(tweets);
        if (mAdapter == null) {
            mAdapter = new TweetAdapter(mContext, mTweets, R.layout.item_list_tweet);
            mSwipeRefreshRv.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        mAdapter.setOnItemClickListener(position -> TweetDetailActivity.show(mContext, mTweets.get(position)));
    }
}
