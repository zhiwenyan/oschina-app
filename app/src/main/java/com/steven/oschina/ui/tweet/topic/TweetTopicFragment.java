package com.steven.oschina.ui.tweet.topic;


import android.os.Bundle;
import android.text.TextUtils;

import com.steven.oschina.CacheManager;
import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseRecyclerFragment;
import com.steven.oschina.bean.tweet.Topic;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.ui.adapter.TweetAdapter;
import com.steven.oschina.ui.tweet.TweetDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetTopicFragment extends BaseRecyclerFragment {
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
        List<Tweet> tweets = null;
        switch (mType) {
            case 1:
                tweets = CacheManager.readListJson(mContext, UP_CACHE_NAME, Tweet.class);
                break;
            case 2:
                tweets = CacheManager.readListJson(mContext, HOTTEST_CACHE_NAME, Tweet.class);
                break;
        }
        if (tweets != null) {
            showTweetList(tweets);
        }
    }

    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        Map<String, Object> params = new HashMap<>();
        if (!TextUtils.isEmpty(nextPageToken)) {
            params.put("pageToken", nextPageToken);
        }
        params.put("topicId", mTopic.getId());
        params.put("type", mType);
        HttpUtils.get(RetrofitClient.getServiceApi().getTopicTweets(params), new HttpCallback<Tweet>() {
            @Override
            public void onSuccess(List<Tweet> tweets, String nextPageToken) {
                if (mRefreshing) {
                    mSwipeRefreshRv.setRefreshing(false);
                }
                mNextPageToken = nextPageToken;
                if (tweets.size() == 0) {
                    mSwipeRefreshRv.showLoadComplete();
                    return;
                }
                showTweetList(tweets);
                if (mType == 1) {
                    CacheManager.saveToJson(mContext, UP_CACHE_NAME, tweets);
                } else {
                    CacheManager.saveToJson(mContext, HOTTEST_CACHE_NAME, tweets);
                }
            }
        });
    }

    private void showTweetList(List<Tweet> tweets) {
        if (mRefreshing) {
            mTweets.clear();
        }
        mTweets.addAll(tweets);
        if (mAdapter == null) {
            mAdapter = new TweetAdapter(mContext, mTweets, R.layout.item_list_tweet);
            mSwipeRefreshRv.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnItemClickListener(position -> {
            TweetDetailActivity.show(mContext, mTweets.get(position));
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        onRequestData("");
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        onRequestData(mNextPageToken);
    }
}
