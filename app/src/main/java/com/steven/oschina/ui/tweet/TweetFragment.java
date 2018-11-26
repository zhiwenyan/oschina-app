package com.steven.oschina.ui.tweet;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.steven.oschina.CacheManager;
import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseRecyclerFragment;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.ui.adapter.TweetAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetFragment extends BaseRecyclerFragment<Tweet> {


    public static final int CATALOG_NEW = 0X0001;
    public static final int CATALOG_HOT = 0X0002;
    public static final int CATALOG_MYSELF = 0X0003;
    public static final int CATALOG_FRIENDS = 0X0004;
    public static final int CATALOG_TAG = 0X0005;
    public static final int CATALOG_SOMEONE = 0X0006;
    public static final int CATALOG_TOPIC = 0X0007;
    public static final String BUNDLE_KEY_REQUEST_CATALOG = "BUNDLE_KEY_REQUEST_CATALOG";

    public static final String CACHE_NEW_TWEET = "cache_new_tweet";
    public static final String CACHE_HOT_TWEET = "cache_hot_tweet";
    public static final String CACHE_USER_TWEET = "cache_user_tweet";
    public static final String CACHE_USER_FRIEND = "cache_user_friend";
    private String CACHE_NAME;
    //动弹的请求类型
    public int mReqCatalog;
    //请求参数
    private Map<String, Object> params = new HashMap<>();
    private List<Tweet> mTweets;

    public static Fragment newInstance(int catalog) {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_REQUEST_CATALOG, catalog);
        Fragment fragment = new TweetFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initData() {
        mTweets = new ArrayList<>();
        super.initData();
    }

    @Override
    public void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mReqCatalog = bundle.getInt(BUNDLE_KEY_REQUEST_CATALOG, CATALOG_NEW);

    }

    @Override
    public void requestCacheData() {
        List<Tweet> tweets = null;
        switch (mReqCatalog) {
            case CATALOG_NEW:
                CACHE_NAME = CACHE_NEW_TWEET;
                tweets = CacheManager.readListJson(mContext, CACHE_NAME, Tweet.class);
                break;
            case CATALOG_HOT:
                CACHE_NAME = CACHE_HOT_TWEET;
                tweets = CacheManager.readListJson(mContext, CACHE_NAME, Tweet.class);
                break;
        }
        if (tweets != null) {
            showTweetList(tweets);
        }

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

    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        switch (mReqCatalog) {
            case CATALOG_NEW:
                params.put("type", 1);
                params.put("order", 1);
                params.put("pageToken", nextPageToken);
                CACHE_NAME = CACHE_NEW_TWEET;
                break;
            case CATALOG_HOT:
                params.put("type", 1);
                params.put("order", 2);
                params.put("pageToken", nextPageToken);
                CACHE_NAME = CACHE_HOT_TWEET;
                break;
            case CATALOG_MYSELF:
                break;
        }
        HttpUtils.get(RetrofitClient.getServiceApi().getTweetList(params), new HttpCallback<Tweet>() {
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
                CacheManager.saveToJson(mContext, CACHE_NAME, tweets);
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
}
