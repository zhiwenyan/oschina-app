package com.steven.oschina.ui.tweet;

import android.os.Bundle;

import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseRecyclerFragment;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.bean.tweet.TweetLike;
import com.steven.oschina.ui.adapter.TweetLikeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：7/18/2018-3:08 PM
 *
 * @author yanzhiwen
 */
public class ListTweetLikeUsersFragment extends BaseRecyclerFragment {
    private Tweet mTweet;
    private List<TweetLike> mTweetLikes;

    public static ListTweetLikeUsersFragment newInstance(Tweet tweet) {
        Bundle args = new Bundle();
        args.putSerializable("tweet", tweet);
        ListTweetLikeUsersFragment fragment = new ListTweetLikeUsersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mTweet = ( Tweet ) bundle.getSerializable("tweet");
    }

    @Override
    public void initData() {
        mTweetLikes = new ArrayList<>();
        super.initData();
    }

    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        HttpUtils.get(RetrofitClient.getServiceApi().getTweetLikeList(mTweet.getId(), nextPageToken), new HttpCallback<TweetLike>() {
            @Override
            public void onSuccess(List<TweetLike> result, String nextPageToken) {
                super.onSuccess(result, nextPageToken);
                mNextPageToken = nextPageToken;
                showTweetLike(result);
            }
        });
    }

    private void showTweetLike(List<TweetLike> result) {
        if (mRefreshing) {
            mSwipeRefreshRv.setRefreshing(false);
            mTweetLikes.clear();
        }
        mTweetLikes.addAll(result);
        if (mAdapter == null) {
            mAdapter = new TweetLikeAdapter(mContext, mTweetLikes, R.layout.item_tweet_likes);
            mSwipeRefreshRv.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        if (result.size() == 0) {
            mSwipeRefreshRv.showLoadComplete();
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
}
