package com.steven.oschina.ui.tweet;

import android.os.Bundle;

import com.steven.oschina.R;
import com.steven.oschina.api.HttpCallback;
import com.steven.oschina.api.HttpUtils;
import com.steven.oschina.api.RetrofitClient;
import com.steven.oschina.base.BaseRecyclerFragment;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.bean.tweet.TweetComment;
import com.steven.oschina.ui.adapter.TweetCommentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：7/18/2018-3:08 PM
 *
 * @author yanzhiwen
 */
public class TweetCommentFragment extends BaseRecyclerFragment {
    private Tweet mTweet;
    private List<TweetComment> mTweetComments;

    public static TweetCommentFragment newInstance(Tweet tweet) {
        Bundle args = new Bundle();
        args.putSerializable("tweet", tweet);
        TweetCommentFragment fragment = new TweetCommentFragment();
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
        mTweetComments = new ArrayList<>();
        super.initData();
    }

    @Override
    public void onRequestData(String nextPageToken) {
        super.onRequestData(nextPageToken);
        HttpUtils.get(RetrofitClient.getServiceApi().getTweetCommentList(mTweet.getId(), nextPageToken), new HttpCallback<TweetComment>() {
            @Override
            public void onSuccess(List<TweetComment> result, String nextPageToken) {
                super.onSuccess(result, nextPageToken);
                mNextPageToken = nextPageToken;
                showTweetComment(result);
            }
        });
    }

    private void showTweetComment(List<TweetComment> result) {
        if (mRefreshing) {
            mSwipeRefreshRv.setRefreshing(false);
            mTweetComments.clear();
        }
        mTweetComments.addAll(result);
        if (mAdapter == null) {
            mAdapter = new TweetCommentAdapter(mContext, mTweetComments, R.layout.item_tweet_comment);
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
