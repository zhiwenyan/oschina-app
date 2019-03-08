package com.steven.oschina.ui.tweet.topic;


import android.content.Intent;
import android.os.Bundle;

import com.steven.oschina.CacheManager;
import com.steven.oschina.OSCApplication;
import com.steven.oschina.R;
import com.steven.oschina.base.BaseRefreshFragment;
import com.steven.oschina.bean.tweet.Topic;
import com.steven.oschina.ui.adapter.TopicAdapter;
import com.steven.oschina.ui.tweet.viewmodel.HotTopicViewModel;

import java.util.ArrayList;
import java.util.List;


public class HotTopicFragment extends BaseRefreshFragment<Topic, HotTopicViewModel> {
    private static final String CACHE_NAME = "hot_topic";
    private static final String TOPIC_TYPE = "2";
    private List<Topic> mTopics;

    public static HotTopicFragment newInstance() {
        Bundle args = new Bundle();
        HotTopicFragment fragment = new HotTopicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        mTopics = new ArrayList<>();
        super.initData();
    }

    @Override
    public void readCacheData() {
        super.readCacheData();
        List<Topic> topics = CacheManager.readListJson(mContext, CACHE_NAME, Topic.class);
        if (topics != null) {
            showTopicList(topics);
        }

    }

    @Override
    public void onRequestData() {
        super.onRequestData();
        if (mObserver == null) {
            mObserver = topic -> {
                assert topic != null;
                mNextPageToken = topic.getNextPageToken();
                showTopicList(topic.getItems());
                CacheManager.saveToJson(OSCApplication.getInstance(), CACHE_NAME, topic.getItems());

            };
        }
        mViewModel.getHotTopics(TOPIC_TYPE, mNextPageToken).observe(this, mObserver);
    }

    private void showTopicList(List<Topic> topics) {
        if (mSwipeRefreshRv.isRefreshing()) {
            mTopics.clear();
            mSwipeRefreshRv.setRefreshing(false);
        }
        if (topics.size() == 0) {
            mSwipeRefreshRv.showLoadComplete();
            return;
        }
        mTopics.addAll(topics);
        if (mAdapter == null) {
            mAdapter = new TopicAdapter(mContext, mTopics, R.layout.item_list_topic);
            mSwipeRefreshRv.setAdapter(mAdapter);
        } else {
            mSwipeRefreshRv.setLoading(false);
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnItemClickListener(position -> {
            Topic topic = mTopics.get(position);
            Intent intent = new Intent(mContext, TopicDetailActivity.class);
            intent.putExtra("topic", topic);
            intent.putExtra("position", position);
            startActivity(intent);
        });
    }
}
