package com.steven.oschina.ui.tweet;

import android.os.Bundle;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseRefreshFragment;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.bean.tweet.TweetComment;
import com.steven.oschina.ui.adapter.TweetCommentAdapter;
import com.steven.oschina.ui.tweet.viewmodel.TweetCommentViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：7/18/2018-3:08 PM
 *
 * @author yanzhiwen
 */
public class TweetCommentFragment extends BaseRefreshFragment<TweetComment, TweetCommentViewModel> {
    private Tweet mTweet;
    private List<TweetComment> mTweetComments = new ArrayList<>();

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
        mTweet = (Tweet) bundle.getSerializable("tweet");
    }

    @Override
    public void initData() {
        super.initData();
        mAdapter = new TweetCommentAdapter(mContext, mTweetComments, R.layout.item_tweet_comment);
        mSwipeRefreshRv.setAdapter(mAdapter);
    }

    @Override
    public void onRequestData() {
        super.onRequestData();
        if (mObserver == null) {
            mObserver = result -> {
                assert result != null;
                mNextPageToken = result.getNextPageToken();
                showTweetComment(result.getItems());
            };
        }
        mViewModel.getTweetComments(mTweet.getId(), mNextPageToken).observe(this, mObserver);
    }

    private void showTweetComment(List<TweetComment> tweetComments) {
        if (mSwipeRefreshRv.isRefreshing()) {
            mTweetComments.clear();
            mSwipeRefreshRv.setRefreshing(false);
        }
        if (tweetComments.size() == 0) {
            mSwipeRefreshRv.showLoadComplete();
            return;
        }
        mTweetComments.addAll(tweetComments);
        mSwipeRefreshRv.setLoading(false);
        mAdapter.notifyDataSetChanged();
    }
}
