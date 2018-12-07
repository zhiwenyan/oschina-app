package com.steven.oschina.ui.tweet;

import android.os.Bundle;

import com.steven.oschina.R;
import com.steven.oschina.base.BaseRecyclerFragment1;
import com.steven.oschina.bean.tweet.Tweet;
import com.steven.oschina.bean.tweet.TweetLike;
import com.steven.oschina.ui.adapter.TweetLikeAdapter;
import com.steven.oschina.ui.tweet.viewmodel.TweetLikesUserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：7/18/2018-3:08 PM
 *
 * @author yanzhiwen
 */
public class ListTweetLikeUsersFragment extends BaseRecyclerFragment1<TweetLike, TweetLikesUserViewModel> {
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
    public void onRequestData() {
        super.onRequestData();
        if (mObserver == null) {
            mObserver = result -> {
                assert result != null;
                mNextPageToken = result.getNextPageToken();
                showTweetLike(result.getItems());
            };
        }
        mViewModel.getTweetLikesUser(mTweet.getId(), mNextPageToken).observe(this, mObserver);
    }

    private void showTweetLike(List<TweetLike> tweetLikes) {
        if (mSwipeRefreshRv.isRefreshing()) {
            mSwipeRefreshRv.setRefreshing(false);
            mTweetLikes.clear();
        }

        if (tweetLikes.size() == 0) {
            mSwipeRefreshRv.showLoadComplete();
            return;
        }
        mTweetLikes.addAll(tweetLikes);
        if (mAdapter == null) {
            mAdapter = new TweetLikeAdapter(mContext, mTweetLikes, R.layout.item_tweet_likes);
            mSwipeRefreshRv.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }
}
