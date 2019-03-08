package com.steven.oschina.ui.tweet.topic;


import android.os.Bundle;
import android.text.TextUtils;

import com.steven.oschina.CacheManager;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseRefreshFragment;
import com.steven.oschina.bean.tweet.Topic;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.ui.adapter.TweetAdapter;
import com.steven.oschina.ui.tweet.TweetDetailActivity;
import com.steven.oschina.ui.tweet.viewmodel.TweetTopicViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetTopicFragment extends BaseRefreshFragment<Tweet, TweetTopicViewModel> {
    private Topic mTopic;
    private int mType;
    private List<Tweet> mTweets = new ArrayList<>();
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
        mTopic = (Topic) bundle.getSerializable("topic");
        mType = bundle.getInt("type");
    }

    @Override
    public void initData() {
        mAdapter = new TweetAdapter(mContext, mTweets, R.layout.item_list_tweet);
        mSwipeRefreshRv.setAdapter(mAdapter);
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
        if (mObserver == null) {
            mObserver = tweet -> {
                mNextPageToken = tweet.getNextPageToken();
                showTweetTopicList(tweet.getItems());
                if (mType == 1) {
                    CacheManager.saveToJson(mContext, UP_CACHE_NAME, tweet.getItems());
                } else {
                    CacheManager.saveToJson(mContext, HOTTEST_CACHE_NAME, tweet.getItems());
                }
            };
        }
        mViewModel.getTweetTopics(params).observe(this, mObserver);
    }

    private void showTweetTopicList(List<Tweet> tweets) {
        if (mSwipeRefreshRv.isRefreshing()) {
            mTweets.clear();
            mSwipeRefreshRv.setRefreshing(false);
        }
        if (tweets.size() == 0) {
            mSwipeRefreshRv.showLoadComplete();
            return;
        }
        mTweets.addAll(tweets);
        mSwipeRefreshRv.setLoading(false);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(position -> TweetDetailActivity.show(mContext, mTweets.get(position)));
    }
}
